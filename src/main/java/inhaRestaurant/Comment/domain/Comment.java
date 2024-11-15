package inhaRestaurant.Comment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "inhaRestaurantComment")
@NoArgsConstructor
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String writerName;
    @Column
    private String content;

    public Comment(String writerName, String content){
        this.writerName = writerName;
        this.content = content;
    }

    public Comment createComment(CommentDto commentDto){
        if(commentDto.getId() != null){
            throw new IllegalArgumentException("dto 존재하지 않습니다.");
        }

        return new Comment(
                commentDto.getWriterName(),
                commentDto.getContent()
        );
    }

}
