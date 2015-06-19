import java.io.*;

public class WriteToFile{
	

	public static void main(String[] args) throws IOException{

		InputStreamReader cin = null;
		FileWriter out = null;
		File f = null;

		try{
			cin = new InputStreamReader(System.in);
			f = new File("C:\\Users\\ernesto\\Desktop\\JAVA Io\\fromKeyboard.txt");
			out = new FileWriter(f);
			System.out.println("Enter characters, '-1' to quit");
			char line;
			do{
				line = (char)cin.read();
				out.write(line);

			}while(line != '-');
		}finally{

			if(cin != null){
				cin.close();
			}

			if(out!=null){
		   		out.close();	 
		 	}
		}

	}
}