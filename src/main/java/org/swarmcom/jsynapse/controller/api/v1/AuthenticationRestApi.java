package org.swarmcom.jsynapse.controller.api.v1;

import org.springframework.web.bind.annotation.*;
import org.swarmcom.jsynapse.controller.JsynapseApi;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationResult;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationSubmission;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationFlows;
import org.swarmcom.jsynapse.service.authentication.AuthenticationService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.swarmcom.jsynapse.controller.JsynapseApi.V1_API;

@RestController
@RequestMapping(value = V1_API)
public class AuthenticationRestApi extends JsynapseApi {

    private final AuthenticationService authenticationService;

    @Inject
    public AuthenticationRestApi(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/login", method = GET)
    public @ResponseBody
    AuthenticationFlows getLoginSchemas() {
        return authenticationService.getSupportedFlows();
    }

    @RequestMapping(value = "/login", method = POST)
    public @ResponseBody
    AuthenticationResult login(@RequestBody final AuthenticationSubmission login, HttpServletRequest request) {
        login.setRemoteAddr(request.getRemoteAddr());
        return authenticationService.login(login);
    }

    @RequestMapping(value = "/register", method = GET)
    public @ResponseBody
    AuthenticationFlows getRegistrationSchemas() {
        return authenticationService.getSupportedFlows();
    }

    @RequestMapping(value = "/register", method = POST)
    public @ResponseBody
    AuthenticationResult register(@RequestBody final AuthenticationSubmission registration, HttpServletRequest request) {
        registration.setRemoteAddr(request.getRemoteAddr());
        return authenticationService.register(registration);
    }
}