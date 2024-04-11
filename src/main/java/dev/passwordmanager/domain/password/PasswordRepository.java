package dev.passwordmanager.domain.password;

import dev.passwordmanager.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
    Page<Password> findByUser(User user, Pageable pageable);
}
