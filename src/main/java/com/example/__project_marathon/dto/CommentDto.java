package com.example.__project_marathon.dto;

import com.example.__project_marathon.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentDto {

    private String id;
    private String writerName;
    private String content;

    public static CommentDto createCommentDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getWriterName(),
                comment.getContent()
        );
    }

}
