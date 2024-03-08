package com.twendeno.msauth;

import com.twendeno.msauth.privilege.Privilege;
import com.twendeno.msauth.privilege.PrivilegeRepository;
import com.twendeno.msauth.role.Role;
import com.twendeno.msauth.role.RoleRepository;
import com.twendeno.msauth.role.RoleType;
import com.twendeno.msauth.user.User;
import com.twendeno.msauth.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // Create initial privileges
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
        Privilege updatePrivilege = createPrivilegeIfNotFound("UPDATE_PRIVILEGE");

        List<Privilege> superAdminPrivileges = Arrays.asList(readPrivilege, writePrivilege, deletePrivilege, updatePrivilege);
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, updatePrivilege);
        List<Privilege> userPrivileges = Collections.singletonList(readPrivilege);

        // Create initial roles
       createRoleIfNotFound(RoleType.ROLE_SUPER_ADMIN, superAdminPrivileges);
       createRoleIfNotFound(RoleType.ROLE_ADMIN, adminPrivileges);
       createRoleIfNotFound(RoleType.ROLE_USER, userPrivileges);

        // Create initial user
        Role adminRole = roleRepository.findByName(RoleType.ROLE_SUPER_ADMIN).get();
        User user = new User();
        user.setName("Super Admin");
        user.setEmail("super-admin@twendeno.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(List.of(adminRole));
        user.setEnable(true);

        userRepository.save(user);

        alreadySetup = true;

    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = null;

        if (privilegeRepository.findByName(name).isPresent()){
            privilege = privilegeRepository.findByName(name).get();
        }

        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    void createRoleIfNotFound(RoleType name, Collection<Privilege> privileges) {
        Role role = null;
        if (roleRepository.findByName(name).isPresent()){
            role = roleRepository.findByName(name).get();
        }
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }
}
