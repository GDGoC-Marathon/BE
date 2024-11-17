package com.example.__project_marathon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TodayDto {

    private Long id;
    private Integer count;
    private String img_url;

}
