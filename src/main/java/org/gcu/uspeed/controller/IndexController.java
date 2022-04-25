package org.gcu.uspeed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/")
@Controller
public class IndexController {

	
	/**
	 * This method is used to display the index view
	 * @param model - used to pass model to view
	 * @return String (index)
	 */
	@ResponseBody
	@GetMapping("/")
    public String display(Model model) {
        //display index
        return "blank.html";
    }
}
