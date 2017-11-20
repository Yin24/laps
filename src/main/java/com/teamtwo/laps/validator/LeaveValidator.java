package com.teamtwo.laps.validator;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.teamtwo.laps.javabeans.LeavePeriodCalculator;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.LeaveType;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.StaffMemberService;

@Component
public class LeaveValidator implements Validator {

	@Autowired
	private StaffMemberService smService;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return Leave.class.isAssignableFrom(arg0);

	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Leave leave = (Leave) arg0;	
		if ((leave.getStartDate()!=null && leave.getEndDate()!=null)&&(leave.getStartDate().compareTo(leave.getEndDate()) > 0)) {
			arg1.reject("endDate", "End date must be after start date.");
			arg1.rejectValue("endDate", "error.dates", "End date must be after start date.");
	
		}
		
		if (leave.getStartDate() == null || leave.getEndDate() == null) {
			return;
		}
		
		if (leave.getStartDate().before(new Date())) {
			arg1.reject("startDate", "Start date cannot be before today.");
			arg1.rejectValue("startDate", "error.dates", "Start date cannot be before today.");
			return;
		}
		
		if (LeavePeriodCalculator.isDateOnWeekend(leave.getStartDate())) {
			arg1.reject("startDate", "Start date cannot be on a weekend.");
			arg1.rejectValue("startDate", "error.dates", "Start date cannot be on a weekend.");
			return;
		}
		if (LeavePeriodCalculator.isDateOnWeekend(leave.getEndDate())) {
			arg1.reject("endDate", "End date cannot be on a weekend.");
			arg1.rejectValue("endDate", "error.dates", "End date cannot be on a weekend.");
			return;
		}
		
		Calendar start = Calendar.getInstance();
		start.setTime(leave.getStartDate());
		Calendar end = Calendar.getInstance();
		end.setTime(leave.getEndDate());
		
		int startYear = start.get(Calendar.YEAR);
		int endYear = end.get(Calendar.YEAR);
		
		if (startYear != endYear) {
			arg1.reject("endDate", "Must be within the same year");
			arg1.rejectValue("endDate", "error.dates", "Must be within the same year");
			
		}
//		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "courseName", "error.courseName", "Course name is required.");
	}

}
