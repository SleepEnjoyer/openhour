package com.openhour.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private String description;
    private String category;
    private int rsvpCount;

    public Event() {}

    public Event(String eventName, LocalDate eventDate, LocalTime startTime,
                 LocalTime endTime, String location, String description, String category) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
        this.category = category;
        this.rsvpCount = 0;
    }

    public String getDayOfWeek() {
        if (eventDate == null) return "";
        return eventDate.getDayOfWeek().name().substring(0, 1)
                + eventDate.getDayOfWeek().name().substring(1).toLowerCase();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getRsvpCount() { return rsvpCount; }
    public void setRsvpCount(int rsvpCount) { this.rsvpCount = rsvpCount; }
}
