package com.example.__project_marathon.service;

import com.example.__project_marathon.model.Meal;
import com.example.__project_marathon.model.Menu;
import com.example.__project_marathon.repository.MealRepository;
import com.example.__project_marathon.repository.MenuRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MealRepository mealRepository;

    public String scrapeMenu() throws IOException {
        String url = "https://www.inha.ac.kr/kr/1072/subview.do";
        Document doc = Jsoup.connect(url).get();

        Element table = doc.select("div.table_1 table").first();
        Elements rows = table.select("tr");

        StringBuilder menuHtml = new StringBuilder();

        for (Element row : rows) {
            String category = row.select("th").text();
            String menuName = row.select("td.left").text();

            String price = "";
            Elements tdElements = row.select("td");
            if (!tdElements.isEmpty()) {
                price = tdElements.last() != null ? tdElements.last().text() : "가격 정보 없음";
            }

            if (!category.isEmpty() && !menuName.isEmpty()) {
                menuHtml.append("<tr><td>").append(category).append("</td><td>").append(menuName)
                        .append("</td><td>").append(price).append("</td></tr>");

                // Menu 엔티티 생성 및 저장
                Menu menuEntity = Menu.builder()
                        .date(LocalDateTime.now())
                        .count(1)  // 예시로 1로 설정
                        .price(Integer.parseInt(price.replaceAll("[^0-9]", "")))  // 가격을 숫자로 변환
                        .areaId(1L)  // 예시로 1로 설정
                        .build();

                // menuName을 공백으로 분리하여 각각의 Meal 엔티티 생성 및 저장
                String[] mealNames = menuName.split("\\s+");
                for (String mealName : mealNames) {
                    Meal meal = Meal.builder()
                            .name(mealName)
                            .menu(menuEntity)  // Menu 엔티티와 연관 설정
                            .build();
                    menuEntity.addMeal(meal);  // Menu 엔티티에 Meal 엔티티 추가
                }

                menuRepository.save(menuEntity);  // Menu 엔티티와 연관된 Meal 엔티티 저장
            }
        }

        return menuHtml.toString();  // HTML 문자열 반환
    }
}