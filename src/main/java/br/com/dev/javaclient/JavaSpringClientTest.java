package br.com.dev.javaclient;

import br.com.dev.model.PageableResponse;
import br.com.dev.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JavaSpringClientTest {
    public static void main(String[] args) {

        Student studentPost = new Student();
        studentPost.setName("John Wick2");
        studentPost.setEmail("john@pencil.com");
       // studentPost.setId(4L);
        JavaCliientDao dao = new JavaCliientDao();
        System.out.println(dao.findById(111));
//        System.out.println(dao.listAll());
//        System.out.println(dao.save(studentPost));
        //dao.update(studentPost);
        //dao.delete(3);

    }


}
