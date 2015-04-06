package org.swarmcom.jsynapse.service.content;

import org.swarmcom.jsynapse.domain.ContentUpload;

import java.io.InputStream;

public interface ContentService {

    ContentUpload upload(InputStream body, String fileName, String contentType);

    ContentResource download(String serverName, String mediaId);

    ContentResource getThumbnail(String serverName, String mediaId, Integer width, Integer height, String method);
}
