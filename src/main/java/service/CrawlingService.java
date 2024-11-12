package service;

import domain.Restaurant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrawlingService {

    private static final String nextLineHtml = "<br>";
    private static final String nextLineCode = "\n";

    public String getHtmlFromUrl(String url){
        RestTemplate restTemplate = new RestTemplate();
        String html = restTemplate.getForObject(url, String.class);
        return html;
    }

    public Elements selectFromHtml(String html, String selectRows){
        Document doc = Jsoup.parse(html);
        Elements rows = doc.select(selectRows);
        return rows;
    }

    public List<Restaurant> addRestaurant(Elements rows, String selectTag1, String selectTag2, String selectTag3){
        List<Restaurant> restaurants = new ArrayList<>();

        for(Element row : rows){
            String lunchName = getTextFromHtml(row, selectTag1);
            String lunchItems = getHtmlFromHtml(row, selectTag2);
            String price = getTextFromLastHtml(row, selectTag3);

            restaurants.add(new Restaurant(lunchName, lunchItems, price));
        }

        return restaurants;
    }

    private String getTextFromHtml(Element row, String text){
        return row.select(text).text();
    }

    private String getHtmlFromHtml(Element row, String text){
        return row.select(text).html().replaceAll(nextLineHtml, nextLineCode);
    }

    private String getTextFromLastHtml(Element row, String text){
        return row.select(text).text();
    }
}
