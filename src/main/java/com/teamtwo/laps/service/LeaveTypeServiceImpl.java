package com.teamtwo.laps.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.teamtwo.laps.model.LeaveType;

import com.teamtwo.laps.repository.LeaveTypeRepository;


@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {
	@Resource
	private LeaveTypeRepository ltrepository;
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.LeaveService#findAllLeaveOfStaff(java.lang.Integer)
	 */
	@Override
	@Transactional
	public LeaveType createLeaveType(LeaveType leave) {
		return ltrepository.saveAndFlush(leave);
	}

	@Override
	@Transactional
	public ArrayList<LeaveType> findAllLeaveType() {
		// TODO Auto-generated method stub
		ArrayList<LeaveType> ul = (ArrayList<LeaveType>) ltrepository.findAll();
		return ul;
	}

	@Override
	@Transactional
	public LeaveType findLeaveTypeById(Integer leaveType) {
		// TODO Auto-generated method stub
		return ltrepository.findLeaveTypeById(leaveType);
	}

	@Override
	@Transactional
	public LeaveType changeLeaveType(LeaveType lt) {
		// TODO Auto-generated method stub
		return ltrepository.saveAndFlush(lt);
	}

	@Override
	public void removeLeaveType(LeaveType lt) {
		// TODO Auto-generated method stub
		ltrepository.delete(lt);
	}

	@Override
	public ArrayList<String> findAllLeaveTypeName() {
		// TODO Auto-generated method stub
		return ltrepository.findAllLeaveTypeName();
	}


}
