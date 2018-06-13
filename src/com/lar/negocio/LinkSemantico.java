package com.lar.negocio;

import java.io.File;
import java.io.InputStream;

import javax.swing.JOptionPane;

public class LinkSemantico {
	
	private final String pathSilkJar = "resources/silk/silk.jar";
	public File linkSpecfile;

	public void foundSemanticLinks(){
		try {
			linkSpecfile = new File("/tmp/linkSpec.xml");
			Process p = Runtime.getRuntime().exec("java -DconfigFile="+linkSpecfile+" -jar "+pathSilkJar);

			//BufferedReader read = new BufferedReader(
				//	new InputStreamReader(p.getInputStream()));
			int termination = p.waitFor();

			// Then retrieve the process output
		    InputStream in = p.getInputStream();
		    InputStream err = p.getErrorStream();

		    byte b[]=new byte[in.available()];
		    in.read(b,0,b.length);

		    byte c[]=new byte[err.available()];
		    err.read(c,0,c.length);
		    
//		    if(p.isAlive()) {
		    if(termination == 0) {
		    	System.out.println("p: "+p.toString());
				JOptionPane.showMessageDialog(null,"Sucessfull Found Semantic Links!");
				p.destroy();
				System.out.println("p.destroy: "+p);
			}
           /* while (read.readLine().isEmpty()) {
                System.out.println("read: "+ read.readLine());
            }*/

		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
