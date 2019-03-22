package org.ntvru.streamingswitcher.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TaskIsServiceRunning implements Runnable {

	  private TaskService taskService;
	  private boolean isRunning;

	  
	
	public TaskIsServiceRunning(TaskService taskService) {
		super();
		this.taskService = taskService;
	}



	@Override
	public void run() {
	this.isRunning =  this.taskService.isServiceRunning();
	System.out.println("IS RUNNING "+this.isRunning);
	}



	public boolean isRunning() {
		return isRunning;
	}



	
	

}
