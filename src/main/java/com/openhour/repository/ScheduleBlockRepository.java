package com.openhour.repository;

import com.openhour.model.ScheduleBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleBlockRepository extends JpaRepository<ScheduleBlock, Long> {

    List<ScheduleBlock> findByStudentName(String studentName);

    List<ScheduleBlock> findByDayOfWeek(String dayOfWeek);
}