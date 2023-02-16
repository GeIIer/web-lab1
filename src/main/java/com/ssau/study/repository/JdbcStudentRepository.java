package com.ssau.study.repository;

import com.ssau.study.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcStudentRepository implements StudentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Student> studentMapper = (rs, rowNum) -> {
        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setName(rs.getString("name"));
        student.setBirthdate(rs.getDate("birthdate"));
        student.setNumber(rs.getInt("number"));
        return student;
    };

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from public.students", Integer.class);
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query("select * from public.students", studentMapper);
    }

    @Override
    public List<Student> findAllByName(String name) {
        return namedParameterJdbcTemplate.query("select * from public.students where name ilike '%' || :name || '%'",
                Collections.singletonMap("name", name), studentMapper);
    }

    @Override
    public Student save(Student student) {
        return namedParameterJdbcTemplate.queryForObject(
                "INSERT INTO public.students VALUES (nextval('students_id_seq'), :name, :birthdate, :number) " +
                        "RETURNING *",
                Map.ofEntries(
                        Map.entry("name", student.getName()),
                        Map.entry("birthdate", student.getBirthdate()),
                        Map.entry("number", student.getNumber())
                ), studentMapper
        );
    }

    @Override
    public Student update(Student student) {
        return namedParameterJdbcTemplate.queryForObject(
                "UPDATE public.students SET name = :name, birthdate = :birthdate, number = :number WHERE id = :id " +
                        "RETURNING *",
                Map.ofEntries(
                        Map.entry("id", student.getId()),
                        Map.entry("name", student.getName()),
                        Map.entry("birthdate", student.getBirthdate()),
                        Map.entry("number", student.getNumber())
                ), studentMapper
        );
    }

    @Override
    public boolean delete(long id) {
        return (namedParameterJdbcTemplate.update("DELETE FROM public.students WHERE id = :id",
                Collections.singletonMap("id", id)) != 0);
    }

    @Override
    public Student findById(long id) {
        try {
            return namedParameterJdbcTemplate.queryForObject("SELECT * FROM public.students WHERE id = :id",
                    Collections.singletonMap("id", id), studentMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
