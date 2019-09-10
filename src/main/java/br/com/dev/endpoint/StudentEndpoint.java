package br.com.dev.endpoint;


import br.com.dev.error.CustomErrorType;
import br.com.dev.error.ResourceNotFoundException;
import br.com.dev.model.Student;
import br.com.dev.repository.StudentRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("v1")
public class StudentEndpoint {

    private final StudentRepository dao;

    //@autowired Injeção das dependencias e Instanciar o Objeto
    public StudentEndpoint(StudentRepository dao) {
        this.dao = dao;
    }

    @GetMapping(path = "protected/students")
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") long id, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails);
        verifyIfStudentsExists(id);
        Optional<Student> student = dao.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/findByName/{name}")
    public ResponseEntity findStudentsByName(@PathVariable String name) {
        return new ResponseEntity<>(dao.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping(path = "admin/students")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(dao.save(student), HttpStatus.OK);
    }

    @DeleteMapping(path = "admin/students/{id}")
    //@PreAuthorize("hasRole('ADMIN')") // PRA EXECUTAR 'DELETE' É PRECISO TER A RULE 'ADMIN'
    public ResponseEntity<?> delete(@PathVariable long id) {
        verifyIfStudentsExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "admin/students")
    public ResponseEntity<?> update(@RequestBody Student student) {
        verifyIfStudentsExists(student.getId());
        dao.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentsExists(Long id) {
        Optional<Student> student = dao.findById(id);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException("Student Not Found for ID: " + id);
        }
    }
}
