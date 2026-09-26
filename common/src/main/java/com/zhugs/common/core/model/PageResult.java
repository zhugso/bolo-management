package com.zhugs.common.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageResult<T> {

    private List<T> records;

    private Integer total;

    private Integer pageNum;

    private Integer pageSize;

}