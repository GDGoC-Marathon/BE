package com.example.__project_marathon.service;

import com.example.__project_marathon.model.Menu;
import com.example.__project_marathon.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ProfessorMenuScraperService {

    @Autowired
    private MenuService menuService;

    public String scrapeMenu() throws IOException {
        String url = "https://www.inha.ac.kr/kr/1073/subview.do";
        Document doc = Jsoup.connect(url).get();

        String weekRange = doc.select("div.moveWeekBox.box_2 strong").text();
        StringBuilder menuHtml = new StringBuilder();
        menuHtml.append("<h2>주간 메뉴: ").append(weekRange).append("</h2>");

        Elements days = doc.select("h2.objHeading_h2");
        for (Element day : days) {
            String date = day.text();
            String formattedDate = DateUtil.formatDate(date);

            Element foodInfoWrap = day.nextElementSibling();
            Elements lunchSections = foodInfoWrap.select("tr");

            menuHtml.append("<h2>").append(date).append("</h2>");

            for (Element lunchSection : lunchSections) {
                String category = lunchSection.select("th[scope=row]").text();
                String menu = lunchSection.select("td.left").html();
                Element priceElement = lunchSection.select("td").last();
                String price = (priceElement != null) ? priceElement.text() : "0";

                if (!category.isEmpty() && !menu.isEmpty()) {
                    menuHtml.append("<h3>").append(category).append("</h3>");
                    menuHtml.append("<p>").append(menu).append("</p>");
                    menuHtml.append("<p>가격: ").append(price).append("</p>");

                    int priceValue = 0;
                    try {
                        priceValue = Integer.parseInt(price.replaceAll("[^0-9]", "0"));
                    } catch (NumberFormatException e) {
                        priceValue = 0;
                    }

                    Menu menuEntity = Menu.builder()
                            .date(LocalDate.parse(formattedDate, DateTimeFormatter.ISO_DATE))
                            .count(1)
                            .price(priceValue)
                            .area("교수식당")
                            .category(category)
                            .build();

                    String[] mealNames = menu.split("\\s+");
                    menuService.saveMenu(menuEntity, mealNames);
                }
            }
        }

        return menuHtml.toString();
    }
}