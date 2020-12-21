package com.buuble.blog.repository;

import com.buuble.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(Integer userId);

    User findByPhone(String phone);

    User findByUsername(String username);

    User save(User user);
    //void deleteById(Integer userid);
    //void flush();
    //User findByPhoneAndPassword(String phone,String password);
    //User findByUsernameAndPassword(String username,String password);
}
