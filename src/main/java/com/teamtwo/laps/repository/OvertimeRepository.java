package com.teamtwo.laps.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.Overtime;

public interface OvertimeRepository extends JpaRepository<Overtime, Integer> {
	
	@Query("SELECT ot FROM Overtime ot WHERE ot.staffId = :id")
	ArrayList<Overtime> findOvertimeOfStaff(@Param("id") Integer id);

	@Query("SELECT SUM(ot.loggedHours - ot.claimedHours) FROM Overtime ot WHERE (ot.staffId = :id AND ot.approved = true)")
	Integer findUnclaimedOvertimeOfStaff(@Param("id") Integer id);

	@Query("SELECT SUM(ot.loggedHours) FROM Overtime ot WHERE ot.staffId = :id")
	Integer findLoggedHoursOfStaff(@Param("id") Integer id);

	@Query("SELECT SUM(ot.claimedHours) FROM Overtime ot WHERE ot.staffId = :id")
	Integer findClaimedHoursOfStaff(@Param("id") Integer id);
}