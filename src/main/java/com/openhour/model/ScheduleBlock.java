package com.openhour.model;

import jakarta.persistence.*; //LEARN WHAT THIS PACKAGE DOES

@Entity
@Table(name = "schedule_blocks")
public class ScheduleBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String studentName;

    public ScheduleBlock() {} // The JPA requires a default constructor

    // Constructor for convenience
    public ScheduleBlock(String courseName, String dayOfWeek, String startTime,
                         String endTime, String studentName) {
        this.courseName = courseName;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentName = studentName;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}