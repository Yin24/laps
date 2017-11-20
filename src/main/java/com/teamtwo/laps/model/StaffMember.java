package com.teamtwo.laps.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.print.attribute.standard.MediaSize.Other;

import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.service.HolidayService;
import com.teamtwo.laps.service.OvertimeService;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "staff_list")
public class StaffMember {
	@Id
	@Column(name = "staff_id")
	private int staffId;
	
	@NotEmpty(message = "Please enter employee name.")
	private String name;
	
	@Column(name = "contact_no")
	private int contactNo;
	
	@NotEmpty(message = "Please enter email address")
	@Email (message = "Please right correct email format")
	private String email;
	
	@Column(name = "home_address")
	@NotEmpty(message = "Please enter home address")
	private String homeAddress;
	
	@NotEmpty(message = "Please enter designation ")
	private String designation;
	
	@Column(name = "a_leave")
	private int aLeave;
	@Column(name = "m_leave")
	private int mLeave;
	@Column(name = "c_leave")
	private int cLeave;
	
	@Column(name = "reports_to")
	private int managerId;
	@Column(name = "total_hours_claimed")
	private int totalHoursClaimed;

	@OneToMany(mappedBy = "staffMember", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Leave> appliedLeaves = new ArrayList<Leave>();

	public StaffMember() {

		// TODO Auto-generated constructor stub
	}

	public StaffMember(int staffId, String name, int contactNo, String email, String homeAddress, String designation,
			int aLeave, int mLeave, int cLeave, int managerId, int totalHoursClaimed) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.contactNo = contactNo;
		this.email = email;
		this.homeAddress = homeAddress;
		this.designation = designation;
		this.aLeave = aLeave;
		this.mLeave = mLeave;
		this.cLeave = cLeave;
		this.managerId = managerId;
		this.totalHoursClaimed = totalHoursClaimed;
	}

	public StaffMember(int staffId) {
		this();
		setStaffId(staffId);
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getContactNo() {
		return contactNo;
	}

	public void setContactNo(int contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getaLeave() {
		return aLeave;
	}

	public void setaLeave(int aLeave) {
		this.aLeave = aLeave;
	}

	public int getmLeave() {
		return mLeave;
	}

	public void setmLeave(int mLeave) {
		this.mLeave = mLeave;
	}

	public int getcLeave() {
		return cLeave;
	}

	public void setcLeave(int cLeave) {
		this.cLeave = cLeave;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public int getTotalHoursClaimed() {
		return totalHoursClaimed;
	}

	public void setTotalHoursClaimed(int totalHoursClaimed) {
		this.totalHoursClaimed = totalHoursClaimed;
	}

	public List<Leave> getAppliedLeaves() {
		return appliedLeaves;
	}

	public void setAppliedLeaves(List<Leave> appliedLeaves) {
		this.appliedLeaves = appliedLeaves;
	}
	
	public Double getAvailableLeaveDaysOfType(Integer leaveTypeId, List<Holiday> holidays, OvertimeService otService) {
		Double result = appliedLeaves.stream()
				.filter(a -> a.getStaffId() == this.getStaffId() && 
				a.getLeaveType() == leaveTypeId
					&& (a.getStatus() == LeaveStatus.APPROVED
					|| a.getStatus() == LeaveStatus.PENDING
					|| a.getStatus() == LeaveStatus.UPDATED))
				.map(x -> x.getNumberOfDays(holidays))
				.reduce(0.0, ((a, b) -> a + b));
		
		Double available = 0.0;
		switch (leaveTypeId) {
		case 1:
			// Annual
			available = aLeave * 1.0 - result;
			break;
		case 2:
			// Medical
			available = mLeave * 1.0 - result;
			break;
		case 3:
			// Compensation
			available = otService.findUnclaimedHoursOfStaff(staffId) * 1.0 / 8;
			break;

		default:
			break;
		}
		return available;
	}

	@Override
	public String toString() {
		return "StaffMember [staffId=" + staffId + ", name=" + name + ", contactNo=" + contactNo + ", email=" + email
				+ ", homeAddress=" + homeAddress + ", designation=" + designation + ", aLeave=" + aLeave + ", mLeave="
				+ mLeave + ", cLeave=" + cLeave + ", managerId=" + managerId + ", totalHoursClaimed="
				+ totalHoursClaimed + "]";
	}

}
