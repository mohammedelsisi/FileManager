package com.digivisions.stc.resources;

import com.digivisions.stc.entity.Item;
import com.digivisions.stc.entity.enums.PermissionLevel;
import com.digivisions.stc.entity.enums.Type;
import com.digivisions.stc.exception.ErrorMessages;
import com.digivisions.stc.exception.GenericException;
import com.digivisions.stc.service.AuthService;
import com.digivisions.stc.service.UploadingService;
import com.digivisions.stc.service.ViewingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/spaces")
@RequiredArgsConstructor
public class SpaceController {

    private final AuthService authService;
    private final UploadingService uploadingService;
    private final ViewingService viewingService;

    @GetMapping
    List<Item> getSpaces() {
        return viewingService.viewAllSpaces();
    }

    @GetMapping("/{id}")
    ResponseEntity<Item> getSpaceById(@PathVariable Long id) {
        Optional<Item> byId = viewingService.findByIdAndType(id, Type.Space);
        return ResponseEntity.of(byId);
    }

    @GetMapping("/{spaceId}/folders")
    ResponseEntity<Set<Item>> getFolderUnderSpace(@PathVariable Long spaceId, @RequestHeader("email") String email, @RequestParam(required = false, defaultValue = "3") int pageSize, @RequestParam(required = false, defaultValue = "0") int pageNumber) {
        authService.isUserAuthorized(spaceId, email, PermissionLevel.VIEW);
        Optional<Item> space = viewingService.findByIdAndType(spaceId, Type.Space);
        if (space.isEmpty()) return ResponseEntity.notFound().build();
        Set<Item> result = space.get().getItems().stream().skip((long) pageNumber * pageSize).limit(pageSize).collect(Collectors.toSet());
        return ResponseEntity.ok(result);
    }

    @PostMapping
    ResponseEntity<Item> addSpace(@RequestBody @Valid Item space, @RequestHeader("email") String email) {

        if (!authService.isAdminUser(email))
            throw new GenericException(HttpStatus.UNAUTHORIZED, ErrorMessages.ONLY_ADMIN_CAN_ADD_SPACE);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadingService.addSpace(space));


    }

    @PostMapping("/{spaceId}/folders")
    ResponseEntity<Item> addFolder(@PathVariable Long spaceId, @Valid @RequestBody Item folder, @RequestHeader("email") String email) {
        try {
            authService.isUserAuthorized(spaceId, email, PermissionLevel.EDIT);
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadingService.addFolderInSpace(folder, spaceId));
        } finally {
            authService.freeItem(spaceId);
        }
    }
}
