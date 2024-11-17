package com.example.__project_marathon.controller;

import com.example.__project_marathon.model.Menu;
import com.example.__project_marathon.service.ProfessorMenuScraperService;
import com.example.__project_marathon.service.StudentMenuScraperService;
import com.example.__project_marathon.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.lang.model.util.Elements;
import javax.swing.text.Document;
import java.io.IOException;
import java.util.List;

@Controller
public class MenuController {
    private final String tableTag = ".table_1 tbody tr";
    private final String thTag = "th[scope=row]";
    private final String tdTag = "td.left";
    private final String tdLast = "td";

    @Autowired
    private StudentMenuScraperService studentMenuScraperService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ProfessorMenuScraperService professorMenuScraperService;

    @GetMapping("/student-lunch-menu")
    public String getStudent(Model model) throws Exception {
        // MenuService에서 크롤링한 데이터를 가져오기
        String menuHtml = studentMenuScraperService.scrapeMenu();

        // 모델에 데이터를 담아 menu.html로 전달
        model.addAttribute("menu", menuHtml);

        return "student-menu";  // student-menu.html 파일을 반환
    }

    @GetMapping("/professor-lunch-menu")
    public String getProfessor(Model model) throws IOException {
        // MenuService에서 크롤링한 데이터를 가져오기
        String menuHtml = professorMenuScraperService.scrapeMenu();

        // 모델에 데이터를 담아 menu.html로 전달
        model.addAttribute("menu", menuHtml);

        return "professor-menu";  // student-menu.html 파일을 반환
    }

    @GetMapping("/{id}/increment-count")
    public Menu incrementMenuCount(@PathVariable Long id) {
        return menuService.incrementMenuCount(id);
    }

    @GetMapping("/{id}/decrement-count")
    public Menu decrementMenuCount(@PathVariable Long id) {
        return menuService.decrementMenuCount(id);
    }

    @GetMapping("/{id}/increment-count-by")
    public Menu incrementMenuCountBy(@PathVariable Long id, @RequestParam int amount) {
        return menuService.incrementMenuCountBy(id, amount);
    }

    @GetMapping("/{id}/decrement-count-by")
    public Menu decrementMenuCountBy(@PathVariable Long id, @RequestParam int amount) {
        return menuService.decrementMenuCountBy(id, amount);
    }

    @GetMapping("/admin-page")
    public String getAdminPage(Model model) {
        List<Menu> menus = menuService.getAllMenus();
        model.addAttribute("menus", menus);
        return "adminpage";
    }
}