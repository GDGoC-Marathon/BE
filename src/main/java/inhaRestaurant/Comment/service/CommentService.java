package inhaRestaurant.Comment.service;

import inhaRestaurant.Comment.domain.Comment;
import inhaRestaurant.Comment.domain.CommentDto;
import inhaRestaurant.Comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Transactional
    public CommentDto createComment(CommentDto commentDto){
        Comment comment = Comment.createComment(commentDto);
        Comment createdComment = commentRepository.save(comment);

        return CommentDto.createCommentDto(createdComment);
    }

    @Transactional
    public List<CommentDto> comments(){
        return commentRepository.finaAll()
                .stream()
                .map(CommentDto::createCommentDto)
                .collect(Collectors.toList());
    }

}
