package inhaRestaurant.Comment.service;

import inhaRestaurant.Comment.domain.Comment;
import inhaRestaurant.Comment.domain.CommentDto;
import inhaRestaurant.Comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public CommentDto createComment(CommentDto commentDto){
        Comment comment = Comment.createComment(commentDto);
        Comment createdComment = commentRepository.save(comment);

        return CommentDto.createCommentDto(createdComment);
    }
}
