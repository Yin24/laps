package com.teamtwo.laps.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamtwo.laps.service.LeaveTypeService;
import com.teamtwo.laps.validator.UserValidator;
import com.teamtwo.laps.model.LeaveType;;

/**
 * Handles requests for the application staff pages.
 */
@Controller
@RequestMapping(value = "/admin/leavetype")
public class AdminLeaveTypeController {

	@Autowired
	private LeaveTypeService ltService;
	

	/**
	 * Renders the staff dashboard.
	 */

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView newLeavePage(HttpSession session) {
		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsAdmin()) {
				mav = new ModelAndView("leave-type-new", "leavetype", new LeaveType());
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
	public ModelAndView createNewLeavePage(@Valid @ModelAttribute ("leavetype") LeaveType leavetype, BindingResult result,
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors())
			return new ModelAndView("leave-type-new");

	
		ModelAndView mav = new ModelAndView();
		String message = "New leave type " + leavetype.getLeaveName() + " was successfully created.";

		ltService.createLeaveType(leavetype);
		mav.setViewName("redirect:/admin/leavetype/list");

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView leaveTypeListPage(HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsAdmin()) {

				mav = new ModelAndView("leave-type-list");
				ArrayList<LeaveType> leaveTypeList = ltService.findAllLeaveType();
				mav.addObject("leaveTypeList", leaveTypeList);
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editLeaveTypePage(@PathVariable Integer id, HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsAdmin()) {

				mav = new ModelAndView("leave-type-edit");
				LeaveType leaveType = ltService.findLeaveTypeById(id);
				mav.addObject("leaveType", leaveType);
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
	public ModelAndView editRole(@ModelAttribute @Valid LeaveType leave, BindingResult result, @PathVariable Integer id,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors())
			return new ModelAndView("leave-type-edit");

		ModelAndView mav = new ModelAndView("redirect:/admin/leavetype/list");
		String message = "leavetype was successfully updated.";

		ltService.changeLeaveType(leave);

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteRole(@PathVariable Integer id, final RedirectAttributes redirectAttributes,
			HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsAdmin()) {

				mav = new ModelAndView("redirect:/admin/leavetype/list");
				LeaveType leave = ltService.findLeaveTypeById(id);
				ltService.removeLeaveType(leave);
				String message = "The role " + leave.getLeaveType() + " was successfully deleted.";

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
