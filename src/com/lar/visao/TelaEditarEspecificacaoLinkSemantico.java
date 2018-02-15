package com.lar.visao;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class TelaEditarEspecificacaoLinkSemantico extends JFrame{

	private static final long serialVersionUID = 1L;

	private JFrame frame = new JFrame("Editar Arquivo LinkSpec");
	private JTextArea textArea = new JTextArea();

	private String storeAllString="";
	
	private JLabel lblDatasetSource = new JLabel("Dataset Source");
	private JLabel lblDatasetTarget= new JLabel("Dataset Target");
	private JLabel lblEditarArquivoTmp= new JLabel("Escolher arquivo para edição");

	private JButton saveCloseBtn = new JButton("Salvar as mudanças e fechar");
	private JButton closeButton = new JButton("Sair sem salvar");
	private JButton btnSalvar = new JButton("Salvar");
	private JButton btnEspecificarLinks = new JButton("Especificar Links");
	private JComboBox<String> cbDatasetSource = new JComboBox<String>();
	private JComboBox<String> cbDatasetTarget = new JComboBox<String>();
	private JComboBox<String> cbArquivoEdicao = new JComboBox<String>();


	public TelaEditarEspecificacaoLinkSemantico() {
		panels();
		carregarCombos();
		carregarComboArquivosTemporarios();
	}

	

	private void panels(){        
		JPanel panel = new JPanel(new GridLayout(1,1));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel rightPanel = new JPanel(new GridLayout(15,0,10,10));
		rightPanel.setBorder(new EmptyBorder(15, 5, 5, 10));

		JScrollPane scrollBarForTextArea=new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrollBarForTextArea); 
		frame.add(panel);
		frame.getContentPane().add(rightPanel,BorderLayout.EAST);
		rightPanel.add(btnSalvar);
		rightPanel.add(saveCloseBtn);
		rightPanel.add(closeButton);
		rightPanel.add(lblDatasetSource);
		rightPanel.add(cbDatasetSource);
		rightPanel.add(lblDatasetTarget);
		rightPanel.add(cbDatasetTarget);
		rightPanel.add(btnEspecificarLinks);
		rightPanel.add(lblEditarArquivoTmp);
		rightPanel.add(cbArquivoEdicao);
		
		btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveBtn();
				cbArquivoEdicao.removeAllItems();
				carregarComboArquivosTemporarios();
			}
		});
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();

			}
		});
		saveCloseBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				saveBtn();
				frame.dispose();

			}
		});
		
		cbDatasetSource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//carregaArquivoParaAreaDeTexto();
				
			}
		});
		
		cbDatasetTarget.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//carregaArquivoParaAreaDeTexto();
				
			}
		});
		cbArquivoEdicao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				carregaArquivoParaAreaDeTexto();
			}
		});
		
		btnEspecificarLinks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarTemplateLinkSpec();
			}
		});
		
		frame.setSize(1000, 650);
		frame.setVisible(true);   
		frame.setLocationRelativeTo(null);
	}

	private void saveBtn(){
		File file = null;
		FileWriter out=null;
		try {
			
			file = new File("/tmp/linkSpec.xml");
			out = new FileWriter(file);     
			out.write(textArea.getText());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregarCombos(){
		File[] files = new File("/tmp").listFiles();
		cbDatasetSource.addItem("Selecione um arquivo");
		cbDatasetTarget.addItem("Selecione um arquivo");
		
		for (File file : files) {
		    if (file.isFile() & file.getName().endsWith(".nt")) {
		        cbDatasetSource.addItem(file.getName());
		        cbDatasetTarget.addItem(file.getName());
		    }
		}
	}
	public void carregarComboArquivosTemporarios(){
		File[] files = new File("/tmp").listFiles();
		cbArquivoEdicao.addItem("Selecione um arquivo");
		
		
		for (File file : files) {
		    if (file.isFile() & file.getName().endsWith(".xml")) {
		        cbArquivoEdicao.addItem(file.getName());
		    }
		}
	}
	
	private void carregaArquivoParaAreaDeTexto(){
		storeAllString = "";
		String nomeArquivoXml = cbArquivoEdicao.getSelectedItem().toString();
		try{    
			if(nomeArquivoXml != "Selecione um arquivo"){
				FileReader read = new FileReader("/tmp/"+nomeArquivoXml);
				Scanner scan = new Scanner(read);
				while(scan.hasNextLine()){
					String temp=scan.nextLine()+"\n";
					storeAllString=storeAllString+temp;
				}
				textArea.setText(storeAllString);
				scan.close();
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	private void gerarTemplateLinkSpec(){
		storeAllString = "";
		String dsSource = cbDatasetSource.getSelectedItem().toString();
		String dsTarget = cbDatasetTarget.getSelectedItem().toString();
		try{    
			if(dsSource != "Selecione um arquivo" & dsTarget != "Selecione um arquivo"){
				textArea.setText(templateLinkSpec(dsSource, dsTarget, "links.nt"));
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	private String templateLinkSpec(String dsSource, String dsTarget, String outFile){
		return "<Silk>\n"+
  "<Prefixes>\n"+
  "    <Prefix id=\"rdf\" namespace=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/>\n"+
  "    <Prefix id=\"dbpp\" namespace=\"http://dbpedia.org/property/\"/>\n"+
  "    <Prefix id=\"dcterm\" namespace=\"http://purl.org/dc/terms/\"/>\n"+
  "    <Prefix id=\"dc\" namespace=\"http://purl.org/dc/elements/1.1/\"/>\n"+
  "    <Prefix id=\"owl\" namespace=\"http://www.w3.org/2002/07/owl#\"/>\n"+
  "    <Prefix id=\"foaf\" namespace=\"http://xmlns.com/foaf/0.1/\"/>\n"+
  "    <Prefix id=\"rdfs\" namespace=\"http://www.w3.org/2000/01/rdf-schema#\"/>\n"+
  "    <Prefix id=\"dbpediaowl\" namespace=\"http://dbpedia.org/ontology/\"/>\n"+
  "    <Prefix id=\"linkedmdb\" namespace=\"http://data.linkedmdb.org/resource/movie/\"/>\n"+
  "</Prefixes>\n"+
  "<DataSources>\n"+
  "    <Dataset id=\"DBpedia\" type=\"file\">\n"+
  "        <Param name=\"file\" value=\""+dsSource+"\"/>\n"+
  "        <Param name=\"format\" value=\"N-TRIPLE\"/>\n"+
  "    </Dataset>\n"+
  "    <Dataset id=\"linkedmdb\" type=\"file\">\n"+
  "        <Param name=\"file\" value=\""+dsTarget+"\"/>\n"+
  "        <Param name=\"format\" value=\"N-TRIPLE\"/>\n"+
  "    </Dataset>\n"+
  "</DataSources>\n"+
    
  "<Interlinks>\n"+
  "    <Interlink id=\"movies\">\n"+
  "        <SourceDataset dataSource=\"DBpedia\" var=\"a\">\n"+
  "            <RestrictTo>\n"+
  "                ?a ?p ?v .\n"+
  "            </RestrictTo>\n"+
  "        </SourceDataset>\n"+
  "        <TargetDataset dataSource=\"linkedmdb\" var=\"b\">\n"+
  "            <RestrictTo>\n"+
  "                ?b ?p ?v .\n"+
  "            </RestrictTo>\n"+
  "        </TargetDataset>\n"+
        
  "        <LinkageRule linkType=\"owl:sameAs\">\n"+
  "            <Aggregate id=\"unnamed_7\" required=\"false\" weight=\"1\" type=\"min\">\n"+
  "            <Compare id=\"unnamed_6\" required=\"false\" weight=\"1\" metric=\"levenshteinDistance\" threshold=\"0.0\"\n"+
                   "indexing=\"true\">\n"+
  "                <TransformInput id=\"unnamed_8\" function=\"lowerCase\">\n"+
  "                    <Input id=\"unnamed_1\" path=\"?a/foaf:name\"/>\n"+
  "                </TransformInput>\n"+
  "                <TransformInput id=\"unnamed_9\" function=\"lowerCase\">\n"+
  "                    <Input id=\"unnamed_2\" path=\"?b/rdfs:label\"/>\n"+
  "                </TransformInput>\n"+
  "                <Param name=\"minChar\" value=\"0\"/>\n"+
  "                <Param name=\"maxChar\" value=\"z\"/>\n"+
  "            </Compare>\n"+
  
  "            <Compare id=\"unnamed_5\" required=\"false\" weight=\"1\" metric=\"date\" threshold=\"400.0\" indexing=\"true\">\n"+
  "                <Input id=\"unnamed_4\" path=\"?a/dbpediaowl:releaseDate\"/>\n"+
  "                <Input id=\"unnamed_3\" path=\"?b/linkedmdb:initial_release_date\"/>\n"+
  "            </Compare>\n"+
  "            </Aggregate>\n"+
  "            <Filter/>\n"+
  "        </LinkageRule>\n"+
  "    </Interlink>\n"+
  "</Interlinks>\n"+
  "<Transforms>\n"+
  "</Transforms>\n"+
  "<Outputs>\n"+
  "    <Dataset id=\"DBpedia\" type=\"file\">\n"+
  "        <Param name=\"file\" value=\"links.nt\"/>\n"+
  "        <Param name=\"format\" value=\"N-TRIPLE\"/>\n"+
  "    </Dataset>\n"+
  "</Outputs>\n"+
"</Silk>";
	}
}

