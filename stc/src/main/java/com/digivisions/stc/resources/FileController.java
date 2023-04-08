package com.digivisions.stc.resources;

import com.digivisions.stc.entity.File;
import com.digivisions.stc.entity.Item;
import com.digivisions.stc.entity.enums.PermissionLevel;
import com.digivisions.stc.entity.enums.Type;
import com.digivisions.stc.exception.ErrorMessages;
import com.digivisions.stc.exception.GenericException;
import com.digivisions.stc.service.AuthService;
import com.digivisions.stc.service.ViewingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final AuthService authService;
    private final ViewingService viewingService;

    @GetMapping("/{fileId}")
    ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId, @RequestHeader("email") String email) {

        authService.isUserAuthorized(fileId, email, PermissionLevel.VIEW);
        Optional<Item> item = viewingService.findByIdAndType(fileId, Type.File);
        if(item.isEmpty()) throw new GenericException(HttpStatus.NOT_FOUND, ErrorMessages.ITEM_NOT_FOUND);
        File file = item.get().getFile();
        if(Objects.isNull(file)) throw new GenericException(HttpStatus.NOT_FOUND, ErrorMessages.FILE_NOT_FOUND);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        headers.setContentLength(file.getFileLength());
        headers.setContentDispositionFormData("attachment", item.get().getName());
        return new ResponseEntity<>(file.getFile(), headers, HttpStatus.OK);
    }

}
