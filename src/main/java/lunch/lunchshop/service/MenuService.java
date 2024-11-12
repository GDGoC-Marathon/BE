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

        // Find the lunch section based on the specific header text for lunch (중식)
        Element lunchSection = doc.select("h3:contains(중식)").first().parent();
        Element table = lunchSection.select("div.table_1 table").first();
        Elements rows = table.select("tr");

        StringBuilder menuHtml = new StringBuilder();

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






