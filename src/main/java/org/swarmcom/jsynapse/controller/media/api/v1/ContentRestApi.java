package org.swarmcom.jsynapse.controller.media.api.v1;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.swarmcom.jsynapse.domain.ContentUpload;
import org.swarmcom.jsynapse.service.content.ContentResource;
import org.swarmcom.jsynapse.service.content.ContentService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.swarmcom.jsynapse.controller.JsynapseApi.CONTENT_V1_API;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@RestController
@RequestMapping(value = CONTENT_V1_API)
public class ContentRestApi {
    private final ContentService contentService;

    @Inject
    public ContentRestApi(final ContentService contentService) {
        this.contentService = contentService;
    }

    @RequestMapping(value = "/upload", method = POST)
    public ContentUpload upload(@RequestParam("body") MultipartFile file) throws IOException {
        return contentService.upload(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
    }

    @RequestMapping(value = "/download/{serverName}/{mediaId}", method = GET, produces = APPLICATION_OCTET_STREAM_VALUE)
    public Resource download(@PathVariable String serverName, @PathVariable String mediaId, HttpServletResponse response) {
        return generateResponse(contentService.download(serverName, mediaId), response);
    }

    @RequestMapping(value = "/thumbnail/{serverName}/{mediaId}", method = GET, produces = APPLICATION_OCTET_STREAM_VALUE)
    public Resource getThumbnail(@PathVariable String serverName, @PathVariable String mediaId,
                                 @RequestParam("width") Integer width, @RequestParam("height") Integer height,
                                 @RequestParam("method") String method, HttpServletResponse response) {
        return generateResponse(contentService.getThumbnail(serverName, mediaId, width, height, method), response);
    }

    public Resource generateResponse(ContentResource content, HttpServletResponse response) {
        if (null == content) {
            response.setStatus(SC_NOT_FOUND);
            return null;
        }
        Resource resource = content.getResource();
        response.setHeader( "Content-Disposition", "attachment; filename=" + resource.getFilename());
        response.setContentType(content.getContentType());
        response.setContentLengthLong(content.getLength());
        return resource;
    }
}
