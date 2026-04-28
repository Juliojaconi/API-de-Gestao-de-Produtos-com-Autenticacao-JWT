package br.com.krono.exerciciossb.model.repositories;

import br.com.krono.exerciciossb.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findUserByLogin(String login);

}
