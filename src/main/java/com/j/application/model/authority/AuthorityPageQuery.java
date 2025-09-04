package com.j.application.model.authority;

import com.j.application.model.PageQuery;
import com.j.domain.entity.user.Authority;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Jinx
 */
@Getter
@Setter
public class AuthorityPageQuery extends PageQuery {

    @Override
    public Example<Authority> getExample() {
        // 权限分页 默认只查询根节点
        Authority probe = new Authority();
        probe.setParent(null);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return Example.of(probe, matcher);
    }

    @Override
    public Specification<Authority> getSpecification() {
        // FIXME 干掉这个魔法值
        return (root, query, cb) -> cb.isNull(root.get("parent"));
    }
}
