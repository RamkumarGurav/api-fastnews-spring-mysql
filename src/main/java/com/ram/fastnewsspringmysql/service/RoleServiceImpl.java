package com.ram.fastnewsspringmysql.service;

import com.ram.fastnewsspringmysql.collection.Role;
import com.ram.fastnewsspringmysql.error.CustomException;
import com.ram.fastnewsspringmysql.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{


    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {

        Optional<Role> roleOptional = roleRepository.findByRoleName(role.getRoleName());

        if(roleOptional.isPresent()){
            throw new CustomException("This Role has already Created", HttpStatus.BAD_REQUEST);
        }

        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getActiveSingleRole(Long id, boolean active) {
        return roleRepository.findByRoleIdAndActive(id,active);
    }

    @Override
    public Optional<Role> getSingleRole(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void permanentlyDeleteRole(Long id) {
         roleRepository.deleteById(id);
    }

    @Override
    public Role deactivateRole(Role role) {
        role.setUpdated(true);
        role.setActive(false);
        role.setDeleted(false);
        return roleRepository.save(role);
    }

    @Override
    public Role activateRole(Role role) {
        role.setUpdated(true);
        role.setActive(true);
        role.setDeleted(false);
        return roleRepository.save(role);
    }

    @Override
    public Role partiallyDeleteRole(Role role) {
        role.setUpdated(true);
        role.setActive(false);
        role.setDeleted(true);
        return roleRepository.save(role);
    }

    @Override
    public Page<Role> search(String keyword, Long roleId, boolean active, boolean deleted, Pageable pageable) {
        return roleRepository.searchByKeywordAndRoleIdAndActiveAndDeleted(keyword,roleId,active,deleted,pageable);
    }
}
