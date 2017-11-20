package com.teamtwo.laps.javabeans;

import java.util.List;

import com.teamtwo.laps.model.Overtime;

public class OvertimeList {
	private List<Overtime> overtimes;

	public List<Overtime> getOvertimes() {
		return overtimes;
	}

	public void setOvertimes(List<Overtime> overtimes) {
		this.overtimes = overtimes;
	}
	
	public Integer getSumLoggedHours(){
		return overtimes.stream().map(ot -> ot.getLoggedHours()).reduce(0, (a, b) -> a + b);
	}
	
	public void setStaffId(Integer staffId) {
		overtimes.forEach(overtime -> overtime.setStaffId(staffId));
	}
	
	public void resetAllHoursClaimed() {
		overtimes.forEach(overtime ->  {
			overtime.setClaimedHours(0);
			overtime.setApproved(false);
			overtime.setWasConfirmed(false);
		}
		);
	}
}
