package com.teamtwo.laps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.laps.model.Overtime;
import com.teamtwo.laps.repository.OvertimeRepository;

@Service
public class OvertimeServiceImpl implements OvertimeService {

	@Resource
	private OvertimeRepository repository;
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findOvertimeOfStaff(java.lang.Integer)
	 */
	@Override
	@Transactional
	public ArrayList<Overtime> findOvertimeOfStaff(Integer staffId) {
		return repository.findOvertimeOfStaff(staffId);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findAllOvertime()
	 */
	@Override
	@Transactional
	public ArrayList<Overtime> findAllOvertime() {
		return (ArrayList<Overtime>) repository.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findOvertimeById(java.lang.Integer)
	 */
	@Override
	@Transactional
	public Overtime findOvertimeById(Integer id) {
		return repository.findOne(id);
	}

	@Override
	@Transactional
	public void deleteOvertime(Integer id) {
		repository.delete(id);
	}
	
	@Override
	@Transactional
	public void saveOvertimes(List<Overtime> overtimes) {
		repository.save(overtimes);
//		repository.flush();
	}
	
	@Override
	@Transactional
	public void updateOvertimes(List<Overtime> overtimes) {
//		List<Overtime> toSave = new ArrayList<>();
		for (Overtime overtime : overtimes) {
			Overtime storedOt = repository.findOne(overtime.getId());
			storedOt.setLoggedHours(overtime.getLoggedHours());
			storedOt.setDate(overtime.getDate());
			storedOt.setApproved(overtime.getApproved());
			storedOt.setWasConfirmed(overtime.getWasConfirmed());
//			toSave.add(storedOt);
			repository.saveAndFlush(storedOt);
		}
		//repository.save(toSave);
//		repository.flush();
	}
	
	@Override
	@Transactional
	public void claimHours(Integer staffId, Integer hours) {
		List<Overtime> overtimes = findOvertimeOfStaff(staffId);
		// Filter to those that have not fully been claimed, and sort it by lowest id first
		overtimes = overtimes.stream().filter(ot -> ot.getLoggedHours() != ot.getClaimedHours() && ot.getApproved()).sorted((a, b) -> a.getId().compareTo(b.getId())).collect(Collectors.toList());
		
		for (Overtime overtime : overtimes) {
			Integer claimableHours = overtime.getLoggedHours() - overtime.getClaimedHours();
			// If the difference is negative, resolve it
			if (claimableHours < 0) {
				hours += -1 * claimableHours;
				overtime.setClaimedHours(overtime.getLoggedHours());
			} else {
				// minus the hours
				if (hours > claimableHours) {
					overtime.setClaimedHours(overtime.getLoggedHours());
					hours -= claimableHours;
				} else {
					// minus a part, and done
					overtime.setClaimedHours(hours);
					break;
				}
			}
		}
		
		updateOvertimes(overtimes);
	}
	
	@Override
	@Transactional
	public void unclaimHours(Integer staffId, Integer hoursToUnclaim) {
		List<Overtime> overtimes = findOvertimeOfStaff(staffId);
		// Filter to those that have been at least part claimed, and sort it by highest id first
		overtimes = overtimes.stream().filter(ot -> ot.getClaimedHours() > 0 && ot.getApproved()).sorted((a, b) -> b.getId().compareTo(a.getId())).collect(Collectors.toList());
		
		for (Overtime overtime : overtimes) {
			if (hoursToUnclaim >= overtime.getClaimedHours()) {
				hoursToUnclaim -= overtime.getClaimedHours();
				overtime.setClaimedHours(0);
			} else {
				Integer claimHours = overtime.getClaimedHours();
				overtime.setClaimedHours(claimHours - hoursToUnclaim);
				break;
			}
		}
		
		updateOvertimes(overtimes);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findUnclaimedOvertimeOfStaff(java.lang.Integer)
	 */
	@Override
	public Integer findUnclaimedHoursOfStaff(Integer staffId) {
		int unclaimedHours;
		try {
			unclaimedHours = repository.findUnclaimedOvertimeOfStaff(staffId);
		} catch (Exception e) {
			unclaimedHours = 0;
		}
		return unclaimedHours;
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findLoggedHoursOfStaff(java.lang.Integer)
	 */
	@Override
	public Integer findLoggedHoursOfStaff(Integer staffId) {
		return repository.findLoggedHoursOfStaff(staffId);
	}
	
	/* (non-Javadoc)
	 * @see com.teamtwo.laps.service.OvertimeService#findClaimedHoursOfStaff(java.lang.Integer)
	 */
	@Override
	public Integer findClaimedHoursOfStaff(Integer staffId) {
		return repository.findClaimedHoursOfStaff(staffId);
	}
}