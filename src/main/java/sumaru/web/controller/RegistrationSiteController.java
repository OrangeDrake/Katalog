package sumaru.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sumaru.persistence.service.UserService;
import sumaru.web.domain.UserDetails;

@Controller
@RequestMapping("/registration")
public class RegistrationSiteController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String getCurrentMenu(
			@RequestParam(value = "check", defaultValue = "0") int check,
			Model model) {

		model.addAttribute("userDetails", new UserDetails());
		model.addAttribute("check", check);

		return "/registration";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addNewUser(@ModelAttribute UserDetails userDetails, Model model) {

		if (userService.isUnique(userDetails.getName())) {
			userService.saveNewUser(userDetails);
			return "redirect:/profile";
		} else
			return "redirect:/registration?check=1";
	}	
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String authorize() {

		userService.authorize();

		return "redirect:/profile";
	}	

}
