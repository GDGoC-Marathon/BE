package inhaRestaurant.Comment.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity(name = "inhaRestaurantComment")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String writerName;
    @Column
    private String content;

    public Comment(String id, String writerName, String content){
        this.id = id;
        this.writerName = writerName;
        this.content = content;
    }

}
