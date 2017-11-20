package com.teamtwo.laps.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "otcomp")
public class Overtime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "otcompid")
	private Integer id;
	@Column(name = "staff_id")
	private Integer staffId;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date date;
	@Column(name = "logged_hours")
	private Integer loggedHours;
	@Column(name = "claimed_hours")
	private Integer claimedHours;
	public Boolean getWasConfirmed() {
		return wasConfirmed;
	}

	public void setWasConfirmed(Boolean wasConfirmed) {
		this.wasConfirmed = wasConfirmed;
	}

	@Column(name = "approved")
	private Boolean approved;
	@Column(name = "was_confirmed")
	private Boolean wasConfirmed;
	
	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	@ManyToOne
	@JoinColumn(name = "staff_id", insertable = false, updatable = false)
	private StaffMember staffMember;

	public Overtime() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getLoggedHours() {
		return loggedHours;
	}

	public void setLoggedHours(Integer loggedHours) {
		this.loggedHours = loggedHours;
	}

	public Integer getClaimedHours() {
		return claimedHours;
	}

	public void setClaimedHours(Integer claimedHours) {
		this.claimedHours = claimedHours;
	}

	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	@Override
	public String toString() {
		return "Overtime [id=" + id + ", staffId=" + staffId + ", date=" + date + ", loggedHours=" + loggedHours
				+ ", claimedHours=" + claimedHours + ", staffMember=" + staffMember + "]";
	}
	
}
