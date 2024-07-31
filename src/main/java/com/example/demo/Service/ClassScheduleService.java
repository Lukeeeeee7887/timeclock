package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.ClassSchedule;
import com.example.demo.Repository.ClassScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClassScheduleService {

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    public ClassSchedule save(ClassSchedule classSchedule) {
        return classScheduleRepository.save(classSchedule);
    }

    public Optional<ClassSchedule> findById(Long id) {
        return classScheduleRepository.findById(id);
    }

    public List<ClassSchedule> findAll() {
        return classScheduleRepository.findAll();
    }

    public List<ClassSchedule> findByClassName(String className) {
        return classScheduleRepository.findByClassName(className);
    }

    public void deleteById(Long id) {
        classScheduleRepository.deleteById(id);
    }
}
