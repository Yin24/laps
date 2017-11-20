package com.teamtwo.laps.service;

import java.util.ArrayList;



import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.LeaveType;


public interface LeaveService {

	ArrayList<Leave> findAllLeaveOfStaff(Integer staffId);

	ArrayList<Leave> findLeaveByType(Integer staffId, Integer leaveType);

	Leave findLeaveById(Integer leaveId);

	Leave createLeave(Leave leave);

	
	Leave changeLeave(Leave leave);
	
	ArrayList<Leave> findAllLeave();

	ArrayList<Leave> findPendingLeaveByType(Integer staffId);
	
	ArrayList<Leave> findAllLeaveOfSubordinate(Integer managerId);
	
	//Huitian
    void DeleteLeave(Leave lt, LeaveStatus ls);
    
	void cancelLeave(Leave lt, LeaveStatus ls);
	
	ArrayList<Leave> findStaffLeaveHistory(Integer sid );
	
	//Htein
	
	Integer findStaffId(Integer id);
}