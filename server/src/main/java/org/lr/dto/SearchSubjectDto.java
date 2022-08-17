package org.lr.dto;

import lombok.Data;

@Data
public class SearchSubjectDto {
    private Long page;
    private Long pageSize;
    private String name;
    private Integer collegeId;
}
