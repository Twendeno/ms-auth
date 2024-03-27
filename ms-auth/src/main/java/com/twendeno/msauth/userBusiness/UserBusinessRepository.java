package com.twendeno.msauth.userBusiness;

import com.twendeno.msauth.userBusiness.entity.UserBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface UserBusinessRepository extends JpaRepository<UserBusiness, UUID> {
    @Query("SELECT ub FROM UserBusiness ub WHERE ub.user.uuid = :userUUID")
    Optional<Stream<UserBusiness>> findAllByUser(UUID userUUID);

    @Query("SELECT ub FROM UserBusiness ub WHERE ub.business.uuid = :BusinessUUID")
    Optional<Stream<UserBusiness>> findAllByBusiness(UUID BusinessUUID);
}
