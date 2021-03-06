package de.fuberlin.wiwiss.d2rq.tests;

import java.util.Collections;

import org.d2rq.algebra.Attribute;
import org.d2rq.db.DummyDB;
import org.d2rq.db.SQLConnection;
import org.d2rq.db.expr.ColumnExpr;
import org.d2rq.db.expr.Equality;
import org.d2rq.db.expr.Expression;
import org.d2rq.db.expr.SQLExpression;
import org.d2rq.lang.Pattern;
import org.d2rq.lang.TranslationTable;
import org.d2rq.values.BlankNodeIDValueMaker;
import org.d2rq.values.TemplateValueMaker;
import org.d2rq.values.Translator;

import junit.framework.TestCase;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

import de.fuberlin.wiwiss.d2rq.nodes.NodeSetConstraintBuilder;


/**
 * TODO: Improve matching of datatypes, languages etc:
 * - "foo"@en and "foo"@en-US should match
 * - "1"@xsd:int and "1"@xsd:bye should match
 * - "asdf" and "asdf"@xsd:string should match
 * - "1"@xsd:double and "+1.0"@xsd:double should match
 * - Attributes with obviously incompatible types should not match
 *
 * @author Richard Cyganiak (richard@cyganiak.de)
 */
public class NodeSetConstraintBuilderTest extends TestCase {
	private final static Attribute table1foo = DummyDB.column("table1.foo");
	private final static Attribute table1bar = DummyDB.column("table1.bar");
	private final static Attribute alias1foo = DummyDB.column("T2_table1.foo");
	private final static BlankNodeIDValueMaker fooBlankNodeID =
		new BlankNodeIDValueMaker("Foo", Collections.singletonList(table1foo));
	private final static BlankNodeIDValueMaker fooBlankNodeID2 =
		new BlankNodeIDValueMaker("Foo", Collections.singletonList(alias1foo));
	private final static BlankNodeIDValueMaker barBlankNodeID =
		new BlankNodeIDValueMaker("Bar", Collections.singletonList(table1foo));
	private final static Expression expression1 = 
		SQLExpression.create("SHA1(table1.foo)", new DummyDB());
	private final static Expression expression2 = 
		SQLExpression.create("LOWER(table1.bar)", new DummyDB());
	private SQLConnection sqlConnection;
	private TemplateValueMaker pattern1; 
	private TemplateValueMaker pattern1aliased; 
	private TemplateValueMaker pattern2;
	private TemplateValueMaker pattern3;
	private NodeSetConstraintBuilder nodes;
	
	public void setUp() {
		sqlConnection = new DummyDB();
		pattern1 = new Pattern("http://example.org/res@@table1.foo@@").toTemplate(sqlConnection);
		pattern1aliased = new Pattern("http://example.org/res@@T2_table1.foo@@").toTemplate(sqlConnection);
		pattern2 = new Pattern("http://example.org/thing@@table1.foo@@").toTemplate(sqlConnection);
		pattern3 = new Pattern("http://example.org/res@@table1.foo@@/@@table1.bar@@").toTemplate(sqlConnection);
		nodes = new NodeSetConstraintBuilder();
	}

	public void testInitiallyNotEmpty() {
		assertFalse(nodes.isEmpty());
	}
	
	public void testLimitToURIsNotEmpty() {
		nodes.limitToURIs();
		assertFalse(nodes.isEmpty());
	}
	
	public void testLimitToLiteralsNotEmpty() {
		nodes.limitToLiterals(null, null);
		assertFalse(nodes.isEmpty());
	}
	
	public void testLimitToBlankNodes() {
		nodes.limitToBlankNodes();
		assertFalse(nodes.isEmpty());
	}
	
	public void testLimitToFixedNodeNotEmpty() {
		nodes.limitTo(RDF.Nodes.type);
		assertFalse(nodes.isEmpty());
	}

	public void testLimitToEmptySetIsEmpty() {
		nodes.limitToEmptySet();
		assertTrue(nodes.isEmpty());
	}
	
	public void testLimitValuesToConstantNotEmpty() {
		nodes.limitValues("foo");
		assertFalse(nodes.isEmpty());
	}
	
	public void testLimitValuesToAttributeNotEmpty() {
		nodes.limitValuesToColumn(table1foo);
		assertFalse(nodes.isEmpty());
	}
	
