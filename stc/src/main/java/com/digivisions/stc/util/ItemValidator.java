package com.digivisions.stc.util;

import com.digivisions.stc.entity.Item;
import com.digivisions.stc.entity.enums.Type;
import com.digivisions.stc.exception.ErrorMessages;
import com.digivisions.stc.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ItemValidator {

    public void isFolder(Item item) {
        if (!item.getType().equals(Type.Folder))    throw new GenericException(HttpStatus.BAD_REQUEST, ErrorMessages.ITEM_MUST_BE_FOLDER);
    }
}
