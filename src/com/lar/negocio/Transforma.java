package com.lar.negocio;


import com.lar.modelo.Dataset;

public class Transforma {
	public String[] comando;// = new String[3];//{"bash", "-c", ""};
	private Dataset dataset;// = new Dataset();

	public Dataset getDataset(){
		return this.dataset; 
	}
	public void setDataset(Dataset dadosDb){
		this.dataset = dadosDb;
	}
	
	
	public Transforma(Dataset ds){
		//dataset = new Dataset();
		comando = new String[3];
		comando[0] = "bash";
		comando[1] = "-c";
		//dadosBD = ds;
		//tableToRML(getDataset());
		tableToRML(ds);
	}
	public Transforma(String in_ttl, Dataset ds){
		//dataset = new Dataset();
		comando = new String[3];
		comando[0] = "bash";
		comando[1] = "-c";
		rmlToNTriple(in_ttl, ds);
	}
	

	public void tableToRML(Dataset dataset){
		try {
			if(dataset.getServidorBD() == "MYSQL"){
				comando[2] = mysqlStringConnectionToMapping(dataset);	
			}
			Runtime.getRuntime().exec(comando);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}	
	
	public void rmlToNTriple(String in, Dataset ds){
		System.out.println("dentro de rml-to-ntriple");
		try {
			System.out.println("dentro do ''try de rml-to-ntriple");
			comando[2] = mysqlStringConnectionToDump(in, ds);
			if(comando[2] != ""){
				System.out.println("***.generate_dump string ok");
			}
			Runtime.getRuntime().exec(comando);
			System.out.println("método(rml-to-ntriple: comando dump to ntriple: "+comando);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	public String mysqlStringConnectionToMapping(Dataset ds){
		
		return "resources/d2rqlib/generate-mapping"+
			   " -u "+ds.getUsuario()+
			   " -p "+ds.getSenha()+
			   " -d com.mysql.jdbc.Driver --r2rml"+
			   " --tables "+ds.getNomeTabela()+ 
			   " jdbc:mysql://localhost/"+ds.getNomeDataset()+
			   " > /tmp/"+ds.getNomeArquivoSaida()+".ttl";
			   //" > resources/d2rqlib/"+ds.getNomeArquivoSaida()+".ttl";
	}

	public String mysqlStringConnectionToDump(String in_ttl, Dataset ds){
		System.out.println("Dentro de string dump-rdf");
		System.out.println("Dados do bd: "+ds);
		String config =
		"resources/d2rqlib/dump-rdf"+
				" -u "+ds.getUsuario()+
				" -p "+ds.getSenha()+
				" -f n-triple"+
				" -j jdbc:mysql://localhost/"+ds.getNomeDataset()+
				" /tmp/"+in_ttl+
				" > /tmp/"+ds.getNomeArquivoSaida()+".nt";
		System.out.println("***string config dump-rdf:\n"+config);
		return config;
	}
}
