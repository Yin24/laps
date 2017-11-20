package com.teamtwo.laps.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.teamtwo.laps.javabeans.EmailSender;
import com.teamtwo.laps.javabeans.OvertimeList;
import com.teamtwo.laps.model.Overtime;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.LeaveTypeService;
import com.teamtwo.laps.service.OvertimeService;
import com.teamtwo.laps.service.StaffMemberService;

@Controller
@RequestMapping(value = "/manager/comp")
public class ManagerOvertimeController {
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
	
	@RequestMapping(value = "/history/{id}", method = RequestMethod.GET)
	public ModelAndView viewCompHistory(HttpSession session, @PathVariable Integer id) {
		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");
		if (userSession == null || userSession.getSessionId() == null || userSession.getUser().getIsManager() == false) {
			return new ModelAndView("redirect:/home/login");
		}
		
//		int staffId = userSession.getEmployee().getStaffId();
		StaffMember staffMember = smService.findStaff(id);
		List<Overtime> overtimes = otService.findOvertimeOfStaff(id);
		
		Boolean hide = false;
		List<Overtime> newOvertimes = overtimes.stream().filter(ot -> !ot.getWasConfirmed()).collect(Collectors.toList());
		
		ModelAndView modelAndView = new ModelAndView("comp-manager-history");
		
		int totalLoggedHours = newOvertimes.stream().map(ot -> ot.getLoggedHours()).reduce(0, (a, b) -> a + b);
		int totalClaimedHours = newOvertimes.stream().map(ot -> ot.getClaimedHours()).reduce(0, (a, b) -> a + b);
		
		modelAndView.addObject("viewStaff", staffMember);
		modelAndView.addObject("overtimes", newOvertimes);
		modelAndView.addObject("totalLoggedHours", totalLoggedHours);
		modelAndView.addObject("totalClaimedHours", totalClaimedHours);
		modelAndView.addObject("hide", hide);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/approve/{id}", method = RequestMethod.GET)
	public ModelAndView compLogHours(HttpSession session, @PathVariable Integer id) {
		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");

		if (userSession == null || userSession.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}
//		int staffId = userSession.getEmployee().getStaffId();
		
		StaffMember staffMember = smService.findStaff(id);

		List<Overtime> overtimes = new ArrayList<>();
		
		overtimes = otService.findOvertimeOfStaff(id).stream()
				.filter(ot -> !ot.getWasConfirmed())
				.collect(Collectors.toList());
		
		OvertimeList overtimeList = new OvertimeList();
		overtimeList.setOvertimes(overtimes);
		
		ModelAndView modelAndView = new ModelAndView("comp-manager-approving", "logHours", overtimeList);
		
		modelAndView.addObject("viewStaff", staffMember);
//		modelAndView.addObject("overtimes", newOvertimes);
//		modelAndView.addObject("totalLoggedHours", totalLoggedHours);
//		modelAndView.addObject("totalClaimedHours", totalClaimedHours);
//		modelAndView.addObject("hide", hide);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/approve", method = RequestMethod.POST)
	public ModelAndView saveLogHours(@ModelAttribute("logHours") OvertimeList overtimeList, HttpSession session, HttpServletRequest request) {
		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");
		if (userSession == null || userSession.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}
//		int staffId = userSession.getEmployee().getStaffId();
		
//		ModelAndView modelAndViewError = new ModelAndView("comp-log-hours", "logHours", overtimeList);
		
//		for (Overtime overtime : overtimeList.getOvertimes()) {
//			if (overtime.getLoggedHours() == null || overtime.getLoggedHours() == 0) {
//				modelAndViewError.addObject("valError", "All hours must be filled and more than 0.");
//				return modelAndViewError;
//			}
//			if (overtime.getDate() == null) {
//				modelAndViewError.addObject("valError", "All dates must be filled.");
//				return modelAndViewError;
//			}
//		}
//		
		ModelAndView modelAndView = new ModelAndView("comp-manager-receipt");
		// Check if it is an edit request
//		String edit = request.getParameter("edit");
//		if (edit.equals("true")) {
		List<Overtime> overtimes = overtimeList.getOvertimes();
		for (Overtime overtime : overtimes) {
			overtime.setWasConfirmed(true);
			if (overtime.getApproved() == null) {
				overtime.setApproved(false);
			} else {
				overtime.setApproved(true);
			}
		}
		
		otService.updateOvertimes(overtimes);
//			modelAndView.addObject("totalHours", overtimeList.getOvertimes().get(0).getId());
//			return modelAndView;
//			modelAndView.addObject("wasEdit", true);
//		} else {
//			overtimeList.setStaffId(staffId);
//			overtimeList.resetAllHoursClaimed();
//			otService.saveOvertimes(overtimeList.getOvertimes());
//		}
		
		Integer totalApproved = overtimes.stream().filter(x -> x.getApproved()).map(m -> m.getLoggedHours()).reduce(0, ((a, b) -> a + b));
		Integer totalRejected = overtimes.stream().filter(x -> !x.getApproved()).map(m -> m.getLoggedHours()).reduce(0, ((a, b) -> a + b));
		
		modelAndView.addObject("totalApproved", totalApproved);
		modelAndView.addObject("totalRejected", totalRejected);
//		modelAndView.addObject("totalHours", overtimeList.getSumLoggedHours());
		
		// ----- EMAIL ------

				// Get manager email
//				String staffEmail = "sa44lapsteamtwo+staff@gmail.com";
				StaffMember staff = smService.findStaff(overtimes.get(0).getStaffId());
				 String staffEmail = staff.getEmail();

				// set message
				String basePath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				String url = basePath + "/staff/comp/history.html";
				String emailMsg = "Dear " + staff.getName() + ",\n" + "Your manager, " + userSession.getEmployee().getName()
						+ " has reviewed your overtime claims. You can view the details here: \n" + url;
				String subject = "Manager " + userSession.getEmployee().getName() + " has reviewed your overtime claim.";

				EmailSender.getEmailSender().addRecipient(staffEmail).setMessage(emailMsg).setSubject(subject).send();
				// ----- END OF EMAIL ------
		
		return modelAndView;
	}
}