import java.io.*;

public class ZX_SimpleShell_startingCode {
	public static void main(String[] args) throws java.io.IOException {
		String commandLine;
		String[] commandParts;
		String line;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			// read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();
			commandParts = commandLine.split(" ");
			
			// TODO: adding a history feature

			// if the user entered a return, just loop again
			if (commandLine.equals("")) {
				continue;
			}

			try {
				ProcessBuilder pb = new ProcessBuilder(); 
				pb.command(commandParts); 
				Process p = pb.start();
				InputStream is = p.getInputStream();  
				InputStreamReader isr = new InputStreamReader(is);
        		BufferedReader br = new BufferedReader(isr);
        
        		while ((line = br.readLine()) != null) {
            		System.out.println(line);
        		}
        	} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			

			// TODO: creating the external process and executing the command in that process
			// TODO: modifying the shell to allow changing directories
		}
	}
}