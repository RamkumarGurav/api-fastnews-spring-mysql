package com.ram.fastnewsspringmysql.controller;

import com.ram.fastnewsspringmysql.collection.Post;
import com.ram.fastnewsspringmysql.collection.Role;
import com.ram.fastnewsspringmysql.dto.MsgRBody;
import com.ram.fastnewsspringmysql.dto.RBody;
import com.ram.fastnewsspringmysql.error.CustomException;
import com.ram.fastnewsspringmysql.repository.RoleRepository;
import com.ram.fastnewsspringmysql.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @PostMapping("/admin-protected/roles")
    public ResponseEntity<Object> createRole(@Valid @RequestBody Role role){

        Optional<Role> foundRoleOp=roleRepository.findByRoleName(role.getRoleName().toUpperCase().trim());
        if(foundRoleOp.isPresent()){
            throw new CustomException("This Role is Already Exists",HttpStatus.BAD_REQUEST);
        }

        role.setRoleName(role.getRoleName().toUpperCase().trim());

        Role newRole = roleService.createRole(role);


        RBody rbody = new RBody("success", newRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(rbody);
    }


    @GetMapping("/admin-protected/roles/{id}")
    public ResponseEntity<Object> getSingleRole(@PathVariable("id") Long id){

       Optional<Role> foundRoleOp = roleService.getSingleRole(id);

       if (!foundRoleOp.isPresent()){
           throw new CustomException("Role not found",HttpStatus.NOT_FOUND);
       }


        RBody rbody = new RBody("success", foundRoleOp.get());
        return ResponseEntity.status(HttpStatus.OK).body(rbody);
    }


    @GetMapping("/admin-protected/roles")
    public ResponseEntity<Object> getAllRoles(@RequestParam(required = false) String keyword,
                                              @RequestParam(defaultValue = "true") boolean active,
                                              @RequestParam(defaultValue = "false") boolean deleted,
                                              @RequestParam(required = false) Long roleId,
                                              @RequestParam(defaultValue = "-createdAt") List<String> sort,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size) {


//--------------for single sort field-----------------
//            Sort sortByProperty=sort.startsWith("-")?Sort.by(Sort.Direction.DESC,sort.substring(1)):Sort.by(Sort.Direction.ASC,sort);

//--------------for MULTIPLE sort fieldS-----------------
        // MAKING SORT LIST
        List<Sort.Order> ordersA = new ArrayList<>();
        //FILTERING BASED ON "-" SYMBOL
        for (String field : sort) {
            if (field.startsWith("-")) {
                ordersA.add(new Sort.Order(Sort.Direction.DESC, field.substring(1)));
            } else {
                ordersA.add(new Sort.Order(Sort.Direction.ASC, field));
            }
        }

//        //----------------using stream and map-----------------------
//            List<Sort.Order> ordersB= sort.stream()
//                    .map(field -> {
//                        Sort.Direction direction = field.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
//                        String fieldName = field.startsWith("-") ? field.substring(1) : field;
//                        return new Sort.Order(direction, fieldName);
//                    })
//                    .collect(Collectors.toList());

        //CREATING SORT OBJECT WHICH TAKES STREAMABLE SORT.ORDER OBJECTS
        Sort sortProps = Sort.by(ordersA);

        //PAGEABLE
        Pageable pageable = PageRequest.of(page, size, sortProps);

        //GETTING POSTS PAGE
        Page<Role> rolesPage = roleService.search(keyword,roleId,active,deleted,pageable);

        RBody rbody = new RBody("success", rolesPage);
        return ResponseEntity.status(HttpStatus.OK).body(rbody);
    }

    @DeleteMapping("/admin-protected/roles/{id}")
    public ResponseEntity<Object> permanentlyDeleteRole(@PathVariable("id") Long id){

        Optional<Role>  foundRoleOp=roleService.getSingleRole(id);
        if (!foundRoleOp.isPresent()){
            throw new CustomException("Role not found",HttpStatus.NOT_FOUND);
        }


         roleService.permanentlyDeleteRole(id);


        MsgRBody rbody = new MsgRBody("success", "successfully deleted role with roleName: "+foundRoleOp.get().getRoleName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }

    @PatchMapping("/admin-protected/roles/deactivate/{id}/deactivate")
    public ResponseEntity<Object> deactivateRole(@PathVariable("id") Long id){

        Optional<Role>  foundRoleOp=roleService.getSingleRole(id);
        if (!foundRoleOp.isPresent()){
            throw new CustomException("Role not found",HttpStatus.NOT_FOUND);
        }



       Role updatedRole= roleService.deactivateRole(foundRoleOp.get());

        MsgRBody rbody = new MsgRBody("success", "successfully deactivated role with roleName: "+foundRoleOp.get().getRoleName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }

    @PatchMapping("/admin-protected/roles/{id}/activate")
    public ResponseEntity<Object> activateRole(@PathVariable("id") Long id){

        Optional<Role>  foundRoleOp=roleService.getSingleRole(id);
        if (!foundRoleOp.isPresent()){
            throw new CustomException("Role not found",HttpStatus.NOT_FOUND);
        }



        Role updatedRole= roleService.activateRole(foundRoleOp.get());

        MsgRBody rbody = new MsgRBody("success", "successfully activated role with roleName: "+foundRoleOp.get().getRoleName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }


    @PatchMapping("/admin-protected/roles/{id}/partial-delete")
    public ResponseEntity<Object> partiallyDelete(@PathVariable("id") Long id){

        Optional<Role>  foundRoleOp=roleService.getSingleRole(id);
        if (!foundRoleOp.isPresent()){
            throw new CustomException("Role not found",HttpStatus.NOT_FOUND);
        }



        Role updatedRole= roleService.partiallyDeleteRole(foundRoleOp.get());

        MsgRBody rbody = new MsgRBody("success", "successfully partially deleted role with roleName: "+foundRoleOp.get().getRoleName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rbody);
    }



}
