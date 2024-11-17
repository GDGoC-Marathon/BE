package com.example.__project_marathon.repository;

import com.example.__project_marathon.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    ArrayList<Comment> findAll();
    @Query(value = "SELECT * FROM Comment WHERE content Like %?1%", nativeQuery = true)
    List<Comment> findByCommentContent(String content);

}
