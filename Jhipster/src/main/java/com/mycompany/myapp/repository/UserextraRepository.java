package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Userextra;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Userextra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserextraRepository extends JpaRepository<Userextra, Long> {
    Optional<User> findOneByUser(User user);
}
