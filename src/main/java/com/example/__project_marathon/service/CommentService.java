package com.example.__project_marathon.service;

import com.example.__project_marathon.dto.CommentDto;
import com.example.__project_marathon.repository.CommentRepository;
import com.example.__project_marathon.model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public CommentDto createComment(CommentDto commentDto){
        Comment comment = Comment.createComment(commentDto);
        Comment createdComment = commentRepository.save(comment);

        return CommentDto.createCommentDto(createdComment);
    }

    @Transactional
    public List<CommentDto> comments(){
        return commentRepository.findAll()
                .stream()
                .map(CommentDto::createCommentDto)
                .collect(Collectors.toList());
    }

}
