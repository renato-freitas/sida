package com.lar.negocio;


import java.io.File;

//import org.silkframework.Silk;

public class Linka {

	String ex;
	File linkSpecFile;
	File outputFile;
	File projectFile;
	String exampleDir;
	
	public Linka(){
		//String exampleDir = URLDecoder.decode(getClass().getClassLoader().getResource("silk").getFile(), "UTF-8");
		//ex = URLDecoder.decode(getClass().getClassLoader().getResourceAsStream("resources/silk/"));
		
			exampleDir = new String("resources/silk/");
			linkSpecFile = new File(exampleDir, "linkSpec.xml");
			
			projectFile = new File(exampleDir, "project.zip");
			outputFile.delete();
		
	}
	
	


	public void silkando(){
		try {
			//Silk.executeFile(linkSpecFile, null, 1, true);
			outputFile = new File(exampleDir, "links.nt");
			System.out.println("teste 01***");
			outputFile.exists();
			//Source.fromFile(outputFile, "UTF-8").getLines().size();
		} catch (Exception  e) {
			e.printStackTrace();
		}
		
		
	}

}
