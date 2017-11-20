package com.teamtwo.laps.repository;






import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teamtwo.laps.model.Holiday;





public interface HolidayRepository extends  JpaRepository<Holiday, String> {


	@Query("SELECT e FROM Holiday e where e.holidayId = :id")
	Holiday findHolidayByName(@Param("id") Integer id);
	
}
