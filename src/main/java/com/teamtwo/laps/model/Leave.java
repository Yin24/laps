package com.teamtwo.laps.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.teamtwo.laps.javabeans.LeavePeriodCalculator;
import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.service.HolidayService;

@Entity
@Table(name = "leave_history")
public class Leave {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "leaveid")
	private Integer leaveId;
	@Column(name = "staffid")
	private Integer staffId;
	@Column(name = "leavetype")
	private Integer leaveType;
	@NotNull
	@Size(min=1, message = "Please give a reason for the leave")
	private String reason;
	
	@NotNull(message = "Please give a start date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
//	@Temporal(TemporalType.DATE)
	@Column(name = "startdate")
	private Date startDate;

	@NotNull(message = "Please give an end date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
//	@Temporal(TemporalType.DATE)
	@Column(name = "enddate")
	private Date endDate;
	@NotNull
	@Size(min=1, message = "Please describe what work to be taken over")
	private String dissemination;
	@Column(name = "disseminationid")
	private Integer disseminationId;
	@Column(name = "contactdetails")
	private String contactDetails;

	@Enumerated(EnumType.STRING)
	private LeaveStatus status;
	
	@Column(name = "comment")
	private String comment;
	@Column(name = "wasupdated")
	private Integer wasUpdated;
	@Column(name = "is_half_day")
	private int isHalfDay;
	
	@ManyToOne
	@JoinColumn(name = "staffid", insertable = false, updatable = false)
	private StaffMember staffMember;
	
	@ManyToOne
	@JoinColumn(name = "disseminationid", insertable = false, updatable = false)
	private StaffMember disseminationMember;
	
	@ManyToOne
	@JoinColumn(name = "leavetype", insertable = false, updatable = false)
	private LeaveType leaveTypeModel;
	
	public Leave() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(Integer leaveId) {
		this.leaveId = leaveId;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public Integer getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(Integer leaveType) {
		this.leaveType = leaveType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDissemination() {
		return dissemination;
	}
	public void setDissemination(String dissemination) {
		this.dissemination = dissemination;
	}
	public Integer getDisseminationId() {
		return disseminationId;
	}
	public void setDisseminationId(Integer disseminationId) {
		this.disseminationId = disseminationId;
	}
	public LeaveStatus getStatus() {
		return status;
	}
	public void setStatus(LeaveStatus status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getWasUpdated() {
		return wasUpdated;
	}
	public void setWasUpdated(Integer wasUpdated) {
		this.wasUpdated = wasUpdated;
	}
	
	public int getIsHalfDay() {
		return isHalfDay;
	}

	public void setIsHalfDay(int isHalfDay) {
		this.isHalfDay = isHalfDay;
	}

	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public StaffMember getDisseminationMember() {
		return disseminationMember;
	}

	public void setDisseminationMember(StaffMember disseminationMember) {
		this.disseminationMember = disseminationMember;
	}

	public LeaveType getLeaveTypeModel() {
		return leaveTypeModel;
	}

	public void setLeaveTypeModel(LeaveType leaveTypeModel) {
		this.leaveTypeModel = leaveTypeModel;
	}
	
	public Double getNumberOfDays(List<Holiday> holidays) {
		return LeavePeriodCalculator.calculateLeaveDays(this, holidays);
//		return (int) TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString() {
		return "Leave [leaveId=" + leaveId + ", staffId=" + staffMember.getStaffId() + ", leaveType=" + leaveType + ", reason=" + reason
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", dissemination=" + dissemination
				+ ", disseminationId=" + disseminationId + ", status=" + status + ", comment=" + comment
				+ ", wasUpdated=" + wasUpdated + ", contactDetails=" + contactDetails + "]";
	}
	
	
}
