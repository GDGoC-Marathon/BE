package lunch.lunchshop.service;

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

        // 주간 날짜 범위를 가져오기
        String weekRange = doc.select("div.moveWeekBox.box_2 strong").text();

        //날짜와 시간 가져오기
        String date = doc.select("h2.objHeading_h2").first().text(); // "월요일 (11.11.)"
        String time = doc.select("h3.objHeading_h3:contains(중식)").first().text();

        // Find the lunch section based on the specific header text for lunch (중식)
        Element lunchSection = doc.select("h3:contains(중식)").first().parent();
        Element table = lunchSection.select("div.table_1 table").first();
        Elements rows = table.select("tr");

        StringBuilder menuHtml = new StringBuilder();
        menuHtml.append("<h2>주간 메뉴: ").append(weekRange).append("</h2>"); //주간범위
        menuHtml.append("<h2>").append(date).append("</h2>");
        menuHtml.append("<h3>").append(time).append("</h3>");

        for (Element row : rows) {
            String category = row.select("th").text();
            String menu = row.select("td.left").html(); // Using html() to keep line breaks
            String price = "";
            Elements tdElements = row.select("td");
            if (!tdElements.isEmpty() && tdElements.last() != null) {
                price = tdElements.last().text();
            } else {
                price = "가격 정보 없음";  // Default text if price is missing
            }

            if (!category.isEmpty() && !menu.isEmpty()) {
                menuHtml.append("<tr><td>").append(category).append("</td><td>").append(menu)
                        .append("</td><td>").append(price).append("</td></tr>");
            }
        }

        return menuHtml.toString();
    }
}






