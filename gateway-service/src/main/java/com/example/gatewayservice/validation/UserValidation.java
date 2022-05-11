package com.example.gatewayservice.validation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="uservalidation", url="${api.url.user-service}", fallback = UserValidationFallback.class)
public interface UserValidation {
    @RequestMapping(method= RequestMethod.GET, path="/users/{userId}")
    public ResponseEntity<UserValidationResult> checkUser(@PathVariable("userId") String userId);
}
