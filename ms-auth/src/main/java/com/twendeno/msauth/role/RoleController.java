package com.twendeno.msauth.role;

import com.twendeno.msauth.role.dto.RoleCreateDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
@RequestMapping("roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public void create(@RequestBody RoleCreateDto roleCreateDto) {
        this.roleService.create(roleCreateDto);
    }

    @GetMapping
    public List<Role> getRoles() {
        return this.roleService.getRoles();
    }

    @GetMapping("{uuid}")
    public Role getRoleById(@PathVariable String uuid) {
        return this.roleService.getRoleById(uuid);
    }

    @DeleteMapping("{uuid}")
    public void deleteRole(@PathVariable String uuid) {
        this.roleService.deleteRole(uuid);
    }

    @PutMapping("{uuid}")
    public void updateRole(@PathVariable String uuid, @RequestBody RoleCreateDto roleCreateDto) {
        this.roleService.updateRole(uuid, roleCreateDto);
    }

}
