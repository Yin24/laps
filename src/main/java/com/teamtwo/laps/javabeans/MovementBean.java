package com.teamtwo.laps.javabeans;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.servlet.ModelAndView;

import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.service.LeaveService;

public class MovementBean {

	public static ModelAndView getMovementMAV(LeaveService lService, ModelAndView modelAndView, int currentMonth,
			int currentYear) {
		List<Leave> allLeave = lService.findAllLeave();
		List<Leave> allApprovedLeave = filterLeaveByStatusAndMonth(allLeave, LeaveStatus.APPROVED, currentMonth, currentYear);
 
		List<String> monthNames = Arrays.asList(new DateFormatSymbols().getMonths()).subList(0, 12);

		for (int i = 0; i < monthNames.size(); i++) {
			int leavesInMonth = filterLeaveByStatusAndMonth(allLeave, LeaveStatus.APPROVED, i, currentYear).size();

			if (leavesInMonth > 0) {
				String monthName = monthNames.get(i);
				monthName += String.format(" (%s %s)", leavesInMonth, leavesInMonth == 1 ? "leave" : "leaves");
				monthNames.set(i, monthName);
			}
		}

		modelAndView.addObject("allLeave", allApprovedLeave);
		modelAndView.addObject("year", currentYear);
		// modelAndView.addObject("monthName", new
		// SimpleDateFormat("MMMM").format(Calendar.getInstance().getTime()));
		modelAndView.addObject("allMonthNames", monthNames);

		return modelAndView;
	}

	public static List<Leave> filterLeaveByStatusAndMonth(List<Leave> allLeave, LeaveStatus status, int month, int year) {
		return allLeave.stream().filter(leave -> leave.getStatus() == status).filter(leave -> {
			Calendar start = Calendar.getInstance();
			start.setTime(leave.getStartDate());
			Calendar end = Calendar.getInstance();
			end.setTime(leave.getEndDate());
			
			if ((start.get(Calendar.YEAR) <= year && year <= end.get(Calendar.YEAR))
					&& (start.get(Calendar.MONTH) <= month && month <= end.get(Calendar.MONTH))
					)
				return true;
			else
				return false;
		}).collect(Collectors.toList());
	}
	
	public static List<Leave> filterLeaveByYear(List<Leave> allLeave, int year) {
		return allLeave.stream().filter(leave -> {
			Calendar start = Calendar.getInstance();
			start.setTime(leave.getStartDate());
			Calendar end = Calendar.getInstance();
			end.setTime(leave.getEndDate());
			
			if ((start.get(Calendar.YEAR) <= year && year <= end.get(Calendar.YEAR)))
				return true;
			else
				return false;
		}).collect(Collectors.toList());
	}

}
