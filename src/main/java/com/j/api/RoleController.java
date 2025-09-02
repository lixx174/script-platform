package com.j.api;

import com.j.application.model.PageReply;
import com.j.application.model.Result;
import com.j.application.model.role.RoleCreateCommand;
import com.j.application.model.role.RoleDto;
import com.j.application.model.role.RolePageQuery;
import com.j.application.service.RoleService;
import jakarta.validation.Valid;
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
@RequestMapping("/role")
public class RoleController {

    private final RoleService service;

    @GetMapping("/page")
    public Result<PageReply<RoleDto>> page(RolePageQuery query) {
        return Result.succeed(service.page(query));
    }

    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody RoleCreateCommand command) {
        return Result.succeed(() -> service.create(command));
    }
}
