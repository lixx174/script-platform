package com.j.api;

import com.j.application.model.PageReply;
import com.j.application.model.Result;
import com.j.application.model.authority.AuthorityCreateCommand;
import com.j.application.model.authority.AuthorityDto;
import com.j.application.model.authority.AuthorityPageQuery;
import com.j.application.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jinx
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/authority")
public class AuthorityController {

    private final AuthorityService service;

    @GetMapping("/page")
    public Result<PageReply<AuthorityDto>> page(AuthorityPageQuery query) {
        return Result.succeed(service.page(query));
    }

    @PostMapping("/create")
    public Result<Void> create(@RequestBody AuthorityCreateCommand command) {
        return Result.succeed(() -> service.create(command));
    }
}
