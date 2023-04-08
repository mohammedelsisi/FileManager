package com.digivisions.stc.service.impl;

import com.digivisions.stc.entity.Item;
import com.digivisions.stc.entity.enums.Type;
import com.digivisions.stc.repo.ItemRepo;
import com.digivisions.stc.service.ViewingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewingServiceImpl implements ViewingService {
    private final ItemRepo itemRepo;

    @Override
    public List<Item> viewAllSpaces() {
        return itemRepo.findByType(Type.Space);
    }

    @Override
    public List<Item> viewAllFolders() {
        return itemRepo.findByType(Type.Folder);
    }


    @Override
    public Optional<Item> findByIdAndType(Long id, Type type) {
        return itemRepo.findByIdAndType(id, type);
    }
}
