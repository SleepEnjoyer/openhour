package com.openhour.controller;

import com.openhour.model.ScheduleBlock;
import com.openhour.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/schedule")
    public String schedule(@RequestParam(required = false) String studentName, Model model) {
        model.addAttribute("newBlock", new ScheduleBlock());
        model.addAttribute("studentName", studentName != null ? studentName : "");

        if (studentName != null && !studentName.isBlank()) {
            List<ScheduleBlock> blocks = scheduleService.getBlocksForStudent(studentName);
            model.addAttribute("blocks", blocks);

            Map<String, Map<Integer, List<ScheduleBlock>>> grid = buildWeeklyGrid(blocks);
            model.addAttribute("grid", grid);
        }

        List<String> days = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        model.addAttribute("days", days);

        List<Integer> hours = new ArrayList<>();
        for (int h = 8; h <= 21; h++) hours.add(h);
        model.addAttribute("hours", hours);

        return "schedule";
    }

    @PostMapping("/schedule/add")
    public String addBlock(@ModelAttribute ScheduleBlock block,
                           @RequestParam List<String> selectedDays) {
        for (String day : selectedDays) {
            ScheduleBlock b = new ScheduleBlock(
                    block.getCourseName(), day, block.getStartTime(),
                    block.getEndTime(), block.getStudentName());
            scheduleService.addBlock(b);
        }
        return "redirect:/schedule?studentName=" + block.getStudentName();
    }

    @GetMapping("/schedule/delete/{id}")
    public String deleteBlock(@PathVariable Long id,
                              @RequestParam String studentName) {
        scheduleService.deleteBlock(id);
        return "redirect:/schedule?studentName=" + studentName;
    }

    private Map<String, Map<Integer, List<ScheduleBlock>>> buildWeeklyGrid(List<ScheduleBlock> blocks) {
        Map<String, Map<Integer, List<ScheduleBlock>>> grid = new LinkedHashMap<>();
        for (String day : List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")) {
            grid.put(day, new TreeMap<>());
        }
        for (ScheduleBlock block : blocks) {
            String day = block.getDayOfWeek();
            if (grid.containsKey(day)) {
                try {
                    int startHour = Integer.parseInt(block.getStartTime().split(":")[0]);
                    grid.get(day).computeIfAbsent(startHour, k -> new ArrayList<>()).add(block);
                } catch (NumberFormatException ignored) {}
            }
        }
        return grid;
    }
}
