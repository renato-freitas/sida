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

	JLabel lblServidorBD = new JLabel("Servidor de BD");
	JLabel lblNomeDataset = new JLabel("Nome do Dataset");
	JLabel lblNomeTabela = new JLabel("Nome da tabela");
	JLabel lblUsuario = new JLabel("Usuário");
	JLabel lblSenha = new JLabel("Senha");
	JLabel lblArquivoSaida = new JLabel("Nome do arquivo de saída");

	JTextField txtServidorBD = new JTextField(20);
	JTextField txtNomeDataset = new JTextField(20);
	JTextField txtNomeTabela = new JTextField(20);
	JTextField txtUsuario = new JTextField(20);
	JTextField txtSenha = new JTextField(20);
	JTextField txtArquivoSaida = new JTextField(20);

	JComboBox<String> cbServidorBD;

	JButton btnOK = new JButton("ok");
	JButton btnSair = new JButton("sair");

	private String[] servidoresBD = { "Escolhar um servidor", "MYSQL", "ORACLE", "POSTGRES", "MS-SQLSERVER" };

	public TelaEscolherDataset() {
		super("Escolher Dataset");

		JPanel pCentral = new JPanel(new GridBagLayout());

		cbServidorBD = new JComboBox<String>(servidoresBD);
		//cbServidorBD.addActionListener(new ComboBoxListener());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		constraints.gridx = 0;
		constraints.gridy = 0;
		pCentral.add(lblServidorBD, constraints);
		constraints.gridx = 1;
		//pCentral.add(txtServidorBD, constraints);
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

		// set border for the panel
		pCentral.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder(), "Painel de Login"));

		// add the panel to this frame
		add(pCentral);
		pack();
		setLocationRelativeTo(null);
	}

	/**Obtem os dados do formulário
	 * Retorna um Dataset preenchido.
	*/
	public Dataset obtemDadosFormulario() {
		//Dataset ds = new Dataset();
		//TelaPrincipal.bdConexao.setServidorBD(servidorBD);
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
			if (botaoClicado.getText() == "ok") {
				System.out.println(botaoClicado.getText());

				//Dataset dadosDoForm = obtemDadosFormulario();
				System.out.println(obtemDadosFormulario().getServidorBD());
				System.out.println(obtemDadosFormulario());
				
				//new Transforma().setDataset(obtemDadosFormulario());
				new Transforma(obtemDadosFormulario());
				
			} else {
				//System.out.println(botaoClicado.getText());
				dispose();
			}

		}
	}

}
