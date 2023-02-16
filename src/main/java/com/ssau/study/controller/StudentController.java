package com.ssau.study.controller;

import com.ssau.study.entity.Student;
import com.ssau.study.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    private StudentRepository studentRepository;

    @GetMapping("/count")
    public int count() {
        return studentRepository.count();
    }

    @GetMapping("/all")
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @GetMapping("/all/{name}")
    public List<Student> findAllByName(@PathVariable String name) {
        return studentRepository.findAllByName(name);
    }

    @PostMapping()
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping()
    public Student updateStudent(@RequestBody Student student) {
        return studentRepository.update(student);
    }

    @DeleteMapping("/{id}")
    public boolean deleteStudent(@PathVariable long id) {
        return studentRepository.delete(id);
    }

    @GetMapping("/{id}")
    public Student findStudentById(@PathVariable long id) {
        return studentRepository.findById(id);
    }
}
