package com.teamtwo.laps.service;


import java.util.ArrayList;


import com.teamtwo.laps.model.Holiday;

public interface HolidayService {

	ArrayList<Holiday> findAllHoliday();
	
	Holiday createHoliday(Holiday holiday);
	
	Holiday findHolidayByName(Integer id);
	
	Holiday changeHoliday(Holiday holiday);
	
	void removeHoliday(Holiday holiday);
}