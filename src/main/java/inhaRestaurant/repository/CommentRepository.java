package inhaRestaurant.repository;

import inhaRestaurant.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CommentRepository extends JpaRepository<Comment, String> {



}
