package org.lr.dto;

import lombok.Data;

@Data
public class SearchPageSimpleDto {
    private String name;
    private Long page;
    private Long pageSize;
}
