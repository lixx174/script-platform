package com.j.application.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 分页请求基础模型
 *
 * @author jinx
 */
@Getter
@Setter
public class PageQuery {

    /**
     * 当前页码(默认1)
     */
    private Number current = 1;
    /**
     * 分页大小(默认10)
     */
    private Number size = 10;


    public Pageable getPage() {
        return getPage("createAt");
    }

    public Pageable getPage(String... sortProperties) {
        return getPage(Sort.Direction.DESC, sortProperties);
    }

    /**
     * jpa 获取分页模型
     *
     * @param direction      排序方式
     * @param sortProperties 排序字段
     * @return 分页参数
     */
    public Pageable getPage(Sort.Direction direction, String... sortProperties) {
        int pageNumber = current == null || current.intValue() < 0 ? 0 : current.intValue() - 1;
        int pageSize = size == null ? 10 : size.intValue();

        return PageRequest.of(pageNumber, pageSize, direction, sortProperties);
    }

    /**
     * jpa 获取查询条件
     *
     * @return 查询条件
     */
    public Example<?> getExample() {
        throw new UnsupportedOperationException("The method needs to be implemented if there are query conditions.");
    }
}
