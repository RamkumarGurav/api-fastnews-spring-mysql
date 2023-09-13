package com.ram.fastnewsspringmysql.repository;

import com.ram.fastnewsspringmysql.collection.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String email);


    Optional<User> findByEmailAndActive(String email,boolean active);

    Optional<User> findByUserIdAndActive(Long userId,boolean active);

    Optional<User> findByFirstNameAndLastName(String firstName,String LastName);
    Optional<User> findByFirstName(String firstName);

    List<User> findByFirstNameStartsWith(String firstName);


    Optional<User> findByPasswordResetToken(String passwordResetToken);


      Optional<User> findByPasswordResetTokenAndPasswordResetTokenExpiresAfter(String passwordResetToken,Date passwordResetTokenExpires);






    @Query("SELECT u FROM User u WHERE " +
            "(:firstName IS NULL OR UPPER(u.firstName) LIKE CONCAT('%',:firstName,'%')) " +
            "AND (:lastName IS NULL OR UPPER(u.lastName) LIKE CONCAT('%',:lastName,'%')) " +
            "AND (:email IS NULL OR UPPER(u.email) LIKE CONCAT('%',:email,'%')) " +
            "AND (:mobile IS NULL OR UPPER(u.mobile) LIKE CONCAT('%',:mobile,'%')) " +
            "AND (:gender IS NULL OR UPPER(u.gender) LIKE CONCAT('%',:gender,'%')) " +
            "AND (:roleName IS NULL OR EXISTS (SELECT 1 FROM u.role r WHERE UPPER(r.roleName) = UPPER(:roleName)) ) " +
            "AND (:keyword IS NULL OR " +
                    "(" +
                         "UPPER(u.firstName) LIKE CONCAT('%', UPPER(:keyword), '%') " +
                        "OR UPPER(u.lastName) LIKE CONCAT('%', UPPER(:keyword), '%') " +
                           "OR UPPER(u.email) LIKE CONCAT('%', UPPER(:keyword), '%') " +
                    ")" +
                 ")"
            )
    Page<User> searchUsersByKeywordAndOtherProps(String keyword, String firstName,String lastName,
                                                 String email,String mobile,String gender,String roleName, Pageable pageable);



}
