package org.ntvru.streamingswitcher.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.ntvru.streamingswitcher.customproperties.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwitcherService {
	
	@Autowired
	private Script script;

	//private ProcessBuilder builder = new ProcessBuilder(script.getPath()+"/"+script.getScript());
	private ProcessBuilder builder;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return true;
	}
	
	public boolean testStreaming() {

		return true;
	}
	//Old Implementation
//	private class RunScript implements Runnable{
//		private Process process;
//
//		@Override
//		public void run() {
//			String[] commands = {"/usr/local/bin/ffmpeg", "-f", "alsa", "-i", "default:CARD=CODEC", "-threads", "4", "-c:a", "libfdk_aac", "-b:a", "128k", "-aq", "0", "-ac",
//					"2", "-q:a", "330", "-cutoff", "15000", "-vn", "-f", "mpegts", "http://127.0.0.1:80/stream"};			
//			try {
//				process = Runtime.getRuntime().exec(commands);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//			BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//	        lineReader.lines().forEach(System.out::println);
//			
//		}
//		
//	}

}
