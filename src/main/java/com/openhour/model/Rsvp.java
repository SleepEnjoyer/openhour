package com.openhour.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rsvps", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"studentName", "event_id"})
})
public class Rsvp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Rsvp() {}

    public Rsvp(String studentName, Event event) {
        this.studentName = studentName;
        this.event = event;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
}
