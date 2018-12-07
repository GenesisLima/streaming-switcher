package org.ntvru.streamingswitcher.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.ntvru.streamingswitcher.bean.Script;
import org.ntvru.streamingswitcher.command.Command;
import org.ntvru.streamingswitcher.command.IsServiceActiveCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwitcherService {
	
	@Autowired
	private Script script;

	//private ProcessBuilder builder = new ProcessBuilder(script.getPath()+"/"+script.getScript());
	private ProcessBuilder builder;
	private volatile boolean isServiceActive;
//	private Thread thread;

	
	
	
	
	public boolean startStreaming() {
		builder = new ProcessBuilder(script.getPath()+"/"+script.getScript());
		boolean result = false;
//		thread = new Thread(new RunScript());
//		thread.start();
		
		try {
			builder.redirectErrorStream(true);
			Process process = builder.start();
			BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = lineReader.readLine ()) != null) {
			    System.out.println ("Stdout: " + line);
			}
			result = true;
		} catch (IOException e) {
			System.out.println("EXCEPTION "+e);
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean stopStreaming() {
		//TODO change this approach to something more organic
		List<String> params = java.util.Arrays.asList("/usr/bin/pgrep","ffmpeg");
		builder = new ProcessBuilder(params);		
		Process process;
		try {
			builder.redirectErrorStream(true);
			process = builder.start();
			BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = lineReader.readLine ()) != null) {
			   Runtime.getRuntime().exec("kill "+line);
			}
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
		
		return true;
	}
	
	public boolean testStreaming() {
		
		
		return true;
	}
	


	public boolean isServiceRunning() {
		 final Boolean[] isActive = new Boolean[1];      
	         isActive[0] = true;
	  System.out.println("IS ACTIVE "+isActive[0]);
	   Thread thread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			List<String> params = java.util.Arrays.asList("/usr/bin/pgrep","ffmpeg");			
			ProcessBuilder builder = new ProcessBuilder(params);
			Process process;
			try {
				builder.redirectErrorStream(true);
				process = builder.start();
				BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = "";
				while ((line = lineReader.readLine ()) != null) {
				  if(line != null || !line.equals("")) {
					  System.out.println("SERVICE "+line);
					    isActive[0] = true;
					    System.out.println("IS ACTIVE "+isActive);
				  }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	});
	   thread.start();	   
      return isActive[0]; 
	}

}
