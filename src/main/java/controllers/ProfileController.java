/*
 * ProfileController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import domain.Actor;
import domain.Administrator;
import domain.Author;
import domain.Reviewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import security.LoginService;
import security.UserAccount;
import services.*;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService actorService;

	@Autowired
	private ReviewerService reviewerService;

	@Autowired
	private AuthorService authorService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView myInformation() {
		final ModelAndView result = new ModelAndView("profile/display");
		final Actor user = this.actorService.getActorLogged();
		final UserAccount userAccount = LoginService.getPrincipal();

		String username = user.getUserAccount().getUsername();

		result.addObject("actor", user);
		result.addObject("nickname", username);

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("REVIEWER")) {
			Reviewer reviewer;
			reviewer = this.reviewerService.findOne(user.getId());
			Assert.notNull(reviewer);
			result.addObject("keywords", reviewer.getKeywords());
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("AUTHOR")) {
			Author author;
			author = this.authorService.findOne(user.getId());
			Assert.notNull(author);
			result.addObject("score", author.getScore());
		}

		return result;
	}

	private ModelAndView forbiddenOperation() {
		return new ModelAndView("redirect:/");
	}

}
