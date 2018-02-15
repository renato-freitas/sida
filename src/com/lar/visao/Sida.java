package com.lar.visao;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class Sida {
	public static void main(String[] args){
		try {
			System.out.println("***Sida Started!");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TelaPrincipal().setVisible(true);
			}
		});
	}
}
