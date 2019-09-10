package br.com.dev;


import br.com.dev.model.Student;
import br.com.dev.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Rule
    public ExpectedException thrown = ExpectedException.none(); // Quais exceções esperamos acontecer, se não teste falho

    @Test
    public void createShouldPersistData() {
        Student student = new Student("Lucas", "lucas@outlook.com");
        this.studentRepository.save(student);
        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo("Lucas");
        assertThat(student.getEmail()).isEqualTo("lucas@outlook.com");

    }

    @Test
    public void deleteShouldRemoveData() {
        Student student = new Student("Lucas", "lucas@outlook.com");
        this.studentRepository.save(student);
        studentRepository.delete(student);
        assertThat(studentRepository.findById(student.getId())).isEmpty();
    }

    @Test
    public void updateShouldChangeAndPersistData() {
        Student student = new Student("Lucas", "lucas@outlook.com");
        this.studentRepository.save(student);
        student.setName("Lucas2");
        student.setEmail("lucas2@outlook.com");
        student = this.studentRepository.save(student);
        //Optional<Student> studento = this.studentRepository.findById(student.getId());
        assertThat(student.getName()).isEqualTo("Lucas2");
        assertThat(student.getEmail()).isEqualTo("lucas2@outlook.com");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
        Student student = new Student("Lucas", "lucas@outlook.com");
        Student student2 = new Student("lucas", "lucas22@outlook.com");
        this.studentRepository.save(student);
        this.studentRepository.save(student2);
        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("lucas");
        assertThat(studentList.size()).isEqualTo(2);
    }

    @Test
    public void createWhenNameIsNullShouldThrowCostraintViolationException(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("o campo nome do estudante é obrigatório");
        this.studentRepository.save(new Student());
    }

    @Test
    public void createWhenEmailIsNullShouldThrowCostraintViolationException(){
        thrown.expect(ConstraintViolationException.class);
        Student student = new Student();
        student.setName("Lucas");
        this.studentRepository.save(student);
    }

    @Test
    public void createWhenEmailIsNotValidShouldThrowCostraintViolationException(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Digite um email válido");
        Student student = new Student();
        student.setName("Lucas");
        student.setEmail("lucas");
        this.studentRepository.save(student);
    }
}
