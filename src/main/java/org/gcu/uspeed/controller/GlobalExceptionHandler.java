package org.gcu.uspeed.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

//This is used to handle exceptions
@ControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * used to catch all exceptions
	 *
	 * @param Exception - we use this to get the exception message
	 * @Return ModelAndView (ErrorPage) - we want the user to see a friendly error page when something breaks
	 *
	 **/
	@PostMapping("/doLogin")
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex)
	{
		//use ModelAndView to display a friendly error page
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Error Page");
		model.addObject("error", "Error - " + ex.getMessage() + ". The devs are looking into this.");
		model.setViewName("ErrorPage");
		return model;
	}
}