package com.teamtwo.laps.repository;



import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teamtwo.laps.model.LeaveType;


public interface LeaveTypeRepository extends  JpaRepository<LeaveType, String> {

	@Query("SELECT s FROM LeaveType s WHERE s.leaveType = :id")
	LeaveType findLeaveTypeById(@Param("id") Integer leaveType);
	
	@Query("SELECT leaveName FROM LeaveType e")
	ArrayList<String> findAllLeaveTypeName();
	
	
}
