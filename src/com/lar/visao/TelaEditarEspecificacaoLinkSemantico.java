package com.lar.visao;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.lar.util.Comum;

public class TelaEditarEspecificacaoLinkSemantico extends JFrame{

	private static final long serialVersionUID = 1L;

	private JFrame frame = new JFrame("To Edit LinkSpec.xml File");
	private JTextArea textArea = new JTextArea();

	private String storeAllString = "";
	private String nomeArquivoXml = "";
	private String chooseAFile = "Choose a file";
	
	private JLabel lblDatasetSource = new JLabel("Dataset Source");
	private JLabel lblDatasetTarget= new JLabel("Dataset Target");
	private JLabel lblEditarArquivoTmp= new JLabel("Choose file to edition");

	private JButton btnSalvar = new JButton("Save");
	private JButton closeButton = new JButton("Close without save");
	private JButton btnEspecificarLinks = new JButton("To specify Semantic links");
	private JComboBox<String> cbDatasetSource = new JComboBox<String>();
	private JComboBox<String> cbDatasetTarget = new JComboBox<String>();
	private JComboBox<String> cbArquivoEdicao = new JComboBox<String>();
	
	private JFileChooser caixa_dialogo;

	int editing;
	int count = 0;

	public TelaEditarEspecificacaoLinkSemantico() {
		panels();
		carregarCombos();
		carregarComboArquivosTemporarios();
		editing = 0;
	}

