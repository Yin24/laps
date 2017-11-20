package com.teamtwo.laps.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.xpath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.HashMap;
import java.util.List;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.LiveBeansView;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamtwo.laps.service.HolidayService;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.OvertimeService;
import com.teamtwo.laps.service.StaffMemberService;

import com.teamtwo.laps.javabeans.Approve;
import com.teamtwo.laps.javabeans.DashboardBean;
import com.teamtwo.laps.javabeans.EmailSender;
import com.teamtwo.laps.javabeans.LeavePeriodCalculator;
import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.javabeans.ManagerPath;
import com.teamtwo.laps.javabeans.MovementBean;
import com.teamtwo.laps.model.Holiday;
import com.teamtwo.laps.model.Leave;

import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.model.User;

/**
 * Handles requests for the application staff pages.
 */
@Controller
@RequestMapping(value = "/manager")
public class ManagerController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private LeaveService lService;

	@Autowired
	private StaffMemberService smService;

	@Autowired
	private HolidayService hService;

	@Autowired
	private OvertimeService otService;

	private final int DASHBOARD_NUM_TO_SHOW = 3;

	/**
	 * Renders the staff dashboard.
	 */
	@RequestMapping(value = "/dashboard")
	public ModelAndView home(HttpSession session) {

		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");

		if (userSession == null || userSession.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}

		int staffId = userSession.getEmployee().getStaffId();
		User user = userSession.getUser();

		if (!user.getIsManager()) {
			return new ModelAndView("redirect:/staff/dashboard");
		}

		StaffMember staffMember = smService.findStaffById(staffId);
		ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
		ArrayList<StaffMember> subordinates = smService.findSubordinates(staffId);
		ArrayList<Leave> subordinatesLeaves = new ArrayList<>();

		for (StaffMember staff : subordinates) {
			subordinatesLeaves.addAll(staff.getAppliedLeaves().stream()
					.filter(al -> al.getStatus() == LeaveStatus.PENDING || al.getStatus() == LeaveStatus.UPDATED)
					.collect(Collectors.toList()));
		}

		// Sort leaves by leaveId, reversed (s1, s2) -> s2 compare s1
		subordinatesLeaves = (ArrayList<Leave>) subordinatesLeaves.stream()
				.sorted((s1, s2) -> s2.getLeaveId().compareTo(s1.getLeaveId())).collect(Collectors.toList());

		List<Holiday> holidays = hService.findAllHoliday();
		ModelAndView modelAndView = new ModelAndView("manager-dashboard");
		modelAndView = DashboardBean.getDashboard(modelAndView, DASHBOARD_NUM_TO_SHOW, staffMember, leaves, holidays,
				otService);

		ManagerPath mp = ManagerPath.DASHBOARD;
		session.setAttribute("USERPATH", mp);

		int pendingToShow = subordinatesLeaves.size() > DASHBOARD_NUM_TO_SHOW ? DASHBOARD_NUM_TO_SHOW
				: subordinatesLeaves.size();
		modelAndView.addObject("subLeaves", subordinatesLeaves.subList(0, pendingToShow));
		modelAndView.addObject("pendingNumToShow", pendingToShow);
		modelAndView.addObject("totalPendingNum", subordinatesLeaves.size());
		return modelAndView;
	}

	// shows a list of leave applications of status = 'PENDING' or 'UPDATED'
	// from manager's subordinate
	@RequestMapping(value = "/pending/list")
	public ModelAndView viewPendingPage(HttpSession session) throws IOException {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsManager()) {

				mav = new ModelAndView("manager-pending-list");
				HashMap<StaffMember, ArrayList<Leave>> hm = new HashMap<StaffMember, ArrayList<Leave>>();

				int staffId = us.getEmployee().getStaffId();
				ArrayList<StaffMember> subordinates = smService.findSubordinates(staffId);

				for (StaffMember sMember : subordinates) {
					ArrayList<Leave> llist = lService.findPendingLeaveByType(sMember.getStaffId());
					hm.put(sMember, llist);

					mav = new ModelAndView("manager-pending-list");
					// Pagination
					// ObjectMapper mapper = new ObjectMapper();
				}

				ManagerPath mp = ManagerPath.PENDING;
				session.setAttribute("USERPATH", mp);
				mav.addObject("pendinghistory", hm);
				return mav;
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}
		return mav;
	}

	// View specifics leave details, if leave has been approved,cancelled,deleted, rejected, manager cannot change its status
	@RequestMapping(value = "/pending/detail/{leaveId}")
	public ModelAndView approveApplicationPage(@PathVariable Integer leaveId, HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsManager()) {

				mav = new ModelAndView("manager-pending-approve");
				ManagerPath mp = ManagerPath.PENDING;
				session.setAttribute("MANAGERPATH", mp);
				Leave leave = lService.findLeaveById(leaveId);

				Calendar cal = Calendar.getInstance();
				cal.setTime(leave.getStartDate());
				int month = cal.get(Calendar.MONTH);
				int year = cal.get(Calendar.YEAR);
				ArrayList<Leave> allLeave = lService.findAllLeaveOfSubordinate(leave.getStaffMember().getManagerId());
				List<Leave> subLeave = MovementBean.filterLeaveByStatusAndMonth(allLeave, LeaveStatus.APPROVED, month,
						year);

				mav.addObject("leave", leave);
				mav.addObject("approve", new Approve());
				mav.addObject("subLeave", subLeave);
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}
		return mav;
	}

	// handles saving to database
	@RequestMapping(value = "/pending/edit/{leaveId}", method = RequestMethod.POST, params = "submit")
	public ModelAndView approveOrRejectCourse(@ModelAttribute("approve") Approve approve, BindingResult result,
			@PathVariable Integer leaveId, HttpSession session, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {

		UserSession us = (UserSession) session.getAttribute("USERSESSION");

		if (us == null || us.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}
		if (result.hasErrors())
			return new ModelAndView("manager-approve");
		else {
			ModelAndView mav = new ModelAndView("manager-pending-approve");
			Leave leave = lService.findLeaveById(leaveId);

			leave.setStatus(LeaveStatus.DELETED);

			Calendar cal = Calendar.getInstance();
			cal.setTime(leave.getStartDate());
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			ArrayList<Leave> allLeave = lService.findAllLeaveOfSubordinate(leave.getStaffMember().getManagerId());
			List<Leave> subLeave = MovementBean.filterLeaveByStatusAndMonth(allLeave, LeaveStatus.APPROVED, month,
					year);

			mav.addObject("leave", leave);
			mav.addObject("approve", new Approve());
			mav.addObject("subLeave", subLeave);

			if (approve.getDecision() == null) {
				mav.addObject("valError", "Select either Approve or Reject");
				return mav;
			} else if (approve.getDecision().equalsIgnoreCase("REJECTED")
					&& approve.getComment().equalsIgnoreCase("")) {
				mav.addObject("valError", "Mandatory comment required if rejecting leave");
				return mav;
			}
		}
		Leave leave = lService.findLeaveById(leaveId);
		String appOrRej = "approved";
		if (approve.getDecision().equalsIgnoreCase("approved")) {
			leave.setStatus(LeaveStatus.APPROVED);
		} else {
			leave.setStatus(LeaveStatus.REJECTED);

			if (leave.getLeaveType() == 3) {
				// If it was compensation, rollback hours
				List<Holiday> holidays = hService.findAllHoliday();
				Double leaveDays = LeavePeriodCalculator.calculateLeaveDays(leave, holidays);
				Integer hoursToUnclaim = Double.valueOf(leaveDays * 8).intValue();
				otService.unclaimHours(leave.getStaffId(), hoursToUnclaim);
				appOrRej = "rejected";
			}

		}
		leave.setComment(approve.getComment());
		System.out.println(leave.toString());
		ModelAndView mav = new ModelAndView("redirect:/manager/pending/list");
		ManagerPath mp = (ManagerPath) session.getAttribute("USERPATH");
		if (mp == ManagerPath.DASHBOARD) {
			mav = new ModelAndView("redirect:/manager/dashboard");
		} else if (mp == ManagerPath.HISTORY) {
			String url = "redirect:/manager/subordinate/history/" + leave.getStaffId();
			mav = new ModelAndView(url);
		} else {
			mav = new ModelAndView("redirect:/manager/pending/list");
		}
		lService.changeLeave(leave);

		// ----- EMAIL ------

		// Get manager email
		// String staffEmail = "sa44lapsteamtwo+staff@gmail.com";
		StaffMember staff = smService.findStaff(leave.getStaffId());
		String staffEmail = staff.getEmail();

		// set message
		String basePath = "http://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		String url = basePath + "/staff/history/details/" + leaveId + ".html";
		String emailMsg = "Dear " + staff.getName() + ",\n" + "Your manager, " + us.getEmployee().getName() + " has "
				+ appOrRej + " your leave. You can view the details here: \n" + url;
		String subject = "Manager " + us.getEmployee().getName() + " has " + appOrRej + " your leave.";

		EmailSender.getEmailSender().addRecipient(staffEmail).setMessage(emailMsg).setSubject(subject).send();
		// ----- END OF EMAIL ------

		String message = "Course was successfully updated.";
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	// if the manager does not want to approve leave and wants to return to previous page
	@RequestMapping(value = "/pending/edit/{leaveId}", method = RequestMethod.POST, params = "cancel")
	public ModelAndView cancelApproveOrRejectCourse(@ModelAttribute("approve") Approve approve, BindingResult result,
			@PathVariable Integer leaveId, HttpSession session, final RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("redirect:/manager/pending/list");
		Leave leave = lService.findLeaveById(leaveId);
		ManagerPath mp = (ManagerPath) session.getAttribute("USERPATH");
		if (mp == ManagerPath.DASHBOARD) {
			mav = new ModelAndView("redirect:/manager/dashboard");
		} else if (mp == ManagerPath.HISTORY) {
			String url = "redirect:/manager/subordinate/history/" + leave.getStaffId();
			mav = new ModelAndView(url);
		} else if (mp == ManagerPath.DETAIL) {
			String url = "redirect:/manager/subordinate/history/" + leave.getStaffId();
			mav = new ModelAndView(url);
		} else {
			mav = new ModelAndView("redirect:/manager/pending/list");
		}
		return mav;
	}

	// shows a list of subordinates details
	@RequestMapping(value = "/subordinate", method = RequestMethod.GET)
	public ModelAndView viewSubordinateListForLeaveApproval(HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsManager()) {

				mav = new ModelAndView("manager-subordinate");

				List<StaffMember> subordinateList = smService.findSubordinates(us.getEmployee().getStaffId());
				mav.addObject("subordinateList", subordinateList);
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}

		ManagerPath mp = ManagerPath.HISTORY;
		session.setAttribute("USERPATH", mp);
		return mav;
	}

	// shows subordinate's leave history
	@RequestMapping(value = "/subordinate/history/{staffId}", method = RequestMethod.GET)
	public ModelAndView viewSubordinateLeaveHistoryDeatils(@PathVariable int staffId, HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsManager()) {

				mav = new ModelAndView("manager-subordinate-history");

				StaffMember staffMember = smService.findStaff(staffId);
				mav.addObject("staffMember", staffMember);

				List<Leave> allLeave = lService.findStaffLeaveHistory(staffMember.getStaffId());
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				List<Leave> leaveHistoryList = MovementBean.filterLeaveByYear(allLeave, year);
				mav.addObject("leaveHistoryList", leaveHistoryList);
				
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}
		return mav;
	}

	// Moving into see subordinate's leave details
	@RequestMapping(value = "/subordinate/history/detail/{leaveId}", method = RequestMethod.GET)
	public ModelAndView viewSubordinateLeaveHistory(@PathVariable int leaveId, HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsManager()) {

				mav = new ModelAndView("manager-pending-approve");
				Leave leave = lService.findLeaveById(leaveId);
				if ((leave.getStatus() == LeaveStatus.APPROVED) || (leave.getStatus() == LeaveStatus.CANCELLED)
						|| (leave.getStatus() == LeaveStatus.REJECTED) || (leave.getStatus() == LeaveStatus.DELETED)) {
					String url = "redirect:/manager/view/detail/" + leave.getLeaveId();
					mav = new ModelAndView(url);
					return mav;
				}
				ManagerPath mp = ManagerPath.HISTORY;
				session.setAttribute("MANAGERPATH", mp);
				mav.addObject("leave", leave);
				mav.addObject("approve", new Approve());
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}
		return mav;
	}

	
	@RequestMapping(value = "/view/detail/{leaveId}", method = RequestMethod.GET)
	public ModelAndView viewSubordinateLeaveDetail(@PathVariable int leaveId, HttpSession session) {

		ModelAndView mav = new ModelAndView("login");
		try {
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getIsManager()) {

				mav = new ModelAndView("manager-view-details");
				Leave leave = lService.findLeaveById(leaveId);
				ManagerPath mp = ManagerPath.DETAIL;
				session.setAttribute("MANAGERPATH", mp);
				mav.addObject("leave", leave);
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
