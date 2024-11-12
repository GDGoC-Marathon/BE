package com.example.__project_marathon.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class MenuService {

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
            }
        }

        return menuHtml.toString();  // HTML 문자열 반환
    }
}