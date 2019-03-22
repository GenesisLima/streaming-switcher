package org.ntvru.streamingswitcher.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.ntvru.streamingswitcher.bean.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwitcherService2 {
	
	@Autowired
	private Script script;

	//private ProcessBuilder builder = new ProcessBuilder(script.getPath()+"/"+script.getScript());
	private ProcessBuilder builder;
	private volatile boolean isServiceActive;
//	private Thread thread;

	
	
	
	
	public boolean startStreaming() {
		//List<String> params = java.util.Arrays.asList("notepad","test");
		builder = new ProcessBuilder(script.getPath()+"/"+script.getScript());
		//builder = new ProcessBuilder(params);
		 final Boolean[] result = new Boolean[1];  
		 result[0] = false;
		 System.out.println("RESULT "+result);
//		thread = new Thread(new RunScript());
//		thread.start();
	Thread thread = new Thread(new Runnable() {
		
		@Override
		public void run() {

			try {
				builder.redirectErrorStream(true);
				Process process = builder.start();
				BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = "";
				while ((line = lineReader.readLine ()) != null) {
				    System.out.println ("Stdout: " + line);
				}
				result[0] = true;
			} catch (IOException e) {
				System.out.println("EXCEPTION "+e);
				result[0] = false;
				e.printStackTrace();
			}
			
		}
	});	
	    
		return result[0];
	}
	
	public boolean stopStreaming() {
		//TODO change this approach to something more organic
		List<String> params = java.util.Arrays.asList("/usr/bin/pgrep","ffmpeg");
		//List<String> params = java.util.Arrays.asList("notepad","test");
		builder = new ProcessBuilder(params);		
		Process process;
		try {
			builder.redirectErrorStream(true);
			process = builder.start();
			BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = lineReader.readLine ()) != null) {
				  System.out.println("LINE ON STOP "+line);
			   Runtime.getRuntime().exec("kill "+line);
			}
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
		
		return true;
	}
	



	public boolean isServiceRunning() {
		
		
		final Boolean[] isActive = new Boolean[1];      
	      //  isActive[0] = false;
		 
		 
		 
	   Thread thread = new Thread(new Runnable() {		
		@Override
		public void run() {
			List<String> params = java.util.Arrays.asList("/usr/bin/pgrep","ffmpeg");		
			//List<String> params = java.util.Arrays.asList("C:\\Windows\\System32\\qprocess.exe","C:\\Windows\\System32\\notepad.exe");	
			System.out.println("PARAMS "+params);
			ProcessBuilder builder = new ProcessBuilder(params);
			Process process;
			try {
				builder.redirectErrorStream(true);
				process = builder.start();
				BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = "";
				while ((line = lineReader.readLine ()) != null) {
					 System.out.println("LINE ON START BEFORE "+line);
					 System.out.println("LINE ON START BEFORE IS NOT NULL "+(line != null));
					 System.out.println("LINE ON START BEFORE IS BLANK "+!line.equals(""));
				  if(line != null || !line.equals("")) {
					  System.out.println("LINE ON START AFTER "+line);
					    isActive[0] = true;
					   
				  }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	});
	   thread.start();	 
	   System.out.println("IS ACTIVE "+isActive[0]);
      return isActive[0]; 
	}
	
	

}
