package org.swarmcom.jsynapse.service.content;

import org.springframework.core.io.Resource;

public class ContentResource {
    String contentType;
    long length;
    Resource resource;

    public ContentResource(String contentType, long length, Resource resource) {
        this.contentType = contentType;
        this.length = length;
        this.resource = resource;
    }

    public String getContentType() {
        return contentType;
    }

    public long getLength() {
        return length;
    }

    public Resource getResource() {
        return resource;
    }
}
