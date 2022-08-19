package org.lr.dto;

import lombok.Data;

@Data
public class SearchNoticeDto {
    private String receiverId;
    private Long page;
    private Long pageSize;
}
