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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class TelaEditarArquivoMapeamentoR2ML extends JFrame{

	private static final long serialVersionUID = 1L;

	private JFrame frame = new JFrame("To edit R2RML file");
	private JTextArea textArea = new JTextArea();

	private String storeAllString="";
	private String chooseAFile = "Choose a file";

	private JButton btnSalvar = new JButton("Save");
	private JButton saveCloseBtn = new JButton("Save Changes and Close");
	private JButton closeButton = new JButton("Close withou save");
	private JButton btnGerarDumpRDF = new JButton("Generate RDF Dump");
	private JComboBox<String> cbArquivosTTL = new JComboBox<String>();


	public TelaEditarArquivoMapeamentoR2ML(){
		panels();
		listarArquivoParaEdicao();
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
		rightPanel.add(cbArquivosTTL);
		rightPanel.add(btnSalvar);
		rightPanel.add(saveCloseBtn);
		rightPanel.add(closeButton);
		

		rightPanel.add(btnGerarDumpRDF);
		
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveBtn();
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
		
		cbArquivosTTL.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				carregaArquivoParaAreaDeTexto();
				
			}
		});

		btnGerarDumpRDF.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				chamarTelaGerarDumpRDF(cbArquivosTTL.getSelectedItem().toString());
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
			file = new File("/tmp/"+cbArquivosTTL.getSelectedItem().toString());
			out = new FileWriter(file);     
			out.write(textArea.getText());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listarArquivoParaEdicao(){
		
		File[] files = new File("/tmp").listFiles();
		cbArquivosTTL.addItem(chooseAFile);
		for (File file : files) {
		    if (file.isFile() & file.getName().endsWith(".ttl")) {
		        cbArquivosTTL.addItem(file.getName());
		    }
		}
	}
	
	private void carregaArquivoParaAreaDeTexto(){
		storeAllString = "";
		String nomeArquivoTtl = cbArquivosTTL.getSelectedItem().toString();
		try{    
			if(nomeArquivoTtl != chooseAFile){
				FileReader read = new FileReader("/tmp/"+nomeArquivoTtl);
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

	private void chamarTelaGerarDumpRDF(String in){
		new TelaGerarDumpRDF(in).setVisible(true);
	}

}
