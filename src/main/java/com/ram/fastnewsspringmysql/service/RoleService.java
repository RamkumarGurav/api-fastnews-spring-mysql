package com.ram.fastnewsspringmysql.service;

import com.ram.fastnewsspringmysql.collection.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role createRole(Role role);

    List<Role> getAllRoles();

    Optional<Role> getActiveSingleRole(Long id, boolean active);

    Optional<Role> getSingleRole(Long id);

    void permanentlyDeleteRole(Long id);

    Role deactivateRole(Role role);

    Role activateRole(Role role);

    Role partiallyDeleteRole(Role role);

    Page<Role> search(String keyword, Long roleId, boolean active, boolean deleted, Pageable pageable);
}
