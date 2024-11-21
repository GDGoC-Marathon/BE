package com.example.__project_marathon.dto;

import com.example.__project_marathon.model.Comments;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentDto {

    private Long id;
    private String writer;
    private String content;

    public static CommentDto createCommentDto(Comments comment){
        return new CommentDto(
                comment.getId(),
                comment.getWriter(),
                comment.getContent()
        );
    }

}
