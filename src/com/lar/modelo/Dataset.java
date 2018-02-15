package com.lar.modelo;

public class Dataset {
	private long id;
	private String nomeDataset;
	private String nomeTabela;
	private String usuario;
	private String senha;
	private String servidorBD;
	private String nomeArquivoSaida;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNomeDataset() {
		return nomeDataset;
	}
	public void setNomeDataset(String nomeDataset) {
		this.nomeDataset = nomeDataset;
	}
	public String getNomeTabela() {
		return nomeTabela;
	}
	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getServidorBD() {
		return servidorBD;
	}
	public void setServidorBD(String servidorBD) {
		this.servidorBD = servidorBD;
	}
	public String getNomeArquivoSaida() {
		return nomeArquivoSaida;
	}
	public void setNomeArquivoSaida(String nomeArquivoSaida) {
		this.nomeArquivoSaida = nomeArquivoSaida;
	}
}
