package laby.lab4_spring.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import laby.lab4_spring.model.Student;
import laby.lab4_spring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Tag(name = "Student")
public class StudentRestController {
    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/studenci/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer studentId) {
        return studentService.getStudent(studentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/studenci")
    public Page<Student> getStudents(Pageable pageable) {
        return studentService.getStudents(pageable);
    }

    @GetMapping(value = "/studenci", params = "nazwisko")
    public Page<Student> getStudentsByNazwisko(@RequestParam String nazwisko, Pageable pageable) {
        return studentService.searchByNazwisko(nazwisko, pageable);
    }

    @GetMapping(value = "/studenci", params = "nrIndeksu")
    public Page<Student> getStudentsByNrIndeksu(@RequestParam String nrIndeksu, Pageable pageable) {
        return studentService.searchByNrIndeksu(nrIndeksu, pageable);
    }

    @PostMapping("/studenci")
    public ResponseEntity<Void> createStudent(@Valid @RequestBody Student student) {
        Student created = studentService.saveStudent(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{studentId}")
                .buildAndExpand(created.getStudentId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/studenci/{studentId}")
    public ResponseEntity<Void> updateStudent(@PathVariable Integer studentId, @Valid @RequestBody Student student) {
        return studentService.getStudent(studentId)
                .map(s -> {
                    student.setStudentId(studentId);
                    studentService.saveStudent(student);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/studenci/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer studentId) {
        return studentService.getStudent(studentId)
                .map(s -> {
                    studentService.deleteStudent(studentId);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}