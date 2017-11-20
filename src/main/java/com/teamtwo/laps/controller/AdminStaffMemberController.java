package com.teamtwo.laps.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.model.User;

import com.teamtwo.laps.service.StaffMemberService;
import com.teamtwo.laps.service.UserService;

@RequestMapping(value = "/admin/staff")
@Controller
public class AdminStaffMemberController {

	@Autowired
	private UserService uService;

	@Autowired
	private StaffMemberService smService;

	/**
	 * USER CRUD OPERATIONS
	 * 
	 * @return
	 */

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView staffListPage(HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsAdmin()) {

				mav = new ModelAndView("staff-list");
				ArrayList<StaffMember> staffList = smService.findAllStaff();
				mav.addObject("stafflist", staffList);
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}
		return mav;
	}
	
		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView newUserPage(HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsAdmin()) {

				mav = new ModelAndView("staff-new", "staff", new StaffMember());
				List<StaffMember> sList = smService.findAllStaff();
				
				sList = sList.stream().filter(staff -> uService.findUserByStaffId(staff.getStaffId()).getIsManager()).collect(Collectors.toList());
				
				mav.addObject("mlist", sList);
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid @ModelAttribute ("staff") StaffMember staff, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			List<StaffMember> sList = smService.findAllStaff();
			
			sList = sList.stream().filter(staff1 -> uService.findUserByStaffId(staff1.getStaffId()).getIsManager()).collect(Collectors.toList());
			
			ModelAndView modelAndView = new ModelAndView("staff-new");
			modelAndView.addObject("mlist", sList);
			return modelAndView;
		}
			

		ModelAndView mav = new ModelAndView();
		String message = "New staff " + staff.getStaffId() + " was successfully created.";

		smService.createStaff(staff);
		mav.setViewName("redirect:/admin/staff/list");

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editUserPage(@PathVariable Integer id, HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsAdmin()) {

				mav = new ModelAndView("staff-edit");
				StaffMember staff = smService.findStaff(id);
				mav.addObject("staff", staff);
				ArrayList<StaffMember> sList = smService.findAllStaffExcept(us.getEmployee().getStaffId());
				mav.addObject("mlist", sList);
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editUser(@ModelAttribute @Valid StaffMember staff, BindingResult result,
			@PathVariable Integer id, final RedirectAttributes redirectAttributes) {

		if (result.hasErrors())
			return new ModelAndView("staff-edit");

		ModelAndView mav = new ModelAndView("redirect:/admin/staff/list");
		String message = "Staff was successfully updated.";

		smService.createStaff(staff);

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable Integer id, final RedirectAttributes redirectAttributes,
			HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsAdmin()) {

				mav = new ModelAndView("redirect:/admin/staff/list");
				StaffMember staff = smService.findStaff(id);
				smService.removeStaff(staff);
				String message = "The staff " + staff.getStaffId() + " was successfully deleted.";

				redirectAttributes.addFlashAttribute("message", message);
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}
		return mav;
	}

}
