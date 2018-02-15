package com.lar.negocio;



import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;



public class LinkSemantico {
	private final String pathSilkJar = "resources/silk/silk.jar";
	private File linkSpec;


	public void encontrarLinksSemanticos(){
		try {
			//linkSpec = new File("resources/silk/linkSpec.xml");
			linkSpec = new File("/tmp/linkSpec.xml");
			Process p = Runtime.getRuntime().exec("java -DconfigFile="+linkSpec+" -jar "+pathSilkJar);
			System.out.println(p);
			BufferedReader read = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
			p.waitFor();
		    // Then retreive the process output
		    InputStream in = p.getInputStream();
		    InputStream err = p.getErrorStream();

		    byte b[]=new byte[in.available()];
		    in.read(b,0,b.length);
		    //System.out.println(new String("byte b:"+b));

		    byte c[]=new byte[err.available()];
		    err.read(c,0,c.length);
		    //System.out.println(new String("byte c:"+c));
		    
			
            while (read.ready()) {
                System.out.println(read.readLine());
            }

		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
