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
package org.swarmcom.jsynapse;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;

import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JSynapseServer.class)
@WebAppConfiguration
@ActiveProfiles(profiles = "test")
public class TestBase {
    @Autowired
    private WebApplicationContext wac;

    public MockMvc mockMvc;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @After
    public void after() throws Exception {
        mongoTemplate.getDb().dropDatabase();
    }

    public void postAndCompareResult(String path, Resource req, Resource res) throws Exception {
        try (InputStream request = req.getInputStream()) {
            try (InputStream response = res.getInputStream()) {
                mockMvc.perform(post(path)
                        .contentType(APPLICATION_JSON).content(IOUtils.toString(request)))
                        .andExpect(status().isOk())
                        .andExpect(content()
                                .string(deleteWhitespace(IOUtils.toString(response))))
                        .andReturn();
            }
        }
    }

    public void postAndCheckStatus(String path, Resource req, HttpStatus status) throws Exception {
        try (InputStream request = req.getInputStream()) {
                mockMvc.perform(post(path)
                        .contentType(APPLICATION_JSON).content(IOUtils.toString(request)))
                        .andExpect(status().is(status.value()))
                        .andReturn();
        }
    }

    public void putAndCheckStatus(String path, Resource req, HttpStatus status) throws Exception {
        try (InputStream request = req.getInputStream()) {
            mockMvc.perform(
                    put(path)
                            .contentType(APPLICATION_JSON).content(IOUtils.toString(request)))
                    .andExpect(status().is(status.value()))
                    .andReturn();

        }
    }

    public void getAndCompareResult(String path, Resource req) throws Exception {
        try (InputStream response = req.getInputStream()) {
            mockMvc.perform(
                    get(path))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .string(deleteWhitespace(IOUtils.toString(response))))
                    .andReturn();

        }
    }

    public void deleteAndCheckStatus(String path, Resource req, HttpStatus status) throws  Exception {
        try (InputStream request = req.getInputStream()) {
            mockMvc.perform(delete(path).contentType(APPLICATION_JSON).content(IOUtils.toString(request)))
                    .andExpect(status().is(status.value()))
                    .andReturn();
        }
    }
}
