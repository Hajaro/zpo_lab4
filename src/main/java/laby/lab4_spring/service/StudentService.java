package laby.lab4_spring.service;

import laby.lab4_spring.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {
    Optional<Student> getStudent(Integer studentId);
    Optional<Student> getStudentByNrIndeksu(String nrIndeksu);
    Student saveStudent(Student student);
    void deleteStudent(Integer studentId);
    Page<Student> getStudents(Pageable pageable);
    Page<Student> searchByNazwisko(String nazwisko, Pageable pageable);
    Page<Student> searchByNrIndeksu(String nrIndeksu, Pageable pageable);
}