package com.example.__project_marathon.model;

import com.example.__project_marathon.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "COMMENTS")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String writer;

    @Column
    private String content;

    public Comments(String writer, String content){
        this.writer = writer;
        this.content = content;
    }

    public static Comments createComment(CommentDto commentDto){
        if(commentDto.getId() != null){
            throw new IllegalArgumentException("이미 존재하는 댓글입니다.");
        }

        return new Comments(commentDto.getWriter(), commentDto.getContent());
    }

}
