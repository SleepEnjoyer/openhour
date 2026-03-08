package com.openhour.controller;

import com.openhour.model.Event;
import com.openhour.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DiscoverController {

    private final EventService eventService;

    public DiscoverController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/discover")
    public String discover(@RequestParam(required = false) String studentName,
                           @RequestParam(required = false) String category,
                           Model model) {
        model.addAttribute("studentName", studentName != null ? studentName : "");
        model.addAttribute("selectedCategory", category != null ? category : "all");

        if (studentName != null && !studentName.isBlank()) {
            List<Event> available = eventService.findNonConflictingEvents(studentName, category);
            model.addAttribute("events", available);
        }

        return "discover";
    }
}
