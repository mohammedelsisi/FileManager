package com.digivisions.stc.service;

import com.digivisions.stc.entity.Item;
import com.digivisions.stc.entity.enums.Type;

import java.util.List;
import java.util.Optional;

public interface ViewingService {
    List<Item> viewAllSpaces();

    List<Item> viewAllFolders();

    Optional<Item> findByIdAndType(Long id, Type type);
}
