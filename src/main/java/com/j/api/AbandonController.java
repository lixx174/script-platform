package com.j.api;

import com.j.application.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jinx
 */
@RestController
@RequestMapping("/")
public class AbandonController {

    @GetMapping("/ping")
    public Result<String> ping() {
        return Result.succeed("pong");
    }
}
