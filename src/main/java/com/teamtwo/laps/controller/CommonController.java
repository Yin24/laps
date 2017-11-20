package com.teamtwo.laps.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.model.User;
import com.teamtwo.laps.service.StaffMemberService;
import com.teamtwo.laps.service.UserService;


@Controller
@RequestMapping(value = "/home")
public class CommonController {

	@Autowired
	private StaffMemberService smService;

	@Autowired
	private UserService uService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String logic(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@RequestMapping(value = "/badlogin", method = RequestMethod.GET)
	public String logic2(Model model) {
		model.addAttribute("user", new User());
		return "bad-login";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ModelAndView authenticate(@ModelAttribute User user, HttpSession session, BindingResult result) {
		ModelAndView mav = new ModelAndView("login");
		if (result.hasErrors())
			return mav;
		UserSession us = new UserSession();
		if (user.getUserId() != null && user.getPassword() != null) {
			
			try {
				
				User u = uService.authenticate(user.getUserId(), user.getPassword());
				us.setUser(u);
				// PUT CODE FOR SETTING SESSION ID
				us.setSessionId(session.getId());
				us.setEmployee(smService.findStaffById(us.getUser().getStaffId()));
				ArrayList<StaffMember> subordinates = smService.findSubordinates(us.getUser().getStaffId());
				if (subordinates != null) {
					us.setSubordinates(subordinates);

				}
				mav = new ModelAndView("redirect:/staff/dashboard");
				
			} catch (NullPointerException e) {
				return mav = new ModelAndView("redirect:/home/badlogin");
			}
			
			
		} else {
			return mav;
		}
		session.setAttribute("USERSESSION", us);
		return mav;
	}

}
