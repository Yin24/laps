package com.teamtwo.laps.controller;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.model.User;



@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserSession {
	
	private String sessionId = null;
	private User user = null;
	private StaffMember employee = null;
	private ArrayList<StaffMember> subordinates = null;
	
	public UserSession() {
		super();
	}

	public UserSession(String sessionId, User user, StaffMember employee, ArrayList<StaffMember> subordinates) {
		super();
		this.sessionId = sessionId;
		this.user = user;
		this.employee = employee;
		this.subordinates = subordinates;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StaffMember getEmployee() {
		return employee;
	}

	public void setEmployee(StaffMember employee) {
		this.employee = employee;
	}

	public ArrayList<StaffMember> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(ArrayList<StaffMember> subordinates) {
		this.subordinates = subordinates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSession other = (UserSession) obj;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		return true;
	}
	
	


}
