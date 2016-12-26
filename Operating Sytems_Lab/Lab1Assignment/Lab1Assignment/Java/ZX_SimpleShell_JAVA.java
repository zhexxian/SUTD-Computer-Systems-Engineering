import java.io.*;
import java.util.ArrayList;

public class ZX_SimpleShell_JAVA {
	public static void main(String[] args) throws java.io.IOException {
		String commandLine;
		String[] commandParts;
		String[] lastCommand;
		ArrayList<String> commandHistory = new ArrayList<String>();
		ArrayList<String> historyIndex = new ArrayList<String> ();
		String line;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		File file;
		File currentpath =  new File(System.getProperty("user.dir"));
		File newpath;
		
		while (true) {

			// read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();
			commandParts = commandLine.split(" ");

			// if the user entered a return, just loop again
			if (commandLine.equals("")) {
				continue;
			}

			// change directory
			else if (commandParts[0].equals("cd")){
				
				if (commandLine.equals("cd")){
					newpath = new File(System.getProperty("user.home"));
				}

				else if (commandParts[1].equals("..")){
					file = new File(System.getProperty("user.dir")); 
					newpath = new File(file.getParent());
				}

				else {
					newpath = new File(currentpath.getAbsolutePath() + File.separator + commandParts[1]);
				}
				 
				if (newpath.isDirectory()){
					currentpath = newpath;
				}
					 
				else {
					System.out.println("error: new directory is invalid!" + newpath);
				}
				continue;
			}

			// command history
			else if (commandLine.equals("history")){
				for (int i=0; i<commandHistory.size(); i++){
					System.out.println(historyIndex.get(i)+" "+commandHistory.get(i));
				}
				continue;
			}

			// last called command
			else if (commandLine.equals("!!")){
				commandParts = commandHistory.get(commandHistory.size()-1).split(" ");
			}

			// call command from history index
			else if (historyIndex.contains(commandLine)){
				commandParts = commandHistory.get(Integer.parseInt(commandLine)).split(" ");
			}

			// store command history
			else {	
				commandHistory.add(commandLine);
				historyIndex.add(Integer.toString(commandHistory.size()-1));
			}

			// call command	
			try {
				ProcessBuilder pb = new ProcessBuilder(); 
				pb.command(commandParts); 
				pb.directory(currentpath);
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
		}
	}
}
