package inhaRestaurant.Crawling.domain;

import lombok.Getter;

@Getter
public class Restaurant {

    private String dayOfTheWeek;
    private String lunchName;
    private String lunchItems;
    private String price;

    public Restaurant(String dayOfTheWeek, String lunchName, String lunchItems, String price){
        this.dayOfTheWeek = dayOfTheWeek;
        this.lunchName = lunchName;
        this.lunchItems = lunchItems;
        this.price = price;
    }
}
