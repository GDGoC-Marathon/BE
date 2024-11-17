package com.example.__project_marathon.repository;

import com.example.__project_marathon.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    ArrayList<Comment> findAll();

}
