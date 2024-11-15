package inhaRestaurant.Comment.repository;

import inhaRestaurant.Comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CommentRepository extends JpaRepository<Comment, String> {

    ArrayList<Comment> finaAll();
    void save();

}
