package org.ntvru.streamingswitcher.service;

import org.ntvru.streamingswitcher.tasks.StartServiceTask;
import org.ntvru.streamingswitcher.tasks.StopServiceTask;
import org.ntvru.streamingswitcher.tasks.TaskIsServiceRunning;
import org.ntvru.streamingswitcher.tasks.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwitcherService {

	@Autowired
	private TaskService taskService;
	
	
	public boolean startService() {
		
		StartServiceTask task = new StartServiceTask(taskService);
		if(!isServiceRunning()) {
			synchronized(this) {
				Thread thread = new Thread(task,"startService");
				thread.start();
				}
		}
	
		return true;
	}

	public boolean isServiceRunning() {
		TaskIsServiceRunning task = new TaskIsServiceRunning(taskService);
		synchronized(this) {
		Thread thread = new Thread(task,"isServiceRunning");
		thread.start();
		}
		return task.isRunning();
	}

	public boolean stopService() {
		StopServiceTask task = new StopServiceTask(taskService);
		if(!isServiceRunning()) {
			synchronized(this) {
				Thread thread = new Thread(task,"startService");
				thread.interrupt();
				
				}
		}
	
		return true;
	}
	
	

}
