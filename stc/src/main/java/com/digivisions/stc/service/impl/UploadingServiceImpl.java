package com.digivisions.stc.service.impl;

import com.digivisions.stc.entity.Item;
import com.digivisions.stc.entity.PermissionGroup;
import com.digivisions.stc.entity.enums.Type;
import com.digivisions.stc.exception.ErrorMessages;
import com.digivisions.stc.exception.GenericException;
import com.digivisions.stc.repo.FileRepo;
import com.digivisions.stc.repo.ItemRepo;
import com.digivisions.stc.repo.PermissionGroupRepo;
import com.digivisions.stc.service.UploadingService;
import com.digivisions.stc.util.ItemValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadingServiceImpl implements UploadingService {

    private final ItemRepo itemRepo;
    private final FileRepo fileRepo;
    private final PermissionGroupRepo permissionGroupRepo;
    private final ItemValidator itemValidator;

    @Override
    public Item addSpace(Item item) {
        item.setPermissionGroup(getPermissionGroup(item));
        return itemRepo.save(item);
    }


    @Override
    public Item addFolderInSpace(Item folder, Long spaceId) {
        itemValidator.isFolder(folder);
        folder.setPermissionGroup(getPermissionGroup(folder));
        Item item = insertItem(folder, Type.Space, spaceId);
        return item;

    }

    @Override
    public Item addFileInFolder(Item file, Long folderId) {
        fileRepo.save(file.getFile());
        addParentPermissionGroup(file, folderId);
        Item item = insertItem(file, Type.Folder, folderId);
        return item;
    }

    private void addParentPermissionGroup(Item file, Long folderId) {
        Optional<Item> folder = itemRepo.findById(folderId);
        if (folder.isEmpty()) {
            throw new GenericException(HttpStatus.NOT_FOUND, ErrorMessages.PARENT_NOT_FOUND);
        }
        file.setPermissionGroup(folder.get().getPermissionGroup());
    }


    private Item insertItem(Item file, Type type, Long itemId) {
        Optional<Item> space = itemRepo.findByIdAndType(itemId, type);
        if (space.isEmpty()) throw new GenericException(HttpStatus.NOT_FOUND, ErrorMessages.PARENT_NOT_FOUND);
        Item item = space.get();
        item.getItems().add(file);
        return itemRepo.save(file);
    }

    private PermissionGroup getPermissionGroup(Item item) {
        String groupName = item.getPermissionGroup().getGroupName();
        Optional<PermissionGroup> permissionGroup = permissionGroupRepo.getByGroupName(groupName);
        if (permissionGroup.isEmpty()) {
            throw new GenericException(HttpStatus.NOT_FOUND, ErrorMessages.NO_PERMISSION_GROUP_WITH_NAME);
        }
        return permissionGroup.get();

    }

}




