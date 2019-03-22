package org.ntvru.streamingswitcher.tasks;

public class StopServiceTask implements Runnable {

	  private TaskService taskService;
	  private boolean isRunning;
	  
	  
	  
	  
	public StopServiceTask(TaskService taskService) {
		super();
		this.taskService = taskService;
	}




	@Override
	public void run() {
	this.isRunning = this.taskService.stopService();
	 System.out.println("stop call on "+this.getClass().getName());
		
	}
}
