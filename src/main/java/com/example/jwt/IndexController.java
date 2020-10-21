package com.example.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alexander
 * @date 2020-10-20
 **/
@RestController
@RequestMapping("/")
@Slf4j
public class IndexController {

    @GetMapping("/member")
    public ResponseEntity checkUser(@AuthenticationPrincipal User user) {
        log.debug(user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body("hello");
    }

    @GetMapping("/error")
    public ResponseEntity unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
