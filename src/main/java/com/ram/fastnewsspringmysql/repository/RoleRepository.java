package com.ram.fastnewsspringmysql.repository;

import com.ram.fastnewsspringmysql.collection.Comment;
import com.ram.fastnewsspringmysql.collection.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role>  findByRoleName(String roleName);
    Optional<Role>  findByRoleNameAndActive(String roleName,boolean active);

    Optional<Role> findByRoleIdAndActive(Long roleId, boolean active);


    @Query("SELECT r FROM Role r WHERE " +
            "(:roleId IS NULL OR r.roleId=:roleId) " +
            "AND (r.active=:active) " +
            "AND (r.deleted=:deleted) " +
            "AND (:keyword IS NULL OR UPPER(r.roleName) LIKE CONCAT('%', UPPER(:keyword), '%') )")
    Page<Role> searchByKeywordAndRoleIdAndActiveAndDeleted(String keyword, Long roleId, boolean active,boolean deleted, Pageable pageable);



}
