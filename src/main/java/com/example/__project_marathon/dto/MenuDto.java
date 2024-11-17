package com.example.__project_marathon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MenuDto {
    private Long id;
    private LocalDateTime date;
    private Integer count;
    private Integer price;
    private String area;
    private String Category;

}
