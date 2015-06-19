import java.io.*;

public class CopyFile2{

   public static void main(String[] agrs) throws IOException{
      
        //Archivos con formato utf-16
    	FileReader in = null;
      	FileWriter out = null;

      	try{
      
      		in = new FileReader("input.txt");
			out = new FileWriter("output2.txt");

			int c;
			while((c=in.read()) != -1){
	   			out.write(c);
			}
      	}
	    finally{
	        if(in!=null){
			    in.close();	 
			}
		 	if(out!=null){
		 		out.write(0x24B62);
		   		out.close();	 
		 	}
      	}
   }
}
