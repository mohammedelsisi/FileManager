package com.digivisions.stc.service;

import com.digivisions.stc.entity.enums.PermissionLevel;

public interface AuthService {

    void isUserAuthorized(Long parentId, String email, PermissionLevel permissionRequired);

    void freeItem(Long id);

    boolean isAdminUser(String email);
}