	public void testLimitValuesToPatternNotEmpty() {
		nodes.limitValuesToPattern(pattern1);
		assertFalse(nodes.isEmpty());
	}
	
	public void testLimitValuesToBlankNodeIDNotEmpty() {
		nodes.limitValuesToBlankNodeID(fooBlankNodeID);
		assertFalse(nodes.isEmpty());
	}
	
	public void testLimitValuesToExpressionNotEmpty() {
		nodes.limitValuesToExpression(expression1);
		assertFalse(nodes.isEmpty());
	}
	
	public void testURIsAndLiteralsEmpty() {
		nodes.limitToURIs();
		nodes.limitToLiterals(null, null);
		assertTrue(nodes.isEmpty());
	}
	
	public void testURIsAndBlanksEmpty() {
		nodes.limitToURIs();
		nodes.limitToBlankNodes();
		assertTrue(nodes.isEmpty());
	}
	
	public void testBlanksAndLiteralsEmpty() {
		nodes.limitToBlankNodes();
		nodes.limitToLiterals(null, null);
		assertTrue(nodes.isEmpty());
	}
	
	public void testDifferentFixedBlanksEmpty() {
		nodes.limitTo(Node.createAnon(new AnonId("foo")));
		nodes.limitTo(Node.createAnon(new AnonId("bar")));
		assertTrue(nodes.isEmpty());
	}
	
	public void testDifferentFixedURIsEmpty() {
		nodes.limitTo(RDF.Nodes.type);
		nodes.limitTo(RDF.Nodes.Property);
		assertTrue(nodes.isEmpty());
	}

	public void testDifferentFixedLiteralsEmpty() {
		nodes.limitTo(Node.createLiteral("foo"));
		nodes.limitTo(Node.createLiteral("bar"));
		assertTrue(nodes.isEmpty());
	}
	
	public void testDifferentTypeFixedLiteralsEmpty() {
		nodes.limitTo(Node.createURI("http://example.org/"));
		nodes.limitTo(Node.createLiteral("http://example.org/"));
		assertTrue(nodes.isEmpty());
	}
	
	public void testDifferentConstantsEmpty() {
		nodes.limitValues("foo");
		nodes.limitValues("bar");
		assertTrue(nodes.isEmpty());
	}
	
	public void testFixedAndConstantEmpty() {
		nodes.limitTo(Node.createURI("http://example.org/"));
		nodes.limitValues("foo");
		assertTrue(nodes.isEmpty());
	}
	
	public void testFixedAndConstantNotEmpty() {
		nodes.limitTo(Node.createURI("http://example.org/"));
		nodes.limitValues("http://example.org/");
		assertFalse(nodes.isEmpty());
	}
	
	public void testDifferentLanguagesEmpty() {
		nodes.limitToLiterals("en", null);
		nodes.limitToLiterals("de", null);
		assertTrue(nodes.isEmpty());
	}
	
	public void testDifferentLanguagesFixedEmpty() {
		nodes.limitTo(Node.createLiteral("foo", "de", null));
		nodes.limitTo(Node.createLiteral("foo", "en", null));
		assertTrue(nodes.isEmpty());
	}
	
	public void testDifferentDatatypesEmpty() {
		nodes.limitToLiterals(null, XSDDatatype.XSDstring);
		nodes.limitToLiterals(null, XSDDatatype.XSDinteger);
		assertTrue(nodes.isEmpty());
	}
	
	public void testDifferentDatatypesFixedEmpty() {
		nodes.limitTo(Node.createLiteral("42", null, XSDDatatype.XSDstring));
		nodes.limitTo(Node.createLiteral("42", null, XSDDatatype.XSDinteger));
		assertTrue(nodes.isEmpty());
	}
	
	public void testSameAttributeTwiceNotEmpty() {
		nodes.limitValuesToColumn(table1foo);
		nodes.limitValuesToColumn(table1foo);
		assertFalse(nodes.isEmpty());
	}
	
	public void testAttributeAndConstantNotEmpty() {
		nodes.limitValues("foo");
		nodes.limitValuesToColumn(table1foo);
		assertFalse(nodes.isEmpty());
	}
	
