package com.ssau.study.repository;

import com.ssau.study.entity.Student;

import java.util.List;

public interface StudentRepository {
    int count();

    List<Student> findAll();

    List<Student> findAllByName(String name);

    Student save(Student student);

    Student update(Student student);

    boolean delete(long id);

    Student findById(long id);
}
