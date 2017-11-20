package com.teamtwo.laps.model;





import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
//import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "publicholiday")
public class Holiday {
	@Id
	@Column(name = "h_id")
	private int holidayId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
//	@NotEmpty(message="Please enter date")
	private Date date;
	@Column(name = "name")
//	@NotEmpty(message="Please enter name")
	private String name;
	
	
	public Holiday()
	{
		
	}


	public int getHolidayId() {
		return holidayId;
	}


	public void setHolidayId(int holidayId) {
		this.holidayId = holidayId;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	
	
	
}
