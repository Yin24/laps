package com.teamtwo.laps.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.repository.StaffMemberRepository;

@Service
public class StaffMemberServiceImpl implements StaffMemberService {
	@Resource
	private StaffMemberRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamtwo.laps.service.StaffMemberService#findAllStaff()
	 */
	@Override
	@Transactional
	public ArrayList<StaffMember> findAllStaff() {
		return (ArrayList<StaffMember>) repository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teamtwo.laps.service.StaffMemberService#findStaffById(java.lang.
	 * Integer)
	 */
	@Override
	@Transactional
	public StaffMember findStaffById(Integer staffId) {
		return repository.findOne(staffId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teamtwo.laps.service.StaffMemberService#findStaffMemberById(java.lang
	 * .Integer)
	 */
	@Override
	@Transactional
	public StaffMember findStaffMemberById(Integer staffId) {
		return repository.findStaffMemberById(staffId);
	}

	@Override
	public ArrayList<StaffMember> findSubordinates(Integer staffId) {
		// TODO Auto-generated method stub
		return repository.findSubordinates(staffId);
	}

	@Override
	public ArrayList<Integer> findAllStaffId() {
		// TODO Auto-generated method stub
		return repository.findAllStaffId();
	}
	// Yin
	@Override
	@Transactional
	public ArrayList<StaffMember> showBySubordinateName() {
		ArrayList<StaffMember> subordinatelist = (ArrayList<StaffMember>) repository.findAll();
		return subordinatelist;
	}

	@Override
	@Transactional
	public ArrayList<String> getOnlyStaffName() {
		ArrayList<String> staffNameList = repository.getOnlyStaffName();
		return staffNameList;
	}

	@Override
	@Transactional
	public StaffMember findStaff(Integer sid) {
		return repository.findOne(sid);
	}

	//htein
	@Override
	public StaffMember createStaff(StaffMember staff) {
		// TODO Auto-generated method stub
		return repository.saveAndFlush(staff);
	}

	@Override
	public void removeStaff(StaffMember staff) {
		// TODO Auto-generated method stub
		repository.delete(staff);
	}

	@Override
	public ArrayList<StaffMember> findAllStaffExcept(Integer staffId) {
		// TODO Auto-generated method stub
		ArrayList<StaffMember> allExcept = repository.findAllStaffExcept(staffId);
		return allExcept;
	}
}
