package com.example.__project_marathon.model;

import com.example.__project_marathon.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "COMMENT")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String writer;

    @Column
    private String content;

    public Comment(String writer, String content){
        this.writer = writer;
        this.content = content;
    }

    public static Comment createComment(CommentDto commentDto){
        if(commentDto.getId() != null){
            throw new IllegalArgumentException("이미 존재하는 댓글입니다.");
        }

        return new Comment(commentDto.getWriter(), commentDto.getContent());
    }

}
