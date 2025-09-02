package com.j.application.model.role;

import com.j.application.model.PageQuery;
import com.j.domain.entity.user.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

/**
 * @author Jinx
 */
@Getter
@Setter
public class RolePageQuery extends PageQuery {

    /**
     * 模糊查询字段
     */
    private String q;

    @Override
    public Example<Role> getExample() {
        Role probe = new Role();
        probe.setName(q);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return Example.of(probe, matcher);
    }
}
