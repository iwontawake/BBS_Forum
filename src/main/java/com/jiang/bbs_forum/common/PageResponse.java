package com.jiang.bbs_forum.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private long total;
    private List<T> list;
    private int page;
    private int size;
    private int pages;
}
