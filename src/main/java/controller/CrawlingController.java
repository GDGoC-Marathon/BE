package controller;

import domain.Restaurant;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.CrawlingService;

import java.util.List;

@RestController
public class CrawlingController {

    private final String tableTag = ".table_1 tbody tr";
    private final String thTag = "th[scope=row]";
    private final String tdTag = "td.left";
    private final String tdLast = "td";
    @Autowired
    private CrawlingService crawlingService;

    @GetMapping("/restaurant")
    public List<Restaurant> getRestaurant(){
        String restaurantHtml = crawlingService.getHtmlFromUrl("https://www.inha.ac.kr/kr/1073/subview.do");
        Elements elements = crawlingService.selectFromHtml(restaurantHtml, tableTag);

        return crawlingService.addRestaurant(elements, thTag, tdTag, tdLast);
    }
}
