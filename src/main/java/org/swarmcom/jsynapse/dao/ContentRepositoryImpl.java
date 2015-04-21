/*
 * (C) Copyright 2015 eZuce Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
*/
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
