package com.teamtwo.laps.service;



import java.util.ArrayList;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.teamtwo.laps.model.Holiday;
import com.teamtwo.laps.repository.HolidayRepository;






@Service
public class HolidayServiceImpl implements HolidayService {

	@Resource
	private HolidayRepository repository;
	
	@Override
	public ArrayList<Holiday> findAllHoliday() {
		// TODO Auto-generated method stub
		ArrayList<Holiday> ul = (ArrayList<Holiday>) repository.findAll();
		return ul;
	}

	@Override
	public Holiday createHoliday(Holiday holiday) {
		// TODO Auto-generated method stub
		return repository.saveAndFlush(holiday);
	}

	@Override
	public Holiday findHolidayByName(Integer id) {
		// TODO Auto-generated method stub
		return repository.findHolidayByName(id);
	}

	@Override
	public Holiday changeHoliday(Holiday holiday) {
		// TODO Auto-generated method stub
		return repository.saveAndFlush(holiday);
	}

	@Override
	public void removeHoliday(Holiday holiday) {
		// TODO Auto-generated method stub
		repository.delete(holiday);
	}
	


}
