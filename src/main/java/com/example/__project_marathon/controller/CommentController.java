package com.example.__project_marathon.controller;

import com.example.__project_marathon.dto.CommentDto;
import com.example.__project_marathon.model.Menu;
import com.example.__project_marathon.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/api/restaurant/comment")
    public ResponseEntity<List<CommentDto>> comments(){
        List<CommentDto> commentDtos = commentService.comments();

        return ResponseEntity.status(HttpStatus.OK).body(commentDtos);
    }

    @PostMapping("/api/restaurant/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentBody){
        CommentDto comment = commentService.createComment(commentBody);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @GetMapping("api/restaurant/comment/{content}")
    public ResponseEntity<List<CommentDto>> filterCommentByContent(@PathVariable String content){
        List<CommentDto> commentDtos = commentService.searchContent(content);
        return ResponseEntity.status(HttpStatus.OK).body(commentDtos);
    }

}
