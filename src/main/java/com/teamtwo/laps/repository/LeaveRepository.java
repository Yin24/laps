package com.teamtwo.laps.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teamtwo.laps.model.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {
	
	@Query("SELECT l FROM Leave l WHERE l.leaveId = :id")
	Leave findLeaveById(@Param("id") Integer id);
	
	@Query("SELECT l FROM Leave l WHERE l.leaveType = :leaveType AND l.staffId = :staffId")
	ArrayList<Leave> findLeaveByType(@Param("leaveType") Integer leaveType, @Param("staffId") Integer staffId);
	
	@Query("SELECT l FROM Leave l WHERE (l.status = 'PENDING' OR l.status = 'UPDATED') AND l.staffId = :staffId")
	ArrayList<Leave> findPendingLeaveByType(@Param("staffId") Integer staffId);
	
	@Query("SELECT l from Leave l WHERE l.staffId = :staffId")
	ArrayList<Leave> findAllLeaveOfStaff(@Param("staffId") Integer staffId);
	
	@Query("SELECT l from Leave l WHERE l.staffMember.managerId = :managerId")
	ArrayList<Leave> findAllLeaveOfSubordinate(@Param("managerId") Integer managerId);

	//Yin
	@Query("SELECT l from Leave l WHERE l.staffId = :sid")
	ArrayList<Leave> getLeaveHistoryBySID(@Param("sid") Integer sid);
	
	//Htein
	@Query("SELECT l.staffId from Leave l WHERE l.leaveId = :lid")
	Integer findStaffId(@Param("lid") Integer id);
	
}