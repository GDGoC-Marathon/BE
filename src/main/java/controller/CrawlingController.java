package controller;

import domain.Restaurant;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
    public String getRestaurant(Model model){
        String restaurantHtml = crawlingService.getHtmlFromUrl("https://www.inha.ac.kr/kr/1073/subview.do");
        Elements elements = crawlingService.selectFromHtml(restaurantHtml, tableTag);
        List<Restaurant> restaurants = crawlingService.addRestaurant(elements, thTag, tdTag, tdLast);

        model.addAttribute("restaurants", restaurants);
        return "restaurant";

    }
}
