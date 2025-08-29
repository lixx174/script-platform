package com.j.api;

import com.j.application.model.Result;
import com.j.application.model.user.UserCreateCommand;
import com.j.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jinx
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody UserCreateCommand command) {
        return Result.succeed(() -> service.create(command));
    }
}
