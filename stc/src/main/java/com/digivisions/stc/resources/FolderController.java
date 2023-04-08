package com.digivisions.stc.resources;

import com.digivisions.stc.entity.Item;
import com.digivisions.stc.entity.enums.PermissionLevel;
import com.digivisions.stc.entity.enums.Type;
import com.digivisions.stc.exception.ErrorMessages;
import com.digivisions.stc.exception.GenericException;
import com.digivisions.stc.service.AuthService;
import com.digivisions.stc.service.UploadingService;
import com.digivisions.stc.service.ViewingService;
import com.digivisions.stc.util.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {


    private final AuthService authService;
    private final UploadingService uploadingService;
    private final FileMapper fileMapper;
    private final ViewingService viewingService;


    @GetMapping("/{folderId}")
    ResponseEntity<Item> getFolder(@PathVariable Long folderId, @RequestHeader("email") String email) {
        authService.isUserAuthorized(folderId, email, PermissionLevel.VIEW);
        Optional<Item> byId = viewingService.findByIdAndType(folderId, Type.Folder);
        return ResponseEntity.of(byId);
    }

    @GetMapping
    List<Item> getFolders(@RequestHeader("email") String email) {
        if (!authService.isAdminUser(email))
            throw new GenericException(HttpStatus.UNAUTHORIZED, ErrorMessages.PERMISSIONS_DOESNT_MATCH);
        return viewingService.viewAllFolders();
    }


    @GetMapping("/{folderId}/files")
    ResponseEntity<Set<Item>> getFilesInFolder(@PathVariable Long folderId, @RequestHeader("email") String email) {
        authService.isUserAuthorized(folderId, email, PermissionLevel.VIEW);
        Optional<Item> space = viewingService.findByIdAndType(folderId, Type.Folder);
        if (space.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(space.get().getItems());
    }

    @PostMapping("/{folderId}/files")
    ResponseEntity<Item> addFile(@PathVariable Long folderId, @RequestParam("file") MultipartFile multipartFile, @RequestHeader("email") String email) {
        try {
            authService.isUserAuthorized(folderId, email, PermissionLevel.EDIT);
            Item item = fileMapper.fromMultiPartToItem(multipartFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadingService.addFileInFolder(item, folderId));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        } finally {
            authService.freeItem(folderId);
        }
    }
}
