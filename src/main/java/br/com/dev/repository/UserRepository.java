package br.com.dev.repository;

import br.com.dev.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findByUsername(String username);
}
