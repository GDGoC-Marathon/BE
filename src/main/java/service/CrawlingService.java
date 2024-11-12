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

    public List<Restaurant> getMenu() {
    }

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

}
