package com.openhour.service;

import com.openhour.model.Event;
import com.openhour.model.Rsvp;
import com.openhour.repository.EventRepository;
import com.openhour.repository.RsvpRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final RsvpRepository rsvpRepository;
    private final ScheduleService scheduleService;

    public EventService(EventRepository eventRepository, RsvpRepository rsvpRepository,
                        ScheduleService scheduleService) {
        this.eventRepository = eventRepository;
        this.rsvpRepository = rsvpRepository;
        this.scheduleService = scheduleService;
    }

    public void createEvent(Event event) {
        event.setRsvpCount(0);
        eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public int incrementRsvp(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setRsvpCount(event.getRsvpCount() + 1);
        eventRepository.save(event);
        return event.getRsvpCount();
    }

    public int decrementRsvp(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setRsvpCount(Math.max(0, event.getRsvpCount() - 1));
        eventRepository.save(event);
        return event.getRsvpCount();
    }

    public List<Event> findNonConflictingEvents(String studentName, String categoryFilter) {
        List<Event> allEvents;
        if (categoryFilter != null && !categoryFilter.isEmpty() && !categoryFilter.equals("all")) {
            allEvents = eventRepository.findByCategory(categoryFilter);
        } else {
            allEvents = eventRepository.findAll();
        }

        return allEvents.stream()
                .filter(event -> {
                    String dayOfWeek = event.getDayOfWeek();
                    String startTime = event.getStartTime().toString();
                    String endTime = event.getEndTime().toString();
                    return !scheduleService.hasConflict(studentName, dayOfWeek, startTime, endTime);
                })
                .collect(Collectors.toList());
    }
}
