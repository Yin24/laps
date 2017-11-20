package com.teamtwo.laps.service;

import java.util.ArrayList;


import com.teamtwo.laps.model.LeaveType;



public interface LeaveTypeService {

	LeaveType createLeaveType(LeaveType leave);
	
	ArrayList<LeaveType> findAllLeaveType();
	
	ArrayList<String> findAllLeaveTypeName();
	
	LeaveType findLeaveTypeById(Integer leaveType);
	
	LeaveType changeLeaveType(LeaveType lt);

	void removeLeaveType(LeaveType lt);
}