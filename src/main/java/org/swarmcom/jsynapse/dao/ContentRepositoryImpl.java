package org.swarmcom.jsynapse.dao;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;
import org.swarmcom.jsynapse.service.content.ContentResource;

import java.io.InputStream;

@Repository
public class ContentRepositoryImpl implements ContentRepository {
    @Autowired
    GridFsTemplate gridFsTemplate;

    @Override
    public String upload(InputStream content, String fileName, String contentType) {
        GridFSFile file = gridFsTemplate.store(content, fileName, contentType);
        return file.getId().toString();
    }

    @Override
    public ContentResource download(String mediaId) {
        GridFSDBFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(mediaId)));
        if (null == file) {
            return null;
        }
        GridFsResource gridFsResource = new GridFsResource(file);
        return new ContentResource(gridFsResource.getContentType(), file.getLength(), gridFsResource);
    }
}
