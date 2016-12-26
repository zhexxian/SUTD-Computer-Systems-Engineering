package week5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class FileOperation {
	private static File currentDirectory = new File(System.getProperty("user.dir"));
	private static ArrayList<String> listOfFiles;
	private static ArrayList<String> listOfMatching;
	public static void main(String[] args) throws java.io.IOException {

		String commandLine;

		BufferedReader console = new BufferedReader
				(new InputStreamReader(System.in));

		while (true) {
			// read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();

			// clear the space before and after the command line
			commandLine = commandLine.trim();

			// if the user entered a return, just loop again
			if (commandLine.equals("")) {
				continue;
			}
			// if exit or quit
			else if (commandLine.equalsIgnoreCase("exit") | commandLine.equalsIgnoreCase("quit")) {
				System.exit(0);
			}

			// check the command line, separate the words
			String[] commandStr = commandLine.split(" ");
			ArrayList<String> command = new ArrayList<String>();
			for (int i = 0; i < commandStr.length; i++) {
				command.add(commandStr[i]);
			}

			// implement code to handle create here
			if (command.get(0).equals("create")){
				Java_create(currentDirectory, command.get(1));
				continue;
			}
			// implement code to handle delete here
			else if (command.get(0).equals("delete")){
				Java_delete(currentDirectory, command.get(1));
				continue;
			}
			// implement code to handle display here
			else if (command.get(0).equals("display")){
				Java_cat(currentDirectory, command.get(1));
				continue;
			}
			// implement code to handle list here
			else if (command.get(0).equals("list")){
				if (command.size()==1){
					Java_ls(currentDirectory,null,null);
				}
				else if (command.size()==2){
					Java_ls(currentDirectory, command.get(1), null);
				}
				else{
					Java_ls(currentDirectory, command.get(1), command.get(2));
				}
				continue;
			}

			// implement code to handle find here
			else if (command.get(0).equals("find")){
				if (Java_find(currentDirectory, command.get(1))){
					System.out.println("list of matching files");
					Java_find_print(currentDirectory, command.get(1));
				}
				else{
					System.out.println("no such file exists");
				}
				continue;
			}

			// TODO: implement code to handle tree here
			else if (command.get(0).equals("tree")){
				if (command.size()==1){
					Java_tree(currentDirectory, -1, null);
				}
				else if (command.size()==2){
					Java_tree(currentDirectory, Integer.parseInt(command.get(1)), null);
				}
				else{
					Java_tree(currentDirectory, Integer.parseInt(command.get(1)), command.get(2));
				}
				continue;
			}
			// other commands
			ProcessBuilder pBuilder = new ProcessBuilder(command);
			pBuilder.directory(currentDirectory);
			try{
				Process process = pBuilder.start();
				// obtain the input stream
				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);

				// read what is returned by the command
				String line;
				while ( (line = br.readLine()) != null)
					System.out.println(line);

				// close BufferedReader
				br.close();
			}
			// catch the IOexception and resume waiting for commands
			catch (IOException ex){
				System.out.println(ex);
				continue;
			}
		}
	}

	/**
	 * Create a file
	 * @param dir - current working directory
	 * @param command - name of the file to be created
	 */
	public static void Java_create(File dir, String name) {
		// create a file
		File file = new File(dir, name);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Delete a file
	 * @param dir - current working directory
	 * @param name - name of the file to be deleted
	 */
	public static void Java_delete(File dir, String name) {
		// delete a file
		File file = new File(dir, name);
		file.delete(); 
	}

	/**
	 * Display the file
	 * @param dir - current working directory
	 * @param name - name of the file to be displayed
	 */
	public static void Java_cat(File dir, String name) {
		// display a file
		File file = new File(dir, name);
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			BufferedReader in = new BufferedReader(fileReader);
			String line;
			while((line = in.readLine())!= null){
				System.out.println(line);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to sort the file list
	 * @param list - file list to be sorted
	 * @param sort_method - control the sort type
	 * @return sorted list - the sorted file list
	 */
	private static File[] sortFileList(File[] list, String sort_method) {
		// sort the file list based on sort_method
		// if sort based on name
		if (sort_method.equalsIgnoreCase("name")) {
			Arrays.sort(list, new Comparator<File>() {
				public int compare(File f1, File f2) {
					return (f1.getName()).compareTo(f2.getName());
				}
			});
		}
		else if (sort_method.equalsIgnoreCase("size")) {
			Arrays.sort(list, new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Long.valueOf(f1.length()).compareTo(f2.length());
				}
			});
		}
		else if (sort_method.equalsIgnoreCase("time")) {
			Arrays.sort(list, new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
				}
			});
		}
		return list;
	}

	/**
	 * List the files under directory
	 * @param dir - current directory
	 * @param function - control the list type
	 * @param sort_method - control the sort type
	 */
	public static void Java_ls(File dir, String display_method, String sort_method) {
		// list files
		File[] list = dir.listFiles();
		if (display_method==null&&sort_method==null){
			for (File file:list){
				System.out.println(file.getName());
			} 
		}
		// list file properties
		else if (display_method.equalsIgnoreCase("property")){
			if (sort_method!=null){
				list = sortFileList(list, sort_method);
			}
			for (File file:list){
					System.out.println(file.getName()
							+"    Size: "+file.length()+"    "
							+"    Last Modified: "+(new Date(file.lastModified())).toString());
				} 
		}
	}

	/**
	 * Find files based on input string
	 * @param dir - current working directory
	 * @param name - input string to find in file's name
	 * @return flag - whether the input string is found in this directory and its subdirectories
	 */
	public static boolean Java_find(File dir, String name) {
		boolean flag = false;
		File[] list = dir.listFiles();	
		FileOperation.listOfFiles = new ArrayList<String>();
		FileOperation.listOfMatching = new ArrayList<String>();

		// call method to get a comprehensive list of files in directory and sub-directory

		listOfAllFiles(dir);
		
		// check matching files against the input keyword 'name'

		for(String filename:FileOperation.listOfFiles){
			if(filename.contains(name)){
				flag=true;
				listOfMatching.add(filename);
			}
		}

		// find files
		return flag;
	}

	// method to get a comprehensive list of files in directory and sub-directory

	public static void listOfAllFiles(File dir){
		File[] list = dir.listFiles();	
		for(File file:list){
			if (file.isDirectory()){
				FileOperation.listOfAllFiles(file);
			}
			else {
				listOfFiles.add(file.getAbsolutePath()); 
			}
		} 
	}

	// print list of matching files

	public static void Java_find_print(File dir, String name) {
		for(String filename:FileOperation.listOfMatching){
			System.out.println(filename);
		}
	}
	/**
	 * Print file structure under current directory in a tree structure
	 * @param dir - current working directory
	 * @param depth - maximum sub-level file to be displayed
	 * @param sort_method - control the sort type
	 */
	public static void Java_tree(File dir, int depth, String sort_method) {
		// print file tree, start with offset set to 1
		recursive_tree(dir, depth, sort_method, 1);
	}

	public static void recursive_tree(File dir, int depth, String sort_method, int offset){
		// get list of all files in directory
		File[] dirList = dir.listFiles();
		// check for condition
		if(offset<=0){
			System.out.println("Error: offset must be positive");
		}
		else {
			// print the blank space according to offset amount
			for (int i=1; i<offset; i++){
				System.out.print(" ");
			}
			// print |- if the for second level directory onwards
			if (offset>1){
				System.out.print("|-");
			}
			System.out.println(dir.getName());
			// check for depth, or print everything if depth is not specified
			while(offset<depth||depth==-1){
				// recursive function
				if(dir.isDirectory()){
					offset++;
					// sort the list according to property
					if(sort_method!=null){
						dirList = sortFileList(dirList, sort_method);
					}
					for(File file:dirList){
						recursive_tree(file,depth,sort_method,offset);
					}
					break;
				}
				else{
					break;
				}
			}
		}
	}
}