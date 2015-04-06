package org.swarmcom.jsynapse.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContentUpload {
    @JsonProperty("content_uri")
    String contentUri;

    public ContentUpload(String contentUri) {
        this.contentUri = contentUri;
    }
}
