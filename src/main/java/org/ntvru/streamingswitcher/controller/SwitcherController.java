package org.ntvru.streamingswitcher.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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
	public String index() {
		
		return "index";
	}
	
	@RequestMapping("/start")
	public ModelAndView start(ModelAndView modelAndView) {		
		
          if(service.startStreaming()) {
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
		
		   if(service.stopStreaming()) {
				modelAndView.addObject("message", "Streaming interrompido com sucesso.");
				modelAndView.setViewName("redirect:/");
		          }else {
		        	  modelAndView.addObject("message", "Ocorreu um problema ao interromper o streaming. Contate o administrador.");
		      		 modelAndView.setViewName("redirect:/");
		          }
		
		
		return modelAndView;
	}
	
	public ModelAndView test(ModelAndView modelAndView) {
		
		 if(service.testStreaming()) {
				modelAndView.addObject("message", "Streaming testado com sucesso.");
				modelAndView.setViewName("redirect:/");
		          }else {
		        	  modelAndView.addObject("message", "Ocorreu um problema ao testar o streaming. Contate o administrador.");
		      		 modelAndView.setViewName("redirect:/");
		          }
		
		return modelAndView;
	}
	 
}
