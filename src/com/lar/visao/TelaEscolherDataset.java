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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lar.modelo.Dataset;
import com.lar.negocio.Transforma;


public class TelaEscolherDataset extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel pCentral;

	JLabel lblServidorBD = new JLabel("DB Server");
	JLabel lblNomeDataset = new JLabel("Dataset name");
	JLabel lblNomeTabela = new JLabel("Table name");
	JLabel lblUsuario = new JLabel("User");
	JLabel lblSenha = new JLabel("Password");
	JLabel lblArquivoSaida = new JLabel("Outfile name");

	JTextField txtServidorBD = new JTextField(20);
	JTextField txtNomeDataset = new JTextField(20);
	JTextField txtNomeTabela = new JTextField(20);
	JTextField txtUsuario = new JTextField(20);
	JTextField txtSenha = new JTextField(20);
	JTextField txtArquivoSaida = new JTextField(20);

	JComboBox<String> cbServidorBD;

	JButton btnOK = new JButton("Ok");
	JButton btnSair = new JButton("Close");

	private String[] servidoresBD = { "Choose a DB-server", "MYSQL", "ORACLE", "POSTGRESQL", "MS-SQLSERVER" };

	public TelaEscolherDataset() {
		super("Choose Dataset");
		
		JPanel pCentral = new JPanel(new GridBagLayout());

		cbServidorBD = new JComboBox<String>(servidoresBD);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		constraints.gridx = 0;
		constraints.gridy = 0;
		pCentral.add(lblServidorBD, constraints);
		constraints.gridx = 1;
		
		pCentral.add(cbServidorBD, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		pCentral.add(lblNomeDataset, constraints);

		constraints.gridx = 1;
		pCentral.add(txtNomeDataset, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		pCentral.add(lblNomeTabela, constraints);

		constraints.gridx = 1;
		pCentral.add(txtNomeTabela, constraints);

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
		constraints.gridy = 5;
		pCentral.add(lblArquivoSaida, constraints);

		constraints.gridx = 1;
		pCentral.add(txtArquivoSaida, constraints);

		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		pCentral.add(btnOK, constraints);
		btnOK.addActionListener(new ButtonsListener());

		constraints.gridx = 1;
		constraints.gridy = 6;
		pCentral.add(btnSair, constraints);
		btnSair.addActionListener(new ButtonsListener());

		
		pCentral.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder(), "Painel de Login"));

		add(pCentral);
		pack();
		setLocationRelativeTo(null);
	}

	/**Obtém os dados do formulário para acessar o dataset.
	 * Retorna um Dataset preenchido.
	*/
	public Dataset obtemDadosFormulario() {
		
		Dataset ds = TelaPrincipal.bdConexao;
		ds.setServidorBD(cbServidorBD.getSelectedItem().toString());
		ds.setNomeDataset(txtNomeDataset.getText());
		ds.setNomeTabela(txtNomeTabela.getText());
		ds.setUsuario(txtUsuario.getText());
		ds.setSenha(txtSenha.getText());
		ds.setNomeArquivoSaida(txtArquivoSaida.getText());
		
		return ds;
	}

	class ButtonsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton botaoClicado = (JButton) e.getSource();
			if (botaoClicado.getText() == "Ok") {
				new Transforma(obtemDadosFormulario());
			} else {
				dispose();
			}

		}
	}
}
