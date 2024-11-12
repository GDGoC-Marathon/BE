package com.example.__project_marathon.service;

import com.example.__project_marathon.model.Menu;
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

    public String scrapeMenu() throws IOException {
        String url = "https://www.inha.ac.kr/kr/1072/subview.do";
        Document doc = Jsoup.connect(url).get();

        Element table = doc.select("div.table_1 table").first();
        Elements rows = table.select("tr");

        StringBuilder menuHtml = new StringBuilder();

        for (Element row : rows) {
            String category = row.select("th").text();
            String menu = row.select("td.left").text();

            String price = "";
            Elements tdElements = row.select("td");
            if (!tdElements.isEmpty()) {
                price = tdElements.last() != null ? tdElements.last().text() : "가격 정보 없음";
            }

            if (!category.isEmpty() && !menu.isEmpty()) {
                menuHtml.append("<tr><td>").append(category).append("</td><td>").append(menu)
                        .append("</td><td>").append(price).append("</td></tr>");

                // Menu 엔티티 생성 및 저장
                Menu menuEntity = Menu.builder()
                        .date(LocalDateTime.now())
                        .count(1)  // 예시로 1로 설정
                        .price(Integer.parseInt(price.replaceAll("[^0-9]", "")))  // 가격을 숫자로 변환
                        .build();
                menuRepository.save(menuEntity);
            }
        }

        return menuHtml.toString();  // HTML 문자열 반환
    }
}