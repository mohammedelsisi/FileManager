package com.digivisions.stc.service;

import com.digivisions.stc.entity.Item;

public interface UploadingService {

    Item addSpace(Item item);

    Item addFolderInSpace(Item folder, Long spaceId);

    Item addFileInFolder(Item folder, Long spaceId);


}
