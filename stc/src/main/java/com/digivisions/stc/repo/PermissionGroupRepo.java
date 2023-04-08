package com.digivisions.stc.repo;

import com.digivisions.stc.entity.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionGroupRepo extends JpaRepository<PermissionGroup, Long> {



    Optional<PermissionGroup> getByGroupName(String groupName);
}
