import java.io.*;

public class CopyFile{

   public static void main(String[] agrs) throws IOException{
      
      	 //Archivos con formato utf-8
    	FileInputStream in = null;
      	FileOutputStream out = null;

      	try{
      
      		in = new FileInputStream("input.txt");
			out = new FileOutputStream("output.txt");

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
		 		out.write(0x20AC);
		   		out.close();	 
		 	}
      	}
   }
}

