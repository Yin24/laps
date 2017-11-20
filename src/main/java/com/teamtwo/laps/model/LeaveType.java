package com.teamtwo.laps.model;





import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "leave_type")
public class LeaveType {
	
	@Id
	@Column(name = "leavetype")
	@NotNull
	private int leaveType;
	
	@Column(name = "leavename")
	@NotEmpty(message = "Please enter leave name.")
	private String leaveName;
	
	
	public LeaveType()
	{
		
	}

	public LeaveType(int leaveType, String leaveName)
	{
		this.leaveType=leaveType;
		this.leaveName= leaveName;
	}
	
	public int getLeaveType()
	{
		return leaveType;
	}
	public void setLeaveType(int leaveType)
	{
		this.leaveType=leaveType;
	}
	public String getLeaveName()
	{
		return leaveName;
	}
	public void setLeaveName(String leaveName)
	{
		this.leaveName=leaveName;
	}
}
