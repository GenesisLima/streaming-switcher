package org.ntvru.streamingswitcher.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.ntvru.streamingswitcher.bean.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.core.net.SyslogOutputStream;

@Configuration
public class TaskService {

	@Autowired
	private Script script;

	//private ProcessBuilder builder = new ProcessBuilder(script.getPath()+"/"+script.getScript());
	private ProcessBuilder builder;
	private boolean isServiceActive;	
//	private Thread thread;

	
	
	
	
	public boolean startService() {
		List<String> params = java.util.Arrays.asList("ffmpeg","-f","alsa","-i","default:CARD=CODEC", "-threads", "4", "-c:a", "libfdk_aac","-b:a", "128k", "-aq", "0", "-ac", "2", "-q:a", "330", "-cutoff", "15000", "-vn", "-f", "mpegts",  "http://127.0.0.1:80/stream");
		// List<String> params = java.util.Arrays.asList(script.getPath()+"/"+"./"+script.getScript());
		//builder = new ProcessBuilder(params);
		//List<String> params = java.util.Arrays.asList("notepad","test");
		builder = new ProcessBuilder(params);
		String nome = Thread.currentThread().getName();
		if(!isServiceRunning()) {
		synchronized(this) {			
		try {
				builder.redirectErrorStream(true);
				Process process = builder.start();
				System.out.println("PROCESS ID ON START "+getPidOfProcess(process));
				BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = "";
				while ((line = lineReader.readLine ()) != null) {
				    System.out.println ("Stdout: " + line);
				}
				this.isServiceActive = true;
//				Thread.sleep(30000);
//				process.destroyForcibly();
			} catch (IOException e) {
				System.out.println("EXCEPTION "+e);
				this.isServiceActive  = false;
				e.printStackTrace();
			} 
//		catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		}
	return this.isServiceActive;
	}
	
	public boolean stopService() {
		//TODO change this approach to something more organic
		List<String> params = java.util.Arrays.asList("/usr/bin/pgrep","ffmpeg");
		
		builder = new ProcessBuilder(params);		
		Process process;
		 System.out.println("stop call on "+this.getClass().getName());
		 System.out.println("service is running? "+isServiceRunning());
	
		try {
			builder.redirectErrorStream(true);
			process = builder.start();
			System.out.println("PROCESSO "+process.getInputStream());
			//System.out.println("PROCESS ID ON STOP "+getPidOfProcess(process));
			BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			System.out.println("Output of running is: "+Arrays.toString(params.toArray()));		
			String pid = lineReader.readLine();
			System.out.println("LINE READER "+pid);
			 
			//if (lineReader.readLine() != null && !lineReader.readLine().equals("")) {
				 System.out.println("LINE ON STOP "+line);
//			    // Runtime.getRuntime().exec("kill "+line);
				 killProcess(pid);
			     this.isServiceActive  = false;
	
			} catch (IOException e) {
				System.out.println("EXCEPTION "+e);
				
				e.printStackTrace();
			}
		
			
	
		return isServiceActive;
	}
	



	public boolean isServiceRunning() {		
		
		List<String> params = java.util.Arrays.asList("/usr/bin/pgrep","ffmpeg");		
		
			ProcessBuilder builder = new ProcessBuilder(params);
			
				
					
				try {
					builder.redirectErrorStream(true);
					Process process = builder.start();
					
					BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = "";

					if(lineReader.readLine() != null) {
						System.out.println("LINE "+line);
						 if(line != null || !line.equals(""))
						this.isServiceActive = true;
					}else {
						this.isServiceActive = false;
					}
					} catch (IOException e) {
						System.out.println("EXCEPTION "+e);
						
						e.printStackTrace();
					}
			
					
		
	   
	   System.out.println("IS ACTIVE "+this.isServiceActive);

	   return this.isServiceActive; 
	}
	
	private void killProcess(String pid) {
		System.out.println("KILLING PROCESS "+pid);
		List<String> params = java.util.Arrays.asList("/bin/kill", "-9", pid);
		ProcessBuilder builder = new ProcessBuilder(params);
		System.out.println("Output of running is: "+Arrays.toString(params.toArray())+" on "+this.getClass().getName());
		
		//synchronized(this) {			
		try {
			System.out.println("Directory "+builder.directory());
			builder.redirectErrorStream(true);
			Process process = builder.start();
			System.out.println("IS ALIVE "+process.isAlive());			
			
			BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
//			while ((line = lineReader.readLine ()) != null) {
//				System.out.println("LINE "+line);
//			  if(line != null || !line.equals(""))
//				this.isServiceActive = true;
//			else
//				this.isServiceActive = false;
//			}
			if(lineReader.readLine() != null) {
				System.out.println("LINE "+line);
				 if(line != null || !line.equals(""))
				this.isServiceActive = true;
			} else {
				this.isServiceActive = false;
			}
			} catch (IOException e) {
				System.out.println("EXCEPTION "+e);
				
				e.printStackTrace();
			}
		//}
	}
	
	
	private synchronized long getPidOfProcess(Process p) {
	    long pid = -1;

	    try {
	      if (p.getClass().getName().equals("java.lang.UNIXProcess")) {
	        Field f = p.getClass().getDeclaredField("pid");
	        f.setAccessible(true);
	        pid = f.getLong(p);
	        f.setAccessible(false);
	      }
	    } catch (Exception e) {
	      pid = -1;
	    }
	    return pid;
	  }
	
}
