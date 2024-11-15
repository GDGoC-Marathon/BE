package inhaRestaurant.domain;

public class Comment {

    private String id;
    private String writerName;
    private String content;

    public Comment(String id, String writerName, String content){
        this.id = id;
        this.writerName = writerName;
        this.content = content;
    }

}