	private void panels(){        
		JPanel panel = new JPanel(new GridLayout(1,1));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel rightPanel = new JPanel(new GridLayout(15,0,10,10));
		rightPanel.setBorder(new EmptyBorder(15, 5, 5, 10));
		
		caixa_dialogo = new JFileChooser();

		JScrollPane scrollBarForTextArea=new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrollBarForTextArea); 
		frame.add(panel);
		frame.getContentPane().add(rightPanel,BorderLayout.EAST);
		rightPanel.add(btnSalvar);
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
				if(editing == 0) {
					++count;
					editing = 1;
				}
				saveBtn(); 
				//cbArquivoEdicao.removeAllItems();
				carregarComboArquivosTemporarios();
				System.out.println("editing: "+editing);
			}
		});
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();

			}
		});
		/*saveCloseBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				saveBtn();
				frame.dispose();

			}
		});
		*/
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
				editing = 0;
				System.out.println("Editing: "+editing);
			}
		});
		
		frame.setSize(1000, 650);
		frame.setVisible(true);   
		frame.setLocationRelativeTo(null);
	}

	private void saveBtn(){
		abrirDialogo();
	}
	
	public void abrirDialogo() {
		int retorno = caixa_dialogo.showSaveDialog(textArea);
        
        if (retorno == JFileChooser.APPROVE_OPTION)
        {
            File arquivo = caixa_dialogo.getSelectedFile();
            try
            {
                BufferedWriter grava = new BufferedWriter(new FileWriter(arquivo));
                grava.write(textArea.getText());
                grava.close();
            }
            catch (IOException e) 
            {               
                e.printStackTrace();
            }
        }   
	}

	public void carregarCombos(){
		File[] files = new File("/tmp").listFiles();
		cbDatasetSource.addItem(chooseAFile);
		cbDatasetTarget.addItem(chooseAFile);
		
		for (File file : files) {
		    if (file.isFile() & file.getName().endsWith(".nt")) {
		        cbDatasetSource.addItem(file.getName());
		        cbDatasetTarget.addItem(file.getName());
		    }
		}
	}
	public void carregarComboArquivosTemporarios(){
		File[] files = new File("/tmp").listFiles();
		cbArquivoEdicao.addItem(chooseAFile);
		
		for (File file : files) {
		    if (file.isFile() & file.getName().endsWith(".xml")) {
		        cbArquivoEdicao.addItem(file.getName());
		    }
		}
	}
	
	private void carregaArquivoParaAreaDeTexto(){
		storeAllString = "";
		nomeArquivoXml = cbArquivoEdicao.getSelectedItem().toString();
		try{    
			if(nomeArquivoXml != chooseAFile){
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
			if(dsSource != chooseAFile & dsTarget != chooseAFile){
				textArea.setText(templateLinkSpec(dsSource, dsTarget, "links.nt"));
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	private String templateLinkSpec(String dsSource, String dsTarget, String outFile){
		String source = Comum.cut_extension(dsSource);
		String target = Comum.cut_extension(dsTarget);
		return "<Silk>\n"+
  "<Prefixes>\n"+
  "    <Prefix id=\"rdf\" namespace=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/>\n"+
  "    <Prefix id=\"dbpp\" namespace=\"http://dbpedia.org/property/\"/>\n"+
  "    <Prefix id=\"dcterm\" namespace=\"http://purl.org/dc/terms/\"/>\n"+
  "    <Prefix id=\"dc\" namespace=\"http://purl.org/dc/elements/1.1/\"/>\n"+
  "    <Prefix id=\"owl\" namespace=\"http://www.w3.org/2002/07/owl#\"/>\n"+
  "    <Prefix id=\"foaf\" namespace=\"http://xmlns.com/foaf/0.1/\"/>\n"+
  "    <Prefix id=\"rdfs\" namespace=\"http://www.w3.org/2000/01/rdf-schema#\"/>\n"+
  "    <Prefix id=\"vocab\" namespace=\"http://ontologia_domain#\"/>\n"+
  "</Prefixes>\n"+
  "<DataSources>\n"+
  "    <Dataset id=\""+source+"\" type=\"file\">\n"+
  "        <Param name=\"file\" value=\""+dsSource+"\"/>\n"+
  "        <Param name=\"format\" value=\"N-Triples\"/>\n"+
  "        <Param name=\"graph\" value=\"\"/>\n"+
  "    </Dataset>\n"+
  "    <Dataset id=\""+target+"\" type=\"file\">\n"+
  "        <Param name=\"file\" value=\""+dsTarget+"\"/>\n"+
  "        <Param name=\"format\" value=\"N-Triples\"/>\n"+
  "        <Param name=\"graph\" value=\"\"/>\n"+
  "    </Dataset>\n"+
  "</DataSources>\n"+
  "<Interlinks>\n"+
  "    <Interlink id=\"task\">\n"+
  "        <SourceDataset dataSource=\""+source+"\" var=\"a\" typeUri=\"\">\n"+
  "            <RestrictTo>\n"+
  "                ?a ?p ?v .\n"+
  "            </RestrictTo>\n"+
  "        </SourceDataset>\n"+
  "        <TargetDataset dataSource=\""+target+"\" var=\"b\" typeUri=\"\">\n"+
  "            <RestrictTo>\n"+
  "                ?b ?p ?v .\n"+
  "            </RestrictTo>\n"+
  "        </TargetDataset>\n"+
        
  "        <LinkageRule linkType=\"owl:sameAs\">\n"+
  "            <Compare id=\"equality1\" required=\"false\" weight=\"1\" metric=\"equality\" threshold=\"0.0\" indexing=\"true\">\n"+
  "                <Input id=\"sourcePath1\" path=\"/sida:cpf\"/>\n"+
  "                <Input id=\"targetPath1\" path=\"/sida:cpf\"/>\n"+
  "            </Compare>"+
  "            <Filter/>\n"+
  "        </LinkageRule>\n"+
  "    </Interlink>\n"+
  "</Interlinks>\n"+
  "<Transforms>\n"+
  "</Transforms>\n"+
  "<Outputs>\n"+
  "    <Dataset id=\""+source+"_"+target+"\" type=\"file\">\n"+
  "        <Param name=\"file\" value=\"links.nt\"/>\n"+
  "        <Param name=\"format\" value=\"N-Triples\"/>\n"+
  "    </Dataset>\n"+
  "</Outputs>\n"+
"</Silk>";
	}
	
	/**Método para obter o nome da datasource sem a extensão do arquivo*/
	/*private String cut_extension(String in) {
		return in.substring(0, in.length() - 3);
	}*/
}

