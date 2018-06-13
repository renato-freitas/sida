package com.lar.visao;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lar.modelo.Dataset;
import com.lar.negocio.LinkSemantico;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	public static Dataset bdConexao;

	JPanel pCentral;

	JComboBox<String> cbServidorBD;
	
	JButton btnEscolherDataset = new JButton("Choose Dataset");
	JButton btnEditarMapeamento = new JButton("Edit Mapping");
	JButton btnCriarLinksSemanticos = new JButton("Semantic Links");
	JButton btnResolucaoIdentidade = new JButton("Identity Resolution");
	JButton btnSair = new JButton("Close");

	public TelaPrincipal() {
		super("SIDA - Sistema de Integração de Dados Semi-Automático");

		bdConexao = new Dataset();
		
		JPanel pCentral = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		constraints.gridx = 0;
		constraints.gridy = 0;
		pCentral.add(btnEscolherDataset, constraints);
		btnEscolherDataset.addActionListener(new ButtonsListener());
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		pCentral.add(btnEditarMapeamento, constraints);
		btnEditarMapeamento.addActionListener(new ButtonsListener());
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		pCentral.add(btnResolucaoIdentidade, constraints);
		btnResolucaoIdentidade.addActionListener(new ButtonsListener());
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		pCentral.add(btnCriarLinksSemanticos, constraints);
		btnCriarLinksSemanticos.addActionListener(new ButtonsListener());
		
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		pCentral.add(btnSair, constraints);
		btnSair.addActionListener(new ButtonsListener());

		pCentral.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Menu"));

		add(pCentral);
		pack();
		setLocationRelativeTo(null);
	}

	
	class ButtonsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton botaoClicado = (JButton) e.getSource();
			
			if(botaoClicado.getText() == "Choose Dataset"){
				new TelaEscolherDataset().setVisible(true);
			}
			else if(botaoClicado.getText() == "Edit Mapping"){
				new TelaEditarArquivoMapeamentoR2ML();
			}
			else if(botaoClicado.getText() == "Semantic Links"){
				LinkSemantico ls = new LinkSemantico();
				ls.foundSemanticLinks();
			}
			else if(botaoClicado.getText() == "Identity Resolution"){
				new TelaEditarEspecificacaoLinkSemantico();
			}
			else {
				System.out.println("***Sida Closed!");
				dispose();
			}
		}
	}
}
