package com.sid.services;

import com.sid.entities.AppRole;
import com.sid.entities.AppUser;
import org.springframework.data.domain.Page;

public interface AccountService {
    Page<AppUser> getUsers(int page, int size, String sortBy, String direction);
    AppUser getUser(String user_id);
    Page<AppRole> getRoles(int page, int size, String sortBy, String direction);
    AppRole getRole(String role_id);

    AppUser getByUsername(String username) throws RuntimeException;

    AppUser saveUser(AppUser appUser);
    AppRole saveRole(AppRole appRole);

    AppUser updateUser(AppUser appUser);
    AppRole updateRole(AppRole appRole);

    void deleteUser(String user_id);
    void deleteRole(String role_id);

    AppUser findByUsername(String username);

}
