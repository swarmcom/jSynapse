package org.swarmcom.jsynapse;

import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.annotation.Validated;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JSynapseServer.class)
@WebAppConfiguration
@IntegrationTest("server.port:4444")
public class TestBase {
}
