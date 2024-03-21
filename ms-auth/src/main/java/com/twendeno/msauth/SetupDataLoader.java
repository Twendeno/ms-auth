package com.twendeno.msauth;

import com.twendeno.msauth.license.License;
import com.twendeno.msauth.license.LicenseRepository;
import com.twendeno.msauth.privilege.Privilege;
import com.twendeno.msauth.privilege.PrivilegeRepository;
import com.twendeno.msauth.role.Role;
import com.twendeno.msauth.role.RoleRepository;
import com.twendeno.msauth.role.RoleType;
import com.twendeno.msauth.ticket.TicketRepository;
import com.twendeno.msauth.ticket.entity.Ticket;
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
    private TicketRepository ticketRepository;

    @Autowired
    private LicenseRepository licenseRepository;

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

        // Create initial roles on database
        createRoleIfNotFound(RoleType.SUPER_ADMIN);
        createRoleIfNotFound(RoleType.ADMIN);
        createRoleIfNotFound(RoleType.USER);
        createRoleIfNotFound(RoleType.TENANT);
        createRoleIfNotFound(RoleType.DEV);

        // Create initial privileges on database
        Role superAdminRole = roleRepository.findByName(RoleType.SUPER_ADMIN).get();
        Role adminRole = roleRepository.findByName(RoleType.ADMIN).get();
        Role userRole = roleRepository.findByName(RoleType.USER).get();
        Role devRole = roleRepository.findByName(RoleType.DEV).get();
        Role tenantRole = roleRepository.findByName(RoleType.TENANT).get();

        List<Privilege> superAdminPrivileges = RoleType.SUPER_ADMIN.getPrivileges().stream()
                .map(privilegeType -> createPrivilegeIfNotFound(privilegeType.name(), superAdminRole)).toList();

        List<Privilege> adminPrivileges = RoleType.ADMIN.getPrivileges().stream()
                .map(privilegeType -> createPrivilegeIfNotFound(privilegeType.name(), adminRole)).toList();

        List<Privilege> devPrivilege = RoleType.DEV.getPrivileges().stream()
                .map(privilegeType -> createPrivilegeIfNotFound(privilegeType.name(), devRole)).toList();

        List<Privilege> userPrivilege = RoleType.USER.getPrivileges().stream()
                .map(privilegeType -> createPrivilegeIfNotFound(privilegeType.name(), userRole)).toList();

        List<Privilege> tenantPrivilege = RoleType.TENANT.getPrivileges().stream()
                .map(privilegeType -> createPrivilegeIfNotFound(privilegeType.name(), tenantRole)).toList();

        // Create initial super admin user on database
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

        // Create initial licenses on database
        List<License> licenses = List.of(
                License.builder().name("BASIC").price(0.0f).duration(1).build(),
                License.builder().name("PRO").price(10.0f).duration(3).build(),
                License.builder().name("ENTERPRISE").price(20.0f).duration(12).build()
        );

        licenseRepository.saveAll(licenses);


        // Create initial tickets on database
        List<Ticket> tickets = List.of(
                Ticket.builder().name("EXPRESS").price(0.0f).duration(1).build(),
                Ticket.builder().name("PERFUSION").price(20.0f).duration(24).build()
        );

        ticketRepository.saveAll(tickets);


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
