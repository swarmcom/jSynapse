package org.swarmcom.jsynapse.controller.api.v1;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.swarmcom.jsynapse.controller.JsynapseApi;
import org.swarmcom.jsynapse.domain.Registration;
import org.swarmcom.jsynapse.service.registration.RegistrationService;

import javax.inject.Inject;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.swarmcom.jsynapse.controller.JsynapseApi.V1_API;

@RestController
@RequestMapping(value = V1_API)
public class LoginRestApi extends JsynapseApi {
    private final RegistrationService registrationService;

    @Inject
    public LoginRestApi(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/login", method = GET)
    public @ResponseBody
    Registration.RegistrationFlows getSchemas() {
        return registrationService.getSupportedFlows();
    }

    @RequestMapping(value = "/login", method = POST)
    public @ResponseBody
    Registration.RegistrationResult register(@RequestBody final Registration.RegistrationSubmission login) {
        return registrationService.login(login);
    }
}
