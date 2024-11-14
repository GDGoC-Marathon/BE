package inhaRestaurant.domain;

import lombok.Getter;

@Getter
public class Restaurant {

    private String lunchName;
    private String lunchItems;
    private String price;

    public Restaurant(String lunchName, String lunchItems, String price){
        this.lunchName = lunchName;
        this.lunchItems = lunchItems;
        this.price = price;
    }
}
