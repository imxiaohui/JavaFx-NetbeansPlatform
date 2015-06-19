import java.io.*;


/*
	docs : http://tutorials.jenkov.com/java-io/file.html
*/
public class FileReaderExample{
	
	public static void main(String[] args){

		File javaFile = new File("C:/Users/ernesto/Desktop/JAVAInOut");
		
		//System.out.println(javaFile.isDirectory());
		javaFile.mkdir();
	}
}