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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    // 메뉴 크롤링, DB에 저장
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
                    String price = "0"; // 기본값 설정

                    Elements tdElements = row.select("td");
                    if (!tdElements.isEmpty() && tdElements.last() != null) {
                        price = tdElements.last().text();
                    }

                    if (!category.isEmpty() && !menu.isEmpty()) {
                        menuHtml.append("<tr><td>").append(category).append("</td><td>").append(menu)
                                .append("</td><td>").append(price).append("</td></tr>");

                        // Menu 엔티티 생성 및 저장
                        // 날짜 형식 변환
                        String formattedDate = "2024-" + date.replaceAll("[^0-9.]", "").replace(".", "-").substring(0, 5);

                        Menu menuEntity = Menu.builder()
                                .date(LocalDate.parse(formattedDate, DateTimeFormatter.ISO_DATE))
                                .count(1)  // 예시로 1로 설정
                                .price(Integer.parseInt(price.replaceAll("[^0-9]", "0")))  // 가격을 숫자로 변환
                                .areaId(1L)  // 예시로 1로 설정
                                .build();

                        // menu를 공백으로 분리하여 각각의 Meal 엔티티 생성 및 저장
                        String[] mealNames = menu.split("\\s+");
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
                menuHtml.append("</tbody></table>");
            }
        }

        return menuHtml.toString();  // HTML 문자열 반환
    }

    public Menu incrementMenuCount(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.setCount(menu.getCount() + 1);
        return menuRepository.save(menu);
    }

    public Menu decrementMenuCount(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.setCount(menu.getCount() - 1);
        return menuRepository.save(menu);
    }
}