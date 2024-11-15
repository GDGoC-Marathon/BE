package inhaRestaurant.controller;

import inhaRestaurant.domain.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import inhaRestaurant.service.CrawlingService;

import java.util.List;

@Controller
@Slf4j
public class CrawlingController {

    private final String tableTag = ".table_1 tbody tr";
    private final String thTag = "th[scope=row]";
    private final String tdTag = "td.left";
    private final String tdLast = "td";

    @Autowired
    private CrawlingService crawlingService;

    @GetMapping("/restaurant")
    public String getRestaurant(Model model){
        String url = "https://www.inha.ac.kr/kr/1073/subview.do";
        Document htmlInha = crawlingService.getHtmlFromUrl(url);
        Elements elements = crawlingService.selectFromHtml(htmlInha, tableTag);
        List<Restaurant> restaurants = crawlingService.addRestaurant(elements, thTag, tdTag, tdLast);
        model.addAttribute("restaurants", restaurants);
        return "professorRestaurant";
    }

}
