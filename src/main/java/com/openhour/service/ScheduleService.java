package com.openhour.service;

import com.openhour.model.ScheduleBlock;
import com.openhour.repository.ScheduleBlockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleBlockRepository repository;

    public ScheduleService(ScheduleBlockRepository repository) {
        this.repository = repository;
    }

    public void addBlock(ScheduleBlock block) {
        repository.save(block);
    }

    public List<ScheduleBlock> getBlocksForStudent(String studentName) {
        return repository.findByStudentName(studentName);
    }

    public List<ScheduleBlock> getAllBlocks() {
        return repository.findAll();
    }

    public void deleteBlock(Long id) {
        repository.deleteById(id);
    }

    public List<String> findFreeSlots(String dayOfWeek) {
        List<ScheduleBlock> blocks = repository.findByDayOfWeek(dayOfWeek);

        List<String> allSlots = new ArrayList<>();
        for (int hour = 8; hour < 22; hour++) {
            allSlots.add(String.format("%02d:00 - %02d:30", hour, hour));
            allSlots.add(String.format("%02d:30 - %02d:00", hour, hour + 1));
        }

        List<String> freeSlots = new ArrayList<>();
        for (String slot : allSlots) {
            String slotStart = slot.split(" - ")[0];
            String slotEnd = slot.split(" - ")[1];

            boolean isTaken = false;
            for (ScheduleBlock block : blocks) {
                if (slotStart.compareTo(block.getEndTime()) < 0
                        && slotEnd.compareTo(block.getStartTime()) > 0) {
                    isTaken = true;
                    break;
                }
            }

            if (!isTaken) {
                freeSlots.add(slot);
            }
        }

        return freeSlots;
    }

    public boolean hasConflict(String studentName, String dayOfWeek,
                               String eventStartTime, String eventEndTime) {
        List<ScheduleBlock> blocks = repository.findByStudentName(studentName);
        for (ScheduleBlock block : blocks) {
            if (block.getDayOfWeek().equalsIgnoreCase(dayOfWeek)) {
                if (eventStartTime.compareTo(block.getEndTime()) < 0
                        && eventEndTime.compareTo(block.getStartTime()) > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
