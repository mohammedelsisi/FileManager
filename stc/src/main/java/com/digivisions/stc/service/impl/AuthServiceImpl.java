package com.digivisions.stc.service.impl;

import com.digivisions.stc.entity.Item;
import com.digivisions.stc.entity.Permission;
import com.digivisions.stc.entity.enums.PermissionLevel;
import com.digivisions.stc.exception.ErrorMessages;
import com.digivisions.stc.exception.GenericException;
import com.digivisions.stc.repo.ItemRepo;
import com.digivisions.stc.repo.PermissionsRepo;
import com.digivisions.stc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final List<Long> currentlyEditingItems = new ArrayList<>();
    private final PermissionsRepo permissionsRepo;
    private final ItemRepo itemRepo;

    @Override
    public void isUserAuthorized(Long parentId, String email, PermissionLevel permissionRequired) {
        noEditInProgress();
        if (!isAdminUser(email)) {
            userHasItemInGroup(email, parentId);
            userHasRequiredPermission(email, permissionRequired);
        }
        if (permissionRequired.equals(PermissionLevel.EDIT)) currentlyEditingItems.add(parentId);
    }

    @Override
    public void freeItem(Long id) {
        currentlyEditingItems.remove(id);
    }

    @Override
    public boolean isAdminUser(String email) {
        return email.equals("admin");
    }


    private void noEditInProgress() {
        Optional<Long> itemIsBeingEdited = currentlyEditingItems.stream().findAny();
        if (itemIsBeingEdited.isPresent())
            throw new GenericException(HttpStatus.CONFLICT, ErrorMessages.ITEM_IS_BEING_EDITED);
    }

    private void userHasItemInGroup(String email, Long itemId) {
        Optional<Permission> userPermission = permissionsRepo.getByUserEmail(email);
        if (userPermission.isEmpty())
            throw new GenericException(HttpStatus.UNAUTHORIZED, ErrorMessages.NO_PERMISSION_WERE_FOUND_FOR_USER);

        Optional<Item> item = itemRepo.findById(itemId);
        if (item.isEmpty()) throw new GenericException(HttpStatus.NOT_FOUND, ErrorMessages.PARENT_NOT_FOUND);

        String userGroupName = userPermission.get().getPermissionGroup().getGroupName();
        String itemGroupName = item.get().getPermissionGroup().getGroupName();

        if (!itemGroupName.equals(userGroupName))
            throw new GenericException(HttpStatus.UNAUTHORIZED, ErrorMessages.ITEM_NOT_IN_USER_GROUP);

    }


    private void userHasRequiredPermission(String email, PermissionLevel permissionRequired) {
        boolean notAuthorized = permissionsRepo.getByUserEmail(email).stream().map(Permission::getPermissionLevel).noneMatch(permissionLevel ->
                permissionLevel.equals(PermissionLevel.EDIT) || permissionLevel.equals(permissionRequired)
        );
        if (notAuthorized) {
            throw new GenericException(HttpStatus.UNAUTHORIZED, ErrorMessages.PERMISSIONS_DOESNT_MATCH);
        }
    }


}
