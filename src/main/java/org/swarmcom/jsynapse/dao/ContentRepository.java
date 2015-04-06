package org.swarmcom.jsynapse.dao;

import org.swarmcom.jsynapse.service.content.ContentResource;

import java.io.InputStream;

public interface ContentRepository {
    String upload(InputStream content, String fileName, String contentType);

    ContentResource download(String mediaId);
}
