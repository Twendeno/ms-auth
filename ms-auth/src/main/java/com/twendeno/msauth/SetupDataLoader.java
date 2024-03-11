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
import org.springframework.stereotype.Component;

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

        // Create initial roles
        createRoleIfNotFound(RoleType.SUPER_ADMIN);
        createRoleIfNotFound(RoleType.ADMIN);
        createRoleIfNotFound(RoleType.USER);
        createRoleIfNotFound(RoleType.MANAGER);

        // Create initial user
        Role superAdminRole = roleRepository.findByName(RoleType.SUPER_ADMIN).get();
        Role adminRole = roleRepository.findByName(RoleType.ADMIN).get();

        List<Privilege> superAdminPrivileges = RoleType.SUPER_ADMIN.getPrivileges().stream()
                .map(privilegeType -> createPrivilegeIfNotFound(privilegeType.name(), superAdminRole)).toList();

        List<Privilege> adminPrivileges = RoleType.ADMIN.getPrivileges().stream()
                .map(privilegeType -> createPrivilegeIfNotFound(privilegeType.name(), adminRole)).toList();

        User user = new User();
        user.setName("Super Admin");
        user.setEmail("superadmin@twendeno.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(superAdminRole);
        user.setEnable(true);

        userRepository.findByEmail(user.getEmail()).ifPresentOrElse(
                userFound -> {
                    user.setUuid(userFound.getUuid());
                    userRepository.save(user);
                },
                () -> userRepository.save(user)
        );

        alreadySetup = true;

    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name, Role role) {
        Privilege privilege = null;

        if (privilegeRepository.findByName(name).isPresent()) {
            privilege = privilegeRepository.findByName(name).get();
        }

        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilege.setRole(role);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    void createRoleIfNotFound(RoleType name) {
        Role role = null;
        if (roleRepository.findByName(name).isPresent()) {
            role = roleRepository.findByName(name).get();
        }
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
    }
}
