package org.ntvru.streamingswitcher.tasks;

public class StartServiceTask implements Runnable{

	
	  private TaskService taskService;
	  private boolean isRunning;
	  
	  
	  
	  
	public StartServiceTask(TaskService taskService) {
		super();
		this.taskService = taskService;
	}




	@Override
	public void run() {
	this.isRunning = this.taskService.startService();
		
	}
	  
	  
}
