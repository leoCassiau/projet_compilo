import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Scanner {

	/*
	BufferedReader buffer;
	
	public Scanner(String cheminFichier) {
		try {
			buffer = new BufferedReader(new FileReader(new File(cheminFichier)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public String nextToken(){
		String token = "";
		boolean stop = false;
		try {
			do {
				int t = buffer.read();
				String s = buffer.readLine();
				
				String[] c = s.split(" ");
				
				if(t == -1){
					stop = true;
				} else {
					
				}
		} while (!stop);	
	} catch (Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	} 
	}
	*/
	
	private int i = 0;
	public String nextToken() {
		//String[] t = {"S0", "->", "[", "a", "]", ".", "b", ",", ";" };
		String[] t = {"IDNTER", "->", "[", "ELTER", "]", ".", "ELTER", ",", ";"};
		return t[i++];
	}
	
	
}
