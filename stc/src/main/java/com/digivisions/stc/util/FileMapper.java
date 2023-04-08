package com.digivisions.stc.util;

import com.digivisions.stc.entity.File;
import com.digivisions.stc.entity.Item;
import com.digivisions.stc.entity.enums.Type;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileMapper {

   public Item fromMultiPartToItem(MultipartFile multipartFile) throws IOException {
        Item item = new Item();
        item.setName(multipartFile.getOriginalFilename());
        item.setType(Type.File);
        File file = new File();
        file.setFile(multipartFile.getBytes());
        file.setFileLength(multipartFile.getSize());
        file.setContentType(multipartFile.getContentType());
        item.setFile(file);
        return item;
    }
}
