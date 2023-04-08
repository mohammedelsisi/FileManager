package com.digivisions.stc.repo;

import com.digivisions.stc.entity.Item;
import com.digivisions.stc.entity.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepo extends JpaRepository<Item, Long> {

    List<Item> findByType(Type type);

    Optional<Item> findByIdAndType(Long id, Type type);
}
