package inhaRestaurant.service;

import inhaRestaurant.domain.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CrawlingService {

    private static final String nextLineHtml = "<br>";
    private static final String nextLineCode = "\n";

    public Document getHtmlFromUrl(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            log.info("document fetched successfully : " + doc.title());
            return doc;
        } catch (Exception e) {
            return null;
        }
    }

    public Elements selectFromHtml(Document doc, String selectRows){
        Elements rows = doc.select(selectRows);
        return rows;
    }

    public List<Restaurant> addRestaurant(Elements rows, String selectTag1, String selectTag2, String selectTag3){
        List<Restaurant> restaurants = new ArrayList<>();

        for(Element row : rows){
            log.info("Element 내용: " + row.text());  // 각 element의 텍스트만 출력
            log.info("Element HTML: " + row.html());
            String lunchName = getTextFromHtml(row, selectTag1);

            log.info(lunchName);
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
        return row.select(text).last().text();
    }
}