	public void testSameBlankNodeIDTwiceNotEmpty() {
		nodes.limitValuesToBlankNodeID(fooBlankNodeID);
		nodes.limitValuesToBlankNodeID(fooBlankNodeID);
		assertFalse(nodes.isEmpty());
	}
	
	public void testSamePatternTwiceNotEmpty() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValuesToPattern(pattern1);
		assertFalse(nodes.isEmpty());
	}
	
	public void testSameExpressionTwiceNotEmpty() {
		nodes.limitValuesToExpression(expression1);
		nodes.limitValuesToExpression(expression1);
		assertFalse(nodes.isEmpty());
	}
	
	public void testBlankNodesFromDifferentClassMapsEmpty() {
		nodes.limitValuesToBlankNodeID(fooBlankNodeID);
		nodes.limitValuesToBlankNodeID(barBlankNodeID);
		assertTrue(nodes.isEmpty());
	}
	
	public void testBlankNodeAndConstantMatchNotEmpty() {
		nodes.limitValuesToBlankNodeID(fooBlankNodeID);
		nodes.limitTo(Node.createAnon(new AnonId("Foo@@42")));
		assertFalse(nodes.isEmpty());
	}
	
	public void testBlankNodeAndConstantNoMatchEmpty() {
		nodes.limitValuesToBlankNodeID(fooBlankNodeID);
		nodes.limitTo(Node.createAnon(new AnonId("Bar@@42")));
		assertTrue(nodes.isEmpty());
	}
	
	public void testIncompatiblePatternsEmpty() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValuesToPattern(pattern2);
		assertTrue(nodes.isEmpty());
	}
	
	public void testPatternAndConstantMatchNotEmpty() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValues("http://example.org/res42");
		assertFalse(nodes.isEmpty());
	}
	
	public void testPatternAndConstantNoMatchEmpty() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValues("http://example.org/thing42");
		assertTrue(nodes.isEmpty());
	}
	
	public void testAliasedPatternsNotEmpty() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValuesToPattern(pattern1aliased);
		assertFalse(nodes.isEmpty());
	}

	public void testExpressionAndConstantNotEmpty() {
		nodes.limitValues("foo");
		nodes.limitValuesToExpression(expression1);
		assertFalse(nodes.isEmpty());
	}
	
	public void testSameAttributeTwiceExpressionIsTrue() {
		nodes.limitValuesToColumn(table1foo);
		nodes.limitValuesToColumn(table1foo);
		assertEquals(Expression.TRUE, nodes.constraint());
	}
	
	public void testAttributeAndConstantExpressionIsEquality() {
		nodes.limitValues("foo");
		nodes.limitValuesToColumn(table1foo);
		assertEquals(Equality.createAttributeValue(table1foo, "foo"), 
				nodes.constraint());
	}
	
	public void testSameBlankNodeIDTwiceExpressionIsTrue() {
		nodes.limitValuesToBlankNodeID(fooBlankNodeID);
		nodes.limitValuesToBlankNodeID(fooBlankNodeID);
		assertEquals(Expression.TRUE, nodes.constraint());
	}
	
	public void testSamePatternTwiceExpressionIsTrue() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValuesToPattern(pattern1);
		assertEquals(Expression.TRUE, nodes.constraint());
	}
	
	public void testSameExpressionTwiceExpressionIsTrue() {
		nodes.limitValuesToExpression(expression1);
		nodes.limitValuesToExpression(expression1);
		assertEquals(Expression.TRUE, nodes.constraint());
	}
	
	public void testBlankNodeAndConstantMatchExpressionIsEquality() {
		nodes.limitValuesToBlankNodeID(fooBlankNodeID);
		nodes.limitTo(Node.createAnon(new AnonId("Foo@@42")));
		assertEquals(Equality.createAttributeValue(table1foo, "42"), 
				nodes.constraint());
	}
	
	public void testPatternAndConstantMatchExpressionIsEquality() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValues("http://example.org/res42");
		assertEquals(Equality.createAttributeValue(table1foo, "42"), 
				nodes.constraint());
	}
	
	public void testExpressionAndConstantExpressionIsEquality() {
		nodes.limitValues("foo");
		nodes.limitValuesToExpression(expression1);
		assertEquals(Equality.createExpressionValue(expression1, "foo"),
				nodes.constraint());
	}
	
	public void testTwoAttributesExpressionIsEquality() {
		nodes.limitValuesToColumn(table1foo);
		nodes.limitValuesToColumn(table1bar);
		assertEquals(Equality.createColumnEquality(table1foo, table1bar),
				nodes.constraint());
	}
	
	public void testEquivalentPatternsExpressionIsEquality() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValuesToPattern(pattern1aliased);
		assertEquals(Equality.createColumnEquality(table1foo, alias1foo), 
				nodes.constraint());
	}
	
	public void testTwoPatternsExpressionIsConcatEquality() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValuesToPattern(pattern3);
		assertEquals("Equality(" +
				"Concatenation(" +
						"Constant(http://example.org/res), " +
						"AttributeExpr(@@table1.foo@@)), " +
				"Concatenation(" +
						"Constant(http://example.org/res), " +
						"AttributeExpr(@@table1.foo@@), " +
						"Constant(/), " +
						"AttributeExpr(@@table1.bar@@)))",
				nodes.constraint().toString());
	}
	
	public void testTwoExpressionsTranslatesToEquality() {
		nodes.limitValuesToExpression(expression1);
		nodes.limitValuesToExpression(expression2);
		assertEquals(Equality.create(expression1, expression2), 
				nodes.constraint());
	}
	
	public void testEquivalentBlankNodeIDsExpressionIsEquality() {
		nodes.limitValuesToBlankNodeID(fooBlankNodeID);
		nodes.limitValuesToBlankNodeID(fooBlankNodeID2);
		assertEquals(Equality.createColumnEquality(table1foo, alias1foo),
				nodes.constraint());
	}
	
	public void testPatternEqualsAttribute() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValuesToColumn(table1bar);
		assertEquals("Equality(" +
				"AttributeExpr(@@table1.bar@@), " +
				"Concatenation(" +
						"Constant(http://example.org/res), " +
						"AttributeExpr(@@table1.foo@@)))",
				nodes.constraint().toString());
	}
	
	public void testExpressionEqualsAttribute() {
		nodes.limitValuesToExpression(expression1);
		nodes.limitValuesToColumn(table1bar);
		assertEquals(Equality.create(expression1, new ColumnExpr(table1bar)),
				nodes.constraint());
	}
	
	public void testExpressionEqualsPattern() {
		nodes.limitValuesToPattern(pattern1);
		nodes.limitValuesToExpression(expression1);
		assertEquals("Equality(" +
				"SQL(SHA1(table1.foo)), " +
				"Concatenation(" +
						"Constant(http://example.org/res), " +
						"AttributeExpr(@@table1.foo@@)))",
				nodes.constraint().toString());
	}
	
	public void testANYNodeDoesNotLimit() {
		nodes.limitTo(Node.ANY);
		nodes.limitTo(RDF.Nodes.type);
		assertFalse(nodes.isEmpty());
	}
	
	public void testVariableNodeDoesNotLimit() {
		nodes.limitTo(Node.createVariable("foo"));
		nodes.limitTo(RDF.Nodes.type);
		assertFalse(nodes.isEmpty());
	}
	
	public void testPatternDifferentColumnFunctionsUnsupported() {
		nodes.limitValuesToPattern(new Pattern("test/@@table1.foo|urlify@@").toTemplate(sqlConnection));
		nodes.limitValuesToPattern(new Pattern("test/@@table1.bar|urlencode@@").toTemplate(sqlConnection));
		assertFalse(nodes.isEmpty());
		assertTrue(nodes.isUnsupported());
	}
	
	public void testTranslatorUnsupported() {
		nodes.setUsesTranslator(Translator.IDENTITY);
		nodes.setUsesTranslator(new TranslationTable(ResourceFactory.createResource()).translator());
		assertFalse(nodes.isEmpty());
		assertTrue(nodes.isUnsupported());
	}
	
	public void testPatternWithColumnFunctionAndColumnUnsupported() {
		nodes.limitValuesToColumn(table1foo);
		nodes.limitValuesToPattern(new Pattern("@@table2.bar|urlify@@").toTemplate(sqlConnection));
		assertEquals("Equality(AttributeExpr(@@table1.foo@@), AttributeExpr(@@table2.bar@@))", 
				nodes.constraint().toString());
		assertTrue(nodes.isUnsupported());
	}
}
