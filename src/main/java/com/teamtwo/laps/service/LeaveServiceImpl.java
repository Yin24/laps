package com.teamtwo.laps.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.LeaveType;
import com.teamtwo.laps.repository.LeaveRepository;

@Service
public class LeaveServiceImpl implements LeaveService {
	@Resource
	private LeaveRepository repository;
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.LeaveService#findAllLeaveOfStaff(java.lang.Integer)
	 */
	@Override
	@Transactional
	public ArrayList<Leave> findAllLeaveOfStaff(Integer staffId) {
		return repository.findAllLeaveOfStaff(staffId);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.LeaveService#findLeaveByType(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional
	public ArrayList<Leave> findLeaveByType(Integer staffId, Integer leaveType) {
		return repository.findLeaveByType(leaveType, staffId);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.LeaveService#findLeaveById(java.lang.Integer)
	 */
	@Override
	@Transactional
	public Leave findLeaveById(Integer leaveId) {
		return repository.findOne(leaveId);
	}

	@Override
	public Leave createLeave(Leave leave) {
		return repository.saveAndFlush(leave);

	}
	public Leave changeLeave(Leave leave) {
		// TODO Auto-generated method stub
		return repository.saveAndFlush(leave);
	}

	@Override
	public ArrayList<Leave> findAllLeave() {
		return (ArrayList<Leave>) repository.findAll();
	}
	
	//Yin
	@Override
	@Transactional
	public ArrayList<Leave> findStaffLeaveHistory(Integer sid) {
		ArrayList<Leave> leavel = (ArrayList<Leave>) repository.getLeaveHistoryBySID(sid);
		return leavel;
	}
	
	//Huitian
	public void cancelLeave(Leave lt, LeaveStatus ls) {
		// TODO Auto-generated method stub
		lt.setStatus(ls);
		repository.saveAndFlush(lt);
	}
	
	public void DeleteLeave(Leave lt, LeaveStatus ls) {
		// TODO Auto-generated method stub
		lt.setStatus(ls);
		repository.saveAndFlush(lt);
	}

	@Override
	@Transactional
	public ArrayList<Leave> findPendingLeaveByType(Integer staffId) {
		ArrayList<Leave> leavel = (ArrayList<Leave>) repository.findPendingLeaveByType(staffId);
		// TODO Auto-generated method stub
		return leavel;
	}
	
	@Override
	@Transactional
	public ArrayList<Leave> findAllLeaveOfSubordinate(Integer managerId) {
		ArrayList<Leave> leavel = (ArrayList<Leave>) repository.findAllLeaveOfSubordinate(managerId);
		// TODO Auto-generated method stub
		return leavel;
	}

	@Override
	public Integer findStaffId(Integer id) {
		// TODO Auto-generated method stub
		return repository.findStaffId(id);
	}
}
