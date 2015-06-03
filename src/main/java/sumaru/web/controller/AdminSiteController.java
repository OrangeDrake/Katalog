package sumaru.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sumaru.persistence.service.AdService;
import sumaru.persistence.service.UserService;
import sumaru.web.domain.AdDetails;

@Controller
@RequestMapping("/admin")
public class AdminSiteController {

	@Autowired
	private UserService userService;

	@Autowired
	private AdService adService;

	@RequestMapping(method = RequestMethod.GET)
	public String getAllUsers(
			@RequestParam(value = "check", defaultValue = "0") int check,
			Model model) {

		model.addAttribute("allUsers", userService.getAllUsers());
		model.addAttribute("name", "admin");
		model.addAttribute("check", check);

		return "/admin";
	}

	@RequestMapping("/categories")
	public String getAllCategories(Model model) {

		model.addAttribute("allCategories", adService.getAllCategories());
		model.addAttribute("name", "admin");

		return "/adminCategories";
	}

	@RequestMapping("/user")
	public String getUser(@RequestParam(value = "id") long id, Model model) {

		List<AdDetails> ads = adService.getAdsbyUserId(id);

		if (ads.size() == 0)
			return "redirect:/admin?check=1";

		model.addAttribute("usersAds", ads);
		model.addAttribute("name", "admin");
		model.addAttribute("user_name", ads.get(0).getUser().getName());

		return "/adminUser";
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(@RequestParam(value = "id") long id) {

		userService.deleteUser(id);

		return "redirect:/admin";
	}

	@RequestMapping(value = "/ads", method = RequestMethod.GET)
	public String getAllAds(Model model) {

		model.addAttribute("allAds", adService.getRecentAds());
		model.addAttribute("name", "admin");

		return "/adminAds";
	}

	@RequestMapping(value = "/deleteAd", method = RequestMethod.POST)
	public String removeAd(@RequestParam("ad_id") long id) {

		adService.removeAd(id);

		return "redirect:/admin/ads";
	}

	@RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
	public String removeCategory(@RequestParam("cate_id") int id) {

		adService.removeCategory(id);

		return "redirect:/admin/categories";
	}

	@RequestMapping(value = "/renameCategory", method = RequestMethod.POST)
	public String renameCategory(@RequestParam("cate_id") int id,
			@RequestParam("cate_element") String element) {

		adService.saveCategory(id, element);

		return "redirect:/admin/categories";
	}

	@RequestMapping(value = "/adCategory", method = RequestMethod.POST)
	public String adtegory(@RequestParam("cate_element") String element) {

		adService.addCategory(element);

		return "redirect:/admin/categories";
	}

}
