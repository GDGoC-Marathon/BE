package com.example.__project_marathon.service;

import com.example.__project_marathon.dto.CommentDto;
import com.example.__project_marathon.repository.CommentRepository;
import com.example.__project_marathon.model.Comments;
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
        Comments comment = Comments.createComment(commentDto);
        Comments createdComment = commentRepository.save(comment);

        return CommentDto.createCommentDto(createdComment);
    }

    @Transactional
    public List<CommentDto> comments(){
        return commentRepository.findAll()
                .stream()
                .map(CommentDto::createCommentDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CommentDto> searchContent(String content){
        return commentRepository.findByCommentContent(content).stream()
                .map(CommentDto::createCommentDto)
                .collect(Collectors.toList());
    }

}
