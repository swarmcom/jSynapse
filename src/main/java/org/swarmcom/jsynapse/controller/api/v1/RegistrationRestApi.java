package org.swarmcom.jsynapse.controller.api.v1;

import org.springframework.web.bind.annotation.*;
import org.swarmcom.jsynapse.controller.JsynapseApi;
import org.swarmcom.jsynapse.domain.Registration.RegistrationResult;
import org.swarmcom.jsynapse.domain.Registration.RegistrationSubmission;
import org.swarmcom.jsynapse.domain.Registration.RegistrationFlows;
import org.swarmcom.jsynapse.service.registration.RegistrationService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.swarmcom.jsynapse.controller.JsynapseApi.V1_API;

@RestController
@RequestMapping(value = V1_API)
public class RegistrationRestApi extends JsynapseApi {

    private final RegistrationService registrationService;

    @Inject
    public RegistrationRestApi(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/register", method = GET)
    public @ResponseBody RegistrationFlows getSchemas() {
        return registrationService.getSupportedFlows();
    }

    @RequestMapping(value = "/register", method = POST)
    public @ResponseBody RegistrationResult register(@RequestBody final RegistrationSubmission registration, HttpServletRequest request) {
        registration.setRemoteAddr(request.getRemoteAddr());
        return registrationService.register(registration);
    }
}