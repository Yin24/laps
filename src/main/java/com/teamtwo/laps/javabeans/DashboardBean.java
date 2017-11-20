package com.teamtwo.laps.javabeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.teamtwo.laps.controller.UserSession;
import com.teamtwo.laps.model.Holiday;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.HolidayService;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.OvertimeService;
import com.teamtwo.laps.service.StaffMemberService;

public class DashboardBean {
	
	public static ModelAndView getDashboard(ModelAndView modelAndView, final int DASHBOARD_NUM_TO_SHOW, StaffMember staffMember, ArrayList<Leave> leaves, List<Holiday> holidays, OvertimeService otService) {
		Collections.reverse(leaves);
		Double annualLeaveDays = leaves.stream()
				.filter(a -> a.getLeaveType() == 1
					&& (a.getStatus() == LeaveStatus.APPROVED
					|| a.getStatus() == LeaveStatus.PENDING
					|| a.getStatus() == LeaveStatus.UPDATED))
				.map(x -> x.getNumberOfDays(holidays)).reduce(0.0, ((a, b) -> a + b));
		Double annualLeavePending = leaves.stream().filter(a -> a.getLeaveType() == 1 && (a.getStatus() == LeaveStatus.PENDING || a.getStatus() == LeaveStatus.UPDATED)).map(x -> x.getNumberOfDays(holidays)).reduce(0.0, ((a, b) -> a + b));
		
		Double medicalLeaveDays = leaves.stream()
				.filter(a -> a.getLeaveType() == 2
				&& (a.getStatus() == LeaveStatus.APPROVED
				|| a.getStatus() == LeaveStatus.PENDING
				|| a.getStatus() == LeaveStatus.UPDATED))
				.map(x -> x.getNumberOfDays(holidays)).reduce(0.0, ((a, b) -> a + b));
		Double medicalLeavePending = leaves.stream().filter(a -> a.getLeaveType() == 1 && (a.getStatus() == LeaveStatus.PENDING || a.getStatus() == LeaveStatus.UPDATED)).map(x -> x.getNumberOfDays(holidays)).reduce(0.0, ((a, b) -> a + b));
		
		Double pendingCompLeaveHours = leaves.stream()
				.filter(a -> a.getLeaveType() == 3
				&& (a.getStatus() == LeaveStatus.PENDING
				|| a.getStatus() == LeaveStatus.UPDATED)
				).map(x -> x.getNumberOfDays(holidays)).reduce(0.0, ((a, b) -> a + b));
		
		int leavesToShow = leaves.size() > DASHBOARD_NUM_TO_SHOW ? DASHBOARD_NUM_TO_SHOW : leaves.size();
		
		modelAndView.addObject("staffMember", staffMember);
		modelAndView.addObject("leaves", leaves.subList(0, leavesToShow));
		modelAndView.addObject("totalLeavesNum", leaves.size());
		modelAndView.addObject("annualLeaveDays", annualLeaveDays);
		modelAndView.addObject("annualLeavePending", annualLeavePending);
		modelAndView.addObject("medicalLeaveDays", medicalLeaveDays);
		modelAndView.addObject("medicalLeavePending", medicalLeavePending);
		modelAndView.addObject("numToShow", leavesToShow);

		Integer availableCompLeaveHours = otService.findUnclaimedHoursOfStaff(staffMember.getStaffId());
		
		modelAndView.addObject("compHoursUnclaimed", availableCompLeaveHours);
		modelAndView.addObject("availableCompLeaveDays", availableCompLeaveHours * 1.0 / 8);
		modelAndView.addObject("pendingCompLeaveDays", pendingCompLeaveHours);

		return modelAndView;
	}
}
