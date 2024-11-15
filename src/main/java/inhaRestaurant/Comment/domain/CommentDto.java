package inhaRestaurant.Comment.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommentDto {

    private String id;
    private String writerName;
    private String comment;

    public static CommentDto createCommentDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getWriterName(),
                comment.getContent()
        );
    }

}
