package com.teamtwo.laps.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.teamtwo.laps.javabeans.EmailSender;
import com.teamtwo.laps.javabeans.OvertimeList;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.Overtime;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.LeaveTypeService;
import com.teamtwo.laps.service.OvertimeService;
import com.teamtwo.laps.service.StaffMemberService;

@Controller
@RequestMapping(value = "/staff/comp")
public class StaffOvertimeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private StaffMemberService smService;
	
	@Autowired
	private LeaveService lService;
	
	@Autowired
	private LeaveTypeService lTypeService;
	
	@Autowired
	private OvertimeService otService;
	
	/**
	 * Renders the staff dashboard.
	 */
	
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public ModelAndView viewCompHistory(HttpSession session, @RequestParam(value = "hideClaimed", required = false) Optional<Integer> hideClaimed) {
		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");
		if (userSession == null || userSession.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}
		
		int staffId = userSession.getEmployee().getStaffId();
		
		List<Overtime> overtimes = otService.findOvertimeOfStaff(staffId);
		
		Boolean hide = false;
		if (hideClaimed.isPresent() && hideClaimed.get() == 1) {
			hide = true;
			overtimes = overtimes.stream().filter(ot -> ot.getLoggedHours() != ot.getClaimedHours()).collect(Collectors.toList());
		}
		
		ModelAndView modelAndView = new ModelAndView("comp-log-history");
		
		int totalLoggedHours = overtimes.stream().map(ot -> ot.getLoggedHours()).reduce(0, (a, b) -> a + b);
		int totalClaimedHours = overtimes.stream().map(ot -> ot.getClaimedHours()).reduce(0, (a, b) -> a + b);
		
		modelAndView.addObject("overtimes", overtimes);
		modelAndView.addObject("totalLoggedHours", totalLoggedHours);
		modelAndView.addObject("totalClaimedHours", totalClaimedHours);
		modelAndView.addObject("hide", hide);
		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteOvertime(HttpSession session, @PathVariable Integer id) {
		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");
		
		if (userSession == null || userSession.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}
		int staffId = userSession.getEmployee().getStaffId();
		
		otService.deleteOvertime(id);
		
		return new ModelAndView("redirect:/staff/comp/loghours?edit=1");
	}
	
	@RequestMapping(value = "/loghours", method = RequestMethod.GET)
	public ModelAndView compLogHours(HttpSession session, @RequestParam(required = false) Optional<String> edit) {
		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");

		if (userSession == null || userSession.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}
		int staffId = userSession.getEmployee().getStaffId();
		
		List<Overtime> overtimes = new ArrayList<>();
		
		if (edit.isPresent()) {
			overtimes = otService.findOvertimeOfStaff(staffId).stream()
					.filter(ot -> ot.getLoggedHours() != ot.getClaimedHours())
					.collect(Collectors.toList());
		} else {
			Overtime overtime = new Overtime();
			overtime.setLoggedHours(0);
			overtime.setDate(new Date());
			overtimes.add(overtime);
		}
		
		OvertimeList overtimeList = new OvertimeList();
		overtimeList.setOvertimes(overtimes);
		
		ModelAndView modelAndView = new ModelAndView("comp-log-hours", "logHours", overtimeList);
		if (edit.isPresent()) {
			modelAndView.addObject("edit", true);
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/loghours", params = "addRow", method = RequestMethod.POST)
	public ModelAndView compAddRowLogHours(@ModelAttribute("logHours") OvertimeList overtimeList) {
		
		List<Overtime> overtimes = overtimeList.getOvertimes();

		Overtime overtime = new Overtime();
		overtime.setLoggedHours(0);
		overtime.setDate(new Date());
		overtimes.add(overtime);
		
		OvertimeList newOvertimeList = new OvertimeList();
		newOvertimeList.setOvertimes(overtimes);
		
		ModelAndView modelAndView = new ModelAndView("comp-log-hours", "logHours", newOvertimeList);
		return modelAndView;
	}
	
	@RequestMapping(value="/loghours", params = "delRow", method = RequestMethod.POST)
	public ModelAndView compDelRowLogHours(@ModelAttribute("logHours") OvertimeList overtimeList) {
		
		List<Overtime> overtimes = overtimeList.getOvertimes();

		overtimes.remove(overtimes.size() - 1);
		
		OvertimeList newOvertimeList = new OvertimeList();
		newOvertimeList.setOvertimes(overtimes);
		
		ModelAndView modelAndView = new ModelAndView("comp-log-hours", "logHours", newOvertimeList);
		return modelAndView;
	}
	
	@RequestMapping(value="/loghours", params = "save", method = RequestMethod.POST)
	public ModelAndView saveLogHours(@ModelAttribute("logHours") OvertimeList overtimeList, HttpSession session, HttpServletRequest request) {
		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");
		if (userSession == null || userSession.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}
		int staffId = userSession.getEmployee().getStaffId();
		
		ModelAndView modelAndViewError = new ModelAndView("comp-log-hours", "logHours", overtimeList);
		
		for (Overtime overtime : overtimeList.getOvertimes()) {
			if (overtime.getLoggedHours() == null || overtime.getLoggedHours() == 0) {
				modelAndViewError.addObject("valError", "All hours must be filled and more than 0.");
				return modelAndViewError;
			}
			if (overtime.getDate() == null) {
				modelAndViewError.addObject("valError", "All dates must be filled.");
				return modelAndViewError;
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("comp-log-receipt");
		// Check if it is an edit request
		String edit = request.getParameter("edit");
		if (edit.equals("true")) {
			otService.updateOvertimes(overtimeList.getOvertimes());
//			modelAndView.addObject("totalHours", overtimeList.getOvertimes().get(0).getId());
//			return modelAndView;
			modelAndView.addObject("wasEdit", true);
		} else {
			overtimeList.setStaffId(staffId);
			overtimeList.resetAllHoursClaimed();
			otService.saveOvertimes(overtimeList.getOvertimes());
		}
		
		modelAndView.addObject("totalHours", overtimeList.getSumLoggedHours());
		
		// ----- EMAIL ------
		// Get manager email
//	String mgrEmail = "sa44lapsteamtwo+manager@gmail.com";
		StaffMember mgr =  smService.findStaff(userSession.getEmployee().getManagerId());
		String mgrEmail = mgr.getEmail();
		
		// set message
		int highestID = 0;
		List<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
		
		for (Leave leaveIter : leaves) {
			if (leaveIter.getLeaveId() > highestID) {
				highestID = leaveIter.getLeaveId();
			}
		}
		
		String basePath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		String url = basePath + "/manager/comp/history/" + userSession.getEmployee().getStaffId() + ".html";
		String message = "Dear " + mgr.getName() + ",\n"
				+ "Your subordinate, " + userSession.getEmployee().getName()
				+ " has applied for overtime. You can view the application here: \n"
				+ url;
		String subject = "Employee " + userSession.getEmployee().getName() + " has applied for compensation.";
		
		EmailSender.getEmailSender().addRecipient(mgrEmail).setMessage(message).setSubject(subject).send();
		// ----- END OF EMAIL ------
		
		return modelAndView;
	}
}