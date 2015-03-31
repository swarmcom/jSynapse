package org.swarmcom.jsynapse.service.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.swarmcom.jsynapse.JSynapseServer.DOMAIN;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.RandomStringUtils.random;

@Component
public class UserUtils {
    public String generateUserId(String userIdOrLocalPart) {
        String userIdRegular = String.format("^@\\S*:%s$", StringUtils.replace(DOMAIN, ".", "\\."));
        Pattern p = Pattern.compile(userIdRegular);
        Matcher m = p.matcher(userIdOrLocalPart);

        return m.matches() ? userIdOrLocalPart : join(new String[]{"@", userIdOrLocalPart,":", DOMAIN});

    }

    public String generateAccessToken() {
        return random(16, true, false);
    }
}
