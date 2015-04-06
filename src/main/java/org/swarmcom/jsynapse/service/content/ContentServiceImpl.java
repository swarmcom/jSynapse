package org.swarmcom.jsynapse.service.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.ContentRepository;
import org.swarmcom.jsynapse.domain.ContentUpload;

import javax.inject.Inject;
import java.io.InputStream;

import static java.lang.String.format;
import static org.swarmcom.jsynapse.JSynapseServer.DOMAIN;

@Service
@Validated
public class ContentServiceImpl implements ContentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);
    private final ContentRepository contentRepository;

    @Inject
    public ContentServiceImpl(final ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public ContentUpload upload(InputStream body, String fileName, String contentType) {
        String mediaId = contentRepository.upload(body, fileName, contentType);
        return new ContentUpload(format("mxc://%s/%s", DOMAIN, mediaId));
    }

    @Override
    public ContentResource download(String serverName, String mediaId) {
        return contentRepository.download(mediaId);
    }

    @Override
    public ContentResource getThumbnail(String serverName, String mediaId, Integer width, Integer height, String method) {
        // TODO use thumbnailator to resize images / figure out better ways to do this
        return contentRepository.download(mediaId);
    }
}
