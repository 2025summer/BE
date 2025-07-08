package com.example.auction_market.domain.member;

import io.lettuce.core.dynamic.annotation.Param;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    Member findByUsername(String username);

    Member findByEmail(String email);

    Member findByUsernameAndPhoneNumber(String username, String phoneNumber);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.password = :newPassword WHERE m.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("newPassword") String newPassword);
}
