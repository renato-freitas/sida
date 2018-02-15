package com.lar.visao;



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lar.modelo.Dataset;
import com.lar.negocio.Transforma;


public class TelaGerarDumpRDF extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel pCentral;

    JLabel lblArquivoR2rml = new JLabel("in:R2RML");
    JLabel lblArquivoNt = new JLabel("out:N-TRIPLE");
    JLabel lblUsuario = new JLabel("Usuário DB");
    JLabel lblSenha = new JLabel("Senah DB");
    JLabel lblDataset = new JLabel("Dataset DB");
    
    JTextField txtArquivoR2rml = new JTextField(20);
    JTextField txtArquivoNt = new JTextField(20);
    JTextField txtUsuario = new JTextField(20);
    JTextField txtSenha = new JTextField(20);
    JTextField txtDataset = new JTextField(20);
	
	JButton btnGerar = new JButton("Gerar");
	JButton btnSair = new JButton("sair");

	public TelaGerarDumpRDF(String fileR2rml) {
		super("SEMI - Gerar Dump RDF");

		JPanel pCentral = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		constraints.gridx = 0;
		constraints.gridy = 0;
		pCentral.add(lblArquivoR2rml, constraints);
		constraints.gridx = 1;
		pCentral.add(txtArquivoR2rml, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		pCentral.add(lblArquivoNt, constraints);

		constraints.gridx = 1;
        pCentral.add(txtArquivoNt, constraints);

        constraints.gridx = 0;
		constraints.gridy = 2;
		pCentral.add(lblDataset, constraints);

		constraints.gridx = 1;
		pCentral.add(txtDataset, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		pCentral.add(lblUsuario, constraints);

		constraints.gridx = 1;
		pCentral.add(txtUsuario, constraints);

        constraints.gridx = 0;
		constraints.gridy = 4;
		pCentral.add(lblSenha, constraints);

		constraints.gridx = 1;
        pCentral.add(txtSenha, constraints);
        
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		pCentral.add(btnGerar, constraints);
		btnGerar.addActionListener(new ButtonsListener());

		constraints.gridx = 1;
		constraints.gridy = 6;
		pCentral.add(btnSair, constraints);
		btnSair.addActionListener(new ButtonsListener());

		// set border for the panel
		pCentral.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Arquivo de entrada e saída"));

        txtArquivoR2rml.setText(fileR2rml);

		// add the panel to this frame
		add(pCentral);
		pack();
		setLocationRelativeTo(null);
	}

    public Dataset obtemDadosFormulario() {
		//Dataset ds = new Dataset();
		Dataset ds = TelaPrincipal.bdConexao;
		ds.setNomeDataset(txtDataset.getText());
		ds.setUsuario(txtUsuario.getText());
        ds.setSenha(txtSenha.getText());
		ds.setNomeArquivoSaida(txtArquivoNt.getText());
		return ds;
	}

	
	class ButtonsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton jb = (JButton) e.getSource();
			
			if(jb.getText() == "Gerar"){
                new Transforma(txtArquivoR2rml.getText(), obtemDadosFormulario());
                    //.setDataset(obtemDadosFormulario());;
                //System.out.println("Chegou na tela gerar dump com: "+
                //    txtArquivoR2rml.getText()+"e "+ txtArquivoNt.getText());
			}
			else {
				System.out.println(jb.getText());
				dispose();
			}
		}
	}

}
