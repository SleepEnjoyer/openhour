package com.openhour.controller;

import com.openhour.model.Event;
import com.openhour.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public String events(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("newEvent", new Event());
        return "events";
    }

    @PostMapping("/events/add")
    public String addEvent(@ModelAttribute Event event) {
        eventService.createEvent(event);
        return "redirect:/events";
    }

    @PostMapping("/events/rsvp")
    @ResponseBody
    public java.util.Map<String, Object> toggleRsvp(@RequestParam Long eventId, @RequestParam String action) {
        int newCount;
        if ("unrsvp".equals(action)) {
            newCount = eventService.decrementRsvp(eventId);
        } else {
            newCount = eventService.incrementRsvp(eventId);
        }
        return java.util.Map.of("rsvpCount", newCount);
    }
}
