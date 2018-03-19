package com.sucheon.box.server.app.dao;

import com.sucheon.box.server.app.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findTopByUsernameOrEmailOrPhone(String arg1, String arg2, String arg3);

    AppUser findTopByUsername(String username);
}
