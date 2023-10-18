package com.kusitms.mainservice.domain.user.repository;




import com.kusitms.mainservice.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

   // void insert(User user);
//    Optional<User> findByEmail(String email);
//    Optional<User> findByLoginId(String username);
  //  boolean existsByEmail(String email);
    //public User findByUsername(String username);
}
