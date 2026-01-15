package files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import frame.Main;


public class Files {
	
	public static boolean checkEmployee(String id) {
		String line;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/employees.txt")))){
			
			while((line = br.readLine()) != null) {
				if(line.equals(id))
					return true;
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
