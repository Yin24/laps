package com.teamtwo.laps.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teamtwo.laps.model.StaffMember;



public interface StaffMemberRepository extends JpaRepository<StaffMember, Integer> {
	@Query("SELECT s FROM StaffMember s WHERE s.staffId = :id")
	StaffMember findStaffMemberById(@Param("id") Integer id);
	
	@Query("SELECT s FROM StaffMember s WHERE s.managerId = :managerId")
	ArrayList<StaffMember> findStaffByManagerId(@Param("managerId") Integer managerId);
	
	@Query("SELECT DISTINCT e2 FROM StaffMember e1, StaffMember e2 WHERE e1.staffId = e2.managerId AND e1.staffId = :eid")
	ArrayList<StaffMember> findSubordinates(@Param("eid") Integer staffId);
	
	@Query("SELECT DISTINCT e.staffId FROM StaffMember e")
	ArrayList<Integer> findAllStaffId();

	//Yin
	@Query(value="select s.name from staff_list s, leave_history l where s.staffid =  l.staffid ", nativeQuery=true)
	ArrayList<String> getOnlyStaffName();
	
	@Query("SELECT  e FROM StaffMember e WHERE e.staffId != :staffId")
	ArrayList<StaffMember> findAllStaffExcept(@Param("staffId") Integer staffId);
}