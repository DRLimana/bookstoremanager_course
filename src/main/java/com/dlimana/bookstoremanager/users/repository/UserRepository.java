package com.dlimana.bookstoremanager.users.repository;

import com.dlimana.bookstoremanager.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
