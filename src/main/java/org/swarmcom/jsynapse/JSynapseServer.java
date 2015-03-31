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

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import static org.springframework.boot.SpringApplication.run;
import static org.apache.commons.lang3.StringUtils.substringAfter;

@SpringBootApplication
@Configuration
@PropertySource(value = "classpath:jsynapse.properties", ignoreResourceNotFound = true)
public class JSynapseServer {
    public static String DOMAIN = "swarmcom.org";

    @Bean(name = "customProps")
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.startsWith("--domain")) {
                    DOMAIN = substringAfter(arg, "=");
                }
            }
        }
        run(JSynapseServer.class, args);
    }
}
