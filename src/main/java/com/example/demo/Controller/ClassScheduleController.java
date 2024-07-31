package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.ClassSchedule;
import com.example.demo.Service.ClassScheduleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/class-schedules")
public class ClassScheduleController {

    @Autowired
    private ClassScheduleService classScheduleService;

    @PostMapping
    public ClassSchedule createClassSchedule(@RequestBody ClassSchedule classSchedule) {
        return classScheduleService.save(classSchedule);
    }

    @GetMapping("/{id}")
    public Optional<ClassSchedule> getClassScheduleById(@PathVariable Long id) {
        return classScheduleService.findById(id);
    }

    @GetMapping("/class/{className}")
    public List<ClassSchedule> getClassSchedulesByClassName(@PathVariable String className) {
        return classScheduleService.findByClassName(className);
    }

    @PutMapping("/{id}")
    public Optional<ClassSchedule> updateClassSchedule(@PathVariable Long id, @RequestBody ClassSchedule classScheduleDetails) {
        return classScheduleService.findById(id).map(classSchedule -> {
            classSchedule.setClassName(classScheduleDetails.getClassName());
            classSchedule.setScheduleTime(classScheduleDetails.getScheduleTime());
            classSchedule.setTeacher(classScheduleDetails.getTeacher());
            classSchedule.setCourse(classScheduleDetails.getCourse());
            return classScheduleService.save(classSchedule);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteClassSchedule(@PathVariable Long id) {
        classScheduleService.deleteById(id);
    }
}
