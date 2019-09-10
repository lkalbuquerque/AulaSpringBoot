package br.com.dev.repository;

import br.com.dev.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

//ENTIDADE QUE REPRESENTA, E O TIPO DO ID (como parametros)
public interface StudentRepository extends PagingAndSortingRepository<Student,Long> {
    List<Student> findByNameIgnoreCaseContaining(String name);

}
