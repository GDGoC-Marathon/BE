package com.example.__project_marathon.service;

import com.example.__project_marathon.model.Menu;
import com.example.__project_marathon.util.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class StudentMenuScraperService {
    @Autowired
    private MenuService menuService;

    public String scrapeMenu() throws IOException {
        String url = "https://www.inha.ac.kr/kr/1072/subview.do";
        Document doc = Jsoup.connect(url).get();

        String weekRange = doc.select("div.moveWeekBox.box_2 strong").text();
        StringBuilder menuHtml = new StringBuilder();
        menuHtml.append("<h2>주간 메뉴: ").append(weekRange).append("</h2>");

        Elements days = doc.select("h2.objHeading_h2");
        for (Element day : days) {
            String date = day.text();
            String formattedDate = DateUtil.formatDate(date);

            Element foodInfoWrap = day.nextElementSibling();
            Element lunchSection = foodInfoWrap.select("h3.objHeading_h3:contains(중식)").first();

            if (lunchSection != null) {
                String time = lunchSection.text();
                menuHtml.append("<h2>").append(date).append("</h2>");
                menuHtml.append("<h3>").append(time).append("</h3>");

                Element table = lunchSection.nextElementSibling().select("table").first();
                Elements rows = table.select("tr");

                menuHtml.append("<table border='1'><thead><tr><th>구분</th><th>메뉴</th><th>가격</th></tr></thead><tbody>");

                for (Element row : rows) {
                    String category = row.select("th").text();
                    String menu = row.select("td.left").html();
                    String price = "0";

                    Elements tdElements = row.select("td");
                    if (!tdElements.isEmpty() && tdElements.last() != null) {
                        price = tdElements.last().text();
                    }

                    if (!category.isEmpty() && !menu.isEmpty()) {
                        menuHtml.append("<tr><td>").append(category).append("</td><td>").append(menu)
                                .append("</td><td>").append(price).append("</td></tr>");

                        int priceValue = 0;
                        try {
                            priceValue = Integer.parseInt(price.replaceAll("[^0-9]", "0"));
                        } catch (NumberFormatException e) {
                            // 빈 문자열 또는 숫자가 아닌 문자열을 기본값 0으로 처리
                            priceValue = 0;
                        }

                        Menu menuEntity = Menu.builder()
                                .date(LocalDate.parse(formattedDate, DateTimeFormatter.ISO_DATE))
                                .count(1)
                                .price(priceValue)
                                .area("학생식당")
                                .category(category)
                                .build();

                        String[] mealNames = menu.split("\\s+");
                        menuService.saveMenu(menuEntity, mealNames);
                    }
                }
                menuHtml.append("</tbody></table>");
            }
        }

        return menuHtml.toString();
    }
}