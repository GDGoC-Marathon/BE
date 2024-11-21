package com.example.__project_marathon.repository;

import com.example.__project_marathon.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, String> {

    ArrayList<Comments> findAll();
    @Query(value = "SELECT * FROM Comment WHERE content Like %?1%", nativeQuery = true)
    List<Comments> findByCommentContent(String content);

}
