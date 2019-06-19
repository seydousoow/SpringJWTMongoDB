package com.sid.web;

import com.sid.entities.AppRole;
import com.sid.entities.AppUser;
import com.sid.services.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    private final AccountService accountService;

    public WebController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/users")
    public Page<AppUser> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "24") int size,
                                  @RequestParam(name = "sort", defaultValue = "username") String sort,
                                  @RequestParam(name = "direction", defaultValue = "ASC") String direction){
        return accountService.getUsers(page, size, sort, direction);
    }

    @GetMapping("/users/{id}")
    public AppUser getUser(@PathVariable("id") String user_id){
        return accountService.getUser(user_id);
    }

    @GetMapping("/users/user")
    public AppUser getUserByUsername(@RequestParam("username") String username) {
        return accountService.getByUsername(username);
    }


    @GetMapping("/roles")
    public Page<AppRole> getRoles(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "24") int size,
                                  @RequestParam(name = "sort", defaultValue = "roleName") String sort,
                                  @RequestParam(name = "direction", defaultValue = "ASC") String direction){
        return accountService.getRoles(page, size, sort, direction);
    }

    @GetMapping("/roles/{id}")
    public AppRole getRole(@PathVariable("id") String role_id){
        return accountService.getRole(role_id);
    }

    @PostMapping("/users")
    public AppUser saveUser(@RequestBody AppUser user) {
        return accountService.saveUser(user);
    }

    @PostMapping("/roles")
    public AppRole saveRole(@RequestBody AppRole role) {
        return accountService.saveRole(role);
    }

    @PutMapping("/users")
    public AppUser updateUser(@RequestBody AppUser user) {
        return accountService.updateUser(user);
    }

    @PutMapping("/roles")
    public AppRole updateRole(@RequestBody AppRole role) {
        return accountService.updateRole(role);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") String user_id){
        accountService.deleteUser(user_id);
    }

    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable("id") String role_id){
        accountService.deleteRole(role_id);
    }

}
