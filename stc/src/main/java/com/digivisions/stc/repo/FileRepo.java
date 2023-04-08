package com.digivisions.stc.repo;

import com.digivisions.stc.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<File, Long> {
}
