package com.example.androidserver.repository;

import com.example.androidserver.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUserId(String userId);
}
