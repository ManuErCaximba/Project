/*
 * AbstractController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import domain.Configuration;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import services.ConfigurationService;

@Controller
public class AbstractController {

	@Autowired
	ConfigurationService configurationService;

	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMismatchException(final MissingServletRequestParameterException oops) {
		return new ModelAndView("redirect:/");
	}

	// Panic handler ----------------------------------------------------------
	@ModelAttribute
	public void everyRequest(final WebRequest request, final Model model) {
		final Configuration conf = this.configurationService.findAll().iterator().next();
		String language = LocaleContextHolder.getLocale().getLanguage();

		model.addAttribute("configuration", conf);
		model.addAttribute("language", language);
	}

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

}
