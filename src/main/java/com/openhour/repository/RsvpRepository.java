package com.openhour.repository;

import com.openhour.model.Rsvp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RsvpRepository extends JpaRepository<Rsvp, Long> {
    Optional<Rsvp> findByStudentNameAndEventId(String studentName, Long eventId);
    List<Rsvp> findByEventId(Long eventId);
    int countByEventId(Long eventId);
}
