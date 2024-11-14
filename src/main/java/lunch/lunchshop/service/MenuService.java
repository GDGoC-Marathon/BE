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

        // 주간 날짜 범위 가져오기
        String weekRange = doc.select("div.moveWeekBox.box_2 strong").text();
        StringBuilder menuHtml = new StringBuilder();
        menuHtml.append("<h2>주간 메뉴: ").append(weekRange).append("</h2>"); // 주간 범위 추가

        // 요일별 메뉴 수집
        Elements days = doc.select("h2.objHeading_h2"); // 요일 제목을 모두 선택
        for (Element day : days) {
            String date = day.text(); // 요일과 날짜 정보 (예: "월요일 (11.11.)")

            // 중식 테이블을 찾기 위한 컨테이너 선택
            Element foodInfoWrap = day.nextElementSibling(); // 다음 요소에서 메뉴를 포함한 div 찾기
            Element lunchSection = foodInfoWrap.select("h3.objHeading_h3:contains(중식)").first();

            if (lunchSection != null) {
                String time = lunchSection.text(); // 시간 (예: "중식 (11:00 ~ 14:00)")
                menuHtml.append("<h2>").append(date).append("</h2>");
                menuHtml.append("<h3>").append(time).append("</h3>");

                Element table = lunchSection.nextElementSibling().select("table").first();
                Elements rows = table.select("tr");

                menuHtml.append("<table border='1'><thead><tr><th>구분</th><th>메뉴</th><th>가격</th></tr></thead><tbody>");

                // 메뉴 테이블에 각 행 추가
                for (Element row : rows) {
                    String category = row.select("th").text();
                    String menu = row.select("td.left").html();
                    String price = "가격 정보 없음"; // 기본값 설정

                    Elements tdElements = row.select("td");
                    if (!tdElements.isEmpty() && tdElements.last() != null) {
                        price = tdElements.last().text();
                    }

                    if (!category.isEmpty() && !menu.isEmpty()) {
                        menuHtml.append("<tr><td>").append(category).append("</td><td>").append(menu)
                                .append("</td><td>").append(price).append("</td></tr>");
                    }
                }
                menuHtml.append("</tbody></table>");
            }
        }

        return menuHtml.toString();
    }

}






