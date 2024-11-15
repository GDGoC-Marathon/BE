package inhaRestaurant.service;

import inhaRestaurant.domain.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CrawlingService {

    private final String nextLineHtml = "<br>";
    private final String nextLineCode = "\n";
    private final String lunch = "중식";
    private final String tableTag = "table";
    private final String divTag = "div.foodInfoWrap";
    private final String findH2Tag = "h2.objHeading_h2";

    public Document getHtmlFromUrl(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc;
        } catch (Exception e) {
            throw new IllegalArgumentException("해당 url 주소를 가져올 수 없습니다.");
        }
    }

    public Elements selectFromHtml(Document doc, String selectRows){
        Elements rows = doc.select(selectRows);
        return rows;
    }

    public List<Restaurant> addRestaurant(Elements rows, String selectTag1, String selectTag2, String selectTag3){
        List<Restaurant> restaurants = new ArrayList<>();
        for(Element row : rows){
            String day = getDayFromHtml(row);
            String lunchName = getTextFromHtml(row, selectTag1);
            String lunchItems = getHtmlFromHtml(row, selectTag2);
            String price = getTextFromLastHtml(row, selectTag3);
            log.info("식사 종류 : " + lunchName);
            if(isLunch(lunchName)){
                restaurants.add(new Restaurant(day, lunchName, lunchItems, price));
            }
        }

        return restaurants;
    }

    private boolean isLunch(String lunchName){
        String kindOfMeal = lunchName.trim().replaceAll("\\s+", "");
        if(kindOfMeal.contains(lunch)) {
            return true;
        }
        return false;
    }

    private String getDayFromHtml(Element row){
        Element table = row.closest(tableTag);
        Element headingElement = table.closest(divTag)
                .previousElementSibling()
                .select(findH2Tag)
                .first();// h2 태그 선택

        String dayText = headingElement.text();
        return dayText;
    }

    private String getTextFromHtml(Element row, String text){
        return row.select(text).text();
    }

    private String getHtmlFromHtml(Element row, String text){
        return row.select(text).html().replaceAll(nextLineHtml, nextLineCode);
    }

    private String getTextFromLastHtml(Element row, String text){
        return row.select(text).last().text();
    }

}
