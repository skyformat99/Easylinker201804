package com.sucheon.box.server.app.dao;

import com.sucheon.box.server.app.model.user.AppUser;
import com.sucheon.box.server.app.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findAllByAppUser(AppUser appUser);
}
