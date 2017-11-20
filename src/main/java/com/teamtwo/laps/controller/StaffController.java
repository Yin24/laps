package com.teamtwo.laps.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.Collections;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.validation.Valid;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dbunit.dataset.stream.StreamingDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.javabeans.ManagerPath;
import com.teamtwo.laps.model.Holiday;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.LeaveType;
import com.teamtwo.laps.model.Overtime;
import com.teamtwo.laps.javabeans.DashboardBean;
import com.teamtwo.laps.javabeans.EmailSender;
import com.teamtwo.laps.javabeans.LeavePeriodCalculator;
import com.teamtwo.laps.javabeans.MovementBean;
import com.teamtwo.laps.javabeans.OvertimeList;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.model.User;
import com.teamtwo.laps.service.HolidayService;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.LeaveTypeService;
import com.teamtwo.laps.service.OvertimeService;
import com.teamtwo.laps.service.StaffMemberService;

import com.teamtwo.laps.controller.UserSession;

import com.teamtwo.laps.validator.LeaveValidator;

/**
 * Handles requests for the application staff pages.
 */
@Controller
@RequestMapping(value = "/staff")
public class StaffController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final int DASHBOARD_NUM_TO_SHOW = 3;

	@Autowired
	private StaffMemberService smService;

	@Autowired
	private LeaveService lService;

	@Autowired
	private LeaveTypeService lTypeService;

	@Autowired
	private OvertimeService otService;

	@Autowired
	private LeaveValidator leaveValidator;
	
	@Autowired
	private HolidayService hService;
	
	@InitBinder("leave")
	private void initCourseBinder(WebDataBinder binder) {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// dateFormat.setLenient(false);
		// binder.registerCustomEditor(Date.class, new
		// CustomDateEditor(dateFormat, false));
		binder.addValidators(leaveValidator);

	}

	/**
	 * Renders the staff dashboard.
	 */

	@RequestMapping(value = "/dashboard")
	public ModelAndView home(HttpSession session) {

		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");
		
		ManagerPath sp = ManagerPath.DASHBOARD;
		session.setAttribute("USERPATH", sp);

		if (userSession == null || userSession.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}

		int staffId = userSession.getEmployee().getStaffId();
		User user = userSession.getUser();

		if (user.getIsManager()) {
			return new ModelAndView("redirect:/manager/dashboard");
		}

		StaffMember staffMember = smService.findStaffById(staffId);
		ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
		ModelAndView modelAndView = new ModelAndView("staff-dashboard");

		List<Holiday> holidays = hService.findAllHoliday();
		
		modelAndView = DashboardBean.getDashboard(modelAndView, DASHBOARD_NUM_TO_SHOW, staffMember, leaves, holidays, otService);

		// leaves.get(1).getStartDate().getDate()
		logger.info("Rendering dashboard for user {}.", user.getUserId());

		return modelAndView;
	}

	@RequestMapping(value = "/email")
	public ModelAndView sendEmail(HttpServletRequest request) {

		final String RECIPIENT = "sa44lapsteamtwo@gmail.com";

//		EmailSender.getEmailSender().addRecipient(RECIPIENT).setMessage("hello").setSubject("Not important").send();

		ModelAndView mav = new ModelAndView("email");
		String basePath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		mav.addObject("url2", basePath);
		return mav;
	}

	@RequestMapping(value = "/movement", method = RequestMethod.GET)
	public ModelAndView movement(HttpSession session) {

		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");

		if (userSession == null || userSession.getSessionId() == null) {
			 return new ModelAndView("redirect:/home/login");
		}

		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		ModelAndView modelAndView = new ModelAndView("staff-movement-register", "monthSelect", Integer.class);

		modelAndView.addObject("picked", currentMonth);

		return MovementBean.getMovementMAV(lService, modelAndView, currentMonth, currentYear);
	}

	@RequestMapping(value = "/movement", method = RequestMethod.POST)
	public ModelAndView movementPost(HttpServletRequest request, HttpServletResponse response) {
		String picker = request.getParameter("monthPicker");

		int currentMonth = Integer.parseInt(picker);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		ModelAndView modelAndView = new ModelAndView("staff-movement-register", "monthSelect", Integer.class);

		modelAndView = MovementBean.getMovementMAV(lService, modelAndView, currentMonth, currentYear);

		modelAndView.addObject("picked", picker);

		return modelAndView;
	}

	@RequestMapping(value = "/view/{staffId}")
	public ModelAndView viewStaffMemberPage(@PathVariable Integer staffId) {
		ModelAndView modelAndView = new ModelAndView("staffMember-view");
		StaffMember staffMember = smService.findStaffById(staffId);
		modelAndView.addObject("staffMember", staffMember);
		return modelAndView;
	}

	// @RequestMapping(value = "/leave/{staffId}")
	// public ModelAndView viewStaffLeave(@PathVariable Integer staffId) {
	// ModelAndView modelAndView = new ModelAndView("staffMember-leave-view");
	//// ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
	// modelAndView.addObject("leaves", lService.findAllLeaveOfStaff(staffId));
	// return modelAndView;
	// }

	@RequestMapping(value = "/leave/create", method = RequestMethod.GET)
	public ModelAndView NewLeavePage(HttpSession session) {

		UserSession us = (UserSession) session.getAttribute("USERSESSION");

		if (us == null) {
			return new ModelAndView("redirect:/home/login");
		}
		int loggedInStaffId = us.getUser().getStaffId();

		ModelAndView mav = new ModelAndView("staff-leave-new");
		
		ArrayList<LeaveType> leaveTypes = lTypeService.findAllLeaveType();
		
		ArrayList<StaffMember> staffMembers = (ArrayList<StaffMember>) smService.findAllStaff().stream()
				.filter(staff -> staff.getStaffId() != loggedInStaffId).collect(Collectors.toList());
		
		Leave leave = new Leave();
		mav.addObject("leave", leave);
		mav.addObject("leaveTypes", leaveTypes);
		mav.addObject("staffMembers", staffMembers);
		
		int unclaimedHours = otService.findUnclaimedHoursOfStaff(loggedInStaffId);
		mav.addObject("compHours", unclaimedHours);
		mav.addObject("compDays", unclaimedHours * 1.0 / 8);

		return mav;
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home/login";

	}

	// Huitian

	@RequestMapping(value = "/history")
	public ModelAndView staffLeaveHistory(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "perPage", required = false) Optional<Integer> prevPerPage,
			@RequestParam(value = "page", required = false) Optional<Integer> prevCurrentPage) {
		
		final int DEFAULT_CURRENT_PAGE = 1;
		final int DEFAULT_PER_PAGE = 5;
		final int[] PER_PAGE_LIST = {5, 10, 15};
		
		
		UserSession us = (UserSession) session.getAttribute("USERSESSION");
		ModelAndView mav = new ModelAndView("login");
		if (us == null || us.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}
		mav = new ModelAndView("/staff-leave-history");
		List<Leave> allLeave = lService.findStaffLeaveHistory(us.getEmployee().getStaffId());
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		List<Leave> leaveHistoryList = MovementBean.filterLeaveByYear(allLeave, year);
		
		ManagerPath mp = ManagerPath.HISTORY;
		session.setAttribute("USERPATH", mp);
		
		/* Pagination */
		int currentPage = prevCurrentPage.isPresent() ? prevCurrentPage.get() : DEFAULT_CURRENT_PAGE;
		int perPage = prevPerPage.isPresent() ? prevPerPage.get() : DEFAULT_PER_PAGE;
		int totalPages = (int) Math.ceil(1.0 * leaveHistoryList.size() / perPage);
		
		if (currentPage > totalPages) currentPage = totalPages;
		
		// Set staffList on grid to only a selection, based on paging criteria
		int startIndex = leaveHistoryList.size() <= ((currentPage - 1) * perPage) ? 0 : ((currentPage - 1) * perPage);
		int endIndex = leaveHistoryList.size() > startIndex + perPage ? startIndex + perPage : leaveHistoryList.size();
		System.out.println("perPage:"+perPage);
		System.out.println("currentPage:"+currentPage);
		try {
			List<Leave> leaveHistoryListOnPage = leaveHistoryList.subList(startIndex, endIndex);
			mav.addObject("lhistory", leaveHistoryListOnPage);

		} catch (IndexOutOfBoundsException e) {
			mav.addObject("valError", "You have not appplied for any leave yet!");
		}
		
		
		mav.addObject("currentPage", currentPage);
		mav.addObject("perPage", perPage);
		mav.addObject("totalPages", totalPages);
		
		Boolean prevBtnEnabled = (currentPage == 1 || totalPages == 1) ? false : true;
		Boolean nextBtnEnabled = currentPage >= totalPages ? false : true;
		mav.addObject("prevBtnEnabled", prevBtnEnabled);
		mav.addObject("nextBtnEnabled", nextBtnEnabled);
		
		mav.addObject("perPageList", PER_PAGE_LIST);
		
		/* End Pagination */
		
		return mav;

	}

	@RequestMapping(value = "/history/details/{id}", method = RequestMethod.GET)
	public ModelAndView LeaveDetails(@PathVariable Integer id, HttpSession session) {
		ModelAndView mav = new ModelAndView("login");
		try {
			Integer sid = lService.findStaffId(id);
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getStaffId() == sid) {

				mav = new ModelAndView("staff-leave-history-details");
				Leave leave = lService.findLeaveById(id);
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

	@RequestMapping(value = "/history/details/{id}", method = RequestMethod.POST)
	public ModelAndView LeaveDetailsBack(@PathVariable Integer id, HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/staff/history");
		ManagerPath sp = (ManagerPath) session.getAttribute("USERPATH");
		if (sp == ManagerPath.DASHBOARD) {
			mav = new ModelAndView("redirect:/staff/dashboard");
		}
		return mav;
	}

	@RequestMapping(value = "/history/update/{id}", method = RequestMethod.GET)
	public ModelAndView updateLeaveHistory(@PathVariable Integer id, HttpSession session) {
		ModelAndView mav = new ModelAndView("login");
		try {
			Integer sid = lService.findStaffId(id);
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getStaffId() == sid) {
				int loggedInStaffId = us.getUser().getStaffId();
				
				mav = new ModelAndView("staff-leave-new");
				ArrayList<LeaveType> leaveTypes = lTypeService.findAllLeaveType();
				ArrayList<StaffMember> staffMembers = (ArrayList<StaffMember>) smService.findAllStaff().stream()
						.filter(staff -> staff.getStaffId() != loggedInStaffId).collect(Collectors.toList());
				Leave leave = lService.findLeaveById(id);
				mav.addObject("leave", leave);
				mav.addObject("leaveTypes", leaveTypes);
				mav.addObject("staffMembers", staffMembers);
				
				int unclaimedHours = otService.findUnclaimedHoursOfStaff(loggedInStaffId);
				mav.addObject("compHours", unclaimedHours);
				mav.addObject("compDays", unclaimedHours * 1.0 / 8);
				
				mav.addObject("isEdit", true);
			} else {
				mav = new ModelAndView("unauthorized-admin-access");
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			mav = new ModelAndView("unauthorized-access");
		}
		return mav;
	}

	@RequestMapping(value = "/history/update/{id}", method = RequestMethod.POST)
	public ModelAndView updateLeave(@ModelAttribute @Valid Leave leave, BindingResult result, @PathVariable Integer id,
			final RedirectAttributes redirectAttributes, HttpSession session) {
		if (result.hasErrors())
			return new ModelAndView("staff-leave-history-update");
		ModelAndView mav = new ModelAndView();
		String message = "The leave " + leave.getLeaveId() + "has been successfully updated.";
		lService.changeLeave(leave);
		UserSession us = (UserSession) session.getAttribute("USERSESSION");
		leave.setStaffId(us.getEmployee().getStaffId());
		leave.setStatus(LeaveStatus.UPDATED);
		mav.setViewName("redirect:/staff/history");
		lService.changeLeave(leave);
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
		
		
	}

	@RequestMapping(value = "/history/delete/{id}", method = RequestMethod.GET)
	public ModelAndView DeleteLeave(@PathVariable Integer id, final RedirectAttributes redirectAttributes,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("login");
		try {
			Integer sid = lService.findStaffId(id);
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getStaffId() == sid) {

				mav = new ModelAndView("redirect:/staff/history");
				Leave leave = lService.findLeaveById(id);
				lService.DeleteLeave(leave, LeaveStatus.DELETED);
				String message = "The leave " + leave.getLeaveId() + " has been successfully deleted.";

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

	@RequestMapping(value = "/history/cancel/{id}", method = RequestMethod.GET)
	public ModelAndView cancelLeave(@PathVariable Integer id, final RedirectAttributes redirectAttributes,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("login");
		try {
			Integer sid = lService.findStaffId(id);
			UserSession us = (UserSession) session.getAttribute("USERSESSION");
			if (us.getSessionId() != null && us.getUser().getStaffId() == sid) {

				mav = new ModelAndView("redirect:/staff/history");
				Leave leave = lService.findLeaveById(id);
				lService.DeleteLeave(leave, LeaveStatus.CANCELLED);
				String message = "The leave " + leave.getLeaveId() + " has been successfully cancelled.";

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

	@RequestMapping(value = "/leave/created", method = RequestMethod.GET)
	public ModelAndView refreshCreatedPage(HttpSession session) {
		return NewLeavePage(session);
	}

	@RequestMapping(value = "/leave/created", method = RequestMethod.POST)
	public ModelAndView createNewLeave(@ModelAttribute @Valid Leave leave, BindingResult result,
			final RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
		UserSession us = (UserSession) session.getAttribute("USERSESSION");

		if (us == null) {
			return new ModelAndView("redirect:/home/login");
		}
		int staffId = us.getUser().getStaffId();

//		Boolean compNotOkay = false;
		String compErrorMessage = "";
		if (leave.getLeaveType() == 3 && leave.getStartDate() != null && leave.getEndDate() != null) {
			// If it's compensation do validation
//			int unclaimedHours = otService.findUnclaimedHoursOfStaff(staffId);
//			double eligibleDays = unclaimedHours * 1.0 / 8;
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			start.setTime(leave.getStartDate());
			end.setTime(leave.getEndDate());
			
//			int startDate = start.get(Calendar.MONTH) * start.getActualMaximum(Calendar.DAY_OF_MONTH) + start.get(Calendar.DATE);
//			int endDate = end.get(Calendar.MONTH) * end.getActualMaximum(Calendar.DAY_OF_MONTH) + end.get(Calendar.DATE);
			
			String startHalfDay = request.getParameter("startDateHalfDay");
			String endHalfDay = request.getParameter("endDateHalfDay");

//			double claimingDays = endDate - startDate;
			
			if (startHalfDay != null) {
				System.out.println("startHalfDay: " + startHalfDay);
				Calendar startDateNew = Calendar.getInstance();
				startDateNew.setTime(leave.getStartDate());
				startDateNew.set(Calendar.HOUR_OF_DAY, 12);
//				leave.setStartDate(startDateNew.getTime());
//				claimingDays -= 0.5;
			}

			if (endHalfDay != null) {
				System.out.println("endHalfDay: " + endHalfDay);
				Calendar endDateNew = Calendar.getInstance();
				endDateNew.setTime(leave.getEndDate());
				endDateNew.set(Calendar.HOUR_OF_DAY, 12);
//				leave.setEndDate(endDateNew.getTime());
				leave.setIsHalfDay(1);
//				claimingDays += 0.5;
			}
			
//			System.out.println(claimingDays);
			
//			if (claimingDays > eligibleDays) {
//				compNotOkay = true;
//				compErrorMessage = String.format("Can't claim more than you have. Trying to claim: %.1f, unclaimed: %.1f).", claimingDays, eligibleDays); 
//			}
		}
		
		Boolean notEnoughLeaveDays = false;
		String leaveDaysErrorMessage = "";
		Double leaveDays = 0.0;
		// Validate according to leave type
		StaffMember staffMember = us.getEmployee();
		
		if (leave.getStartDate() != null && leave.getEndDate() != null) {
			List<Holiday> holidays = hService.findAllHoliday();
			leaveDays = LeavePeriodCalculator.calculateLeaveDays(leave, holidays);
			Double availableLeave = staffMember.getAvailableLeaveDaysOfType(leave.getLeaveType(), holidays, otService);
			if (leaveDays > availableLeave) {
				leaveDaysErrorMessage = String.format("Not enough leave days. Trying to claim: %.1f, eligible: %.1f).", leaveDays, availableLeave); 
				notEnoughLeaveDays = true;
			}
		}
		
		if (result.hasErrors() || notEnoughLeaveDays) {
			ModelAndView mavError = new ModelAndView("staff-leave-new");
			ArrayList<LeaveType> leaveTypes = lTypeService.findAllLeaveType();
			ArrayList<StaffMember> staffMembers = (ArrayList<StaffMember>) smService.findAllStaff().stream()
					.filter(staff -> staff.getStaffId() != staffId).collect(Collectors.toList());
			mavError.addObject("leave", leave);
			mavError.addObject("leaveTypes", leaveTypes);
			mavError.addObject("staffMembers", staffMembers);
			
			int unclaimedHours = otService.findUnclaimedHoursOfStaff(staffId);
			mavError.addObject("compHours", unclaimedHours);
			mavError.addObject("compDays", unclaimedHours * 1.0 / 8);

			mavError.addObject("leaveDaysError", leaveDaysErrorMessage);
			mavError.addObject("compError", compErrorMessage);
			
			if (request.getParameter("edit").equals("true")) {
				mavError.addObject("isEdit", true);
			}
			
			return mavError;
		}
		
		Integer claimHours = Double.valueOf(leaveDays * 8).intValue();
		otService.claimHours(staffId, claimHours);
		
			ModelAndView mav = new ModelAndView("staff-leave-created");
			// leave.setEmployeeId(us.getEmployee().getEmployeeId());
			
			if (request.getParameter("edit").equals("true")) {
				// is an update
				lService.changeLeave(leave);

				leave.setStaffId(us.getEmployee().getStaffId());
				leave.setStatus(LeaveStatus.UPDATED);
				mav.setViewName("redirect:/staff/history");
				lService.changeLeave(leave);
			} else {
				// is a create
				leave.setStaffId(us.getUser().getStaffId());
	
				leave.setStatus(LeaveStatus.PENDING);
	
				// String message = "New leave " + leave.getLeaveId() + " was
				// successfully created.";
				lService.createLeave(leave);
				
				// ----- EMAIL ------
				// Get manager email
//			String mgrEmail = "sa44lapsteamtwo+manager@gmail.com";
				StaffMember mgr =  smService.findStaff(us.getEmployee().getManagerId());
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
				String url = basePath + "/manager/subordinate/history/detail/" + highestID + ".html";
				String message = "Dear " + mgr.getName() + ",\n"
						+ "Your subordinate, " + us.getEmployee().getName()
						+ " has applied for leave. You can view the application here: \n"
						+ url;
				String subject = "Employee " + us.getEmployee().getName() + " has applied for leave.";
				
				EmailSender.getEmailSender().addRecipient(mgrEmail).setMessage(message).setSubject(subject).send();
				// ----- END OF EMAIL ------
			}

			return mav;
	}

}
