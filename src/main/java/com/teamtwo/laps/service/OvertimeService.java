package com.teamtwo.laps.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.laps.model.Overtime;

public interface OvertimeService {

	ArrayList<Overtime> findOvertimeOfStaff(Integer staffId);

	ArrayList<Overtime> findAllOvertime();

	Overtime findOvertimeById(Integer id);

	Integer findUnclaimedHoursOfStaff(Integer staffId);

	Integer findLoggedHoursOfStaff(Integer staffId);

	Integer findClaimedHoursOfStaff(Integer staffId);

	void saveOvertimes(List<Overtime> overtimes);

	void updateOvertimes(List<Overtime> overtimes);

	void deleteOvertime(Integer id);

	void claimHours(Integer staffId, Integer hours);

	void unclaimHours(Integer staffId, Integer hoursToUnclaim);

}