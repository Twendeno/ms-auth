package com.twendeno.msauth.role;

import com.twendeno.msauth.role.dto.RoleCreateDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public void create(RoleCreateDto roleCreateDto) {
        Role role = new Role();
        RoleType roleType = RoleType.valueOf(roleCreateDto.name());
        role.setName(roleType);
        this.roleRepository.save(role);
    }

    public List<Role> getRoles() {
        return this.roleRepository.findAll();
    }

    public Role getRoleById(String uuid) {
        return this.roleRepository.findById(UUID.fromString(uuid)).orElseThrow();
    }

    public void deleteRole(String uuid) {
        this.roleRepository.deleteById(UUID.fromString(uuid));
    }

    public void updateRole(String uuid, RoleCreateDto roleCreateDto) {
        Role role = this.roleRepository.findById(UUID.fromString(uuid)).orElseThrow();
        RoleType roleType = RoleType.valueOf(roleCreateDto.name());
        role.setName(roleType);
        this.roleRepository.save(role);
    }
}
