package com.digivisions.stc.repo;


import com.digivisions.stc.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionsRepo extends JpaRepository<Permission, Long> {

    Optional<Permission>  getByUserEmail(String email);
}
