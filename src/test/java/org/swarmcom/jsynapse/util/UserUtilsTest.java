package org.swarmcom.jsynapse.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.swarmcom.jsynapse.TestBase;
import org.swarmcom.jsynapse.service.user.UserUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.swarmcom.jsynapse.JSynapseServer.DOMAIN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class UserUtilsTest extends  TestBase {

    @Autowired
    private UserUtils userUtils;

    @Test
    public void userIdTest() {
        String userIdRegular = String.format("^@\\S*:%s$", StringUtils.replace(DOMAIN, ".", "\\."));
        Pattern p = Pattern.compile(userIdRegular);
        Matcher m = p.matcher("@john.doe:swarmcom.org");
        assertTrue(m.matches());
        m = p.matcher("@john doe:swarmcom.org");
        assertFalse(m.matches());
        m = p.matcher("@john.doe:swarmcom.orgx");
        assertFalse(m.matches());
        m = p.matcher("john.doe:swarmcom.org");
        assertFalse(m.matches());
        m = p.matcher("z@john.doe:swarmcom.orgx");
        assertFalse(m.matches());
        m = p.matcher("@john.doe:swarmcsom.org");
        assertFalse(m.matches());
        m = p.matcher("@john.doe.swarmcom.org");
        assertFalse(m.matches());
    }

    @Test
    public void generateUserIdTest() {
        //generate user id given localpart
        String userId = userUtils.generateUserId("john.doe");
        assertEquals("@john.doe:swarmcom.org", userId);
        //generate user id given user id - no change
        userId = userUtils.generateUserId("@john.doe:swarmcom.org");
        assertEquals("@john.doe:swarmcom.org", userId);
    }
}
