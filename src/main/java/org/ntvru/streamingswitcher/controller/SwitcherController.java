package org.ntvru.streamingswitcher.controller;

import org.ntvru.streamingswitcher.service.SwitcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class SwitcherController {

	@Autowired
	SwitcherService service;
	
    @RequestMapping("/")	
	public ModelAndView index(ModelAndView modelAndView) {
    	
    	 modelAndView.addObject("isServiceRunning", service.isServiceRunning());   
    	 
    	 modelAndView.setViewName("index"); 
    	
		return modelAndView;
	}
	
	@RequestMapping("/start")
	public ModelAndView start(ModelAndView modelAndView) {		
		
          if(service.startService()) {
		modelAndView.addObject("message", "Streaming iniciado com sucesso.");
		modelAndView.setViewName("redirect:/");
          }else {
        	  modelAndView.addObject("message", "Ocorreu um problema ao iniciar o streaming. Contate o administrador.");
      		 modelAndView.setViewName("redirect:/");
          }

          return modelAndView;
		
	}
	
	@RequestMapping("/stop")
	public ModelAndView stop(ModelAndView modelAndView) {
		        System.out.println("stop call on "+this.getClass().getName());
		   if(service.stopService()) {
				modelAndView.addObject("message", "Streaming interrompido com sucesso.");
				modelAndView.setViewName("redirect:/");
		          }else {
		        	 modelAndView.addObject("message", "Ocorreu um problema ao interromper o streaming. Contate o administrador.");
		      		 modelAndView.setViewName("redirect:/");
		          }
		
		
		return modelAndView;
	}
	

	 
}
