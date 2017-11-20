
package com.teamtwo.laps.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;

/**
 * User class
 *
 * @version $Revision: 1.0
 */
@Entity
@Table(name = "user_list")
public class User {
	@Id
	@Column(name = "userid")
	private String userId;
	@Basic(optional = false)
	@Column(name = "password")
	private String password;
	@Column(name = "staff_id")
	private int staffId;
	@Column(name = "is_admin")
	private boolean isAdmin;
	@Column(name = "is_manager")
	private boolean isManager;
	@Column(name = "is_staff")
	private boolean isStaff;




	public User(String userId, String password, int staffId, boolean isAdmin, boolean isManager, boolean isStaff) {
		super();
		this.userId = userId;
		this.password = password;
		this.staffId = staffId;
		this.isAdmin = isAdmin;
		this.isManager = isManager;
		this.isStaff = isStaff;
	}




	public String getUserId() {
		return userId;
	}




	public void setUserId(String userId) {
		this.userId = userId;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public int getStaffId() {
		return staffId;
	}




	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}




	public boolean getIsAdmin() {
		return isAdmin;
	}




	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}




	public boolean getIsManager() {
		return isManager;
	}




	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}




	public boolean getIsStaff() {
		return isStaff;
	}




	public void setIsStaff(boolean isStaff) {
		this.isStaff = isStaff;
	}




	public User() {
	}

	

}