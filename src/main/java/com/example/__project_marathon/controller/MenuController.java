package com.example.__project_marathon.controller;

import com.example.__project_marathon.model.Menu;
import com.example.__project_marathon.service.MenuScraperService;
import com.example.__project_marathon.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private MenuScraperService menuScraperService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/lunch-menu")
    public String getLunchMenu(Model model) throws Exception {
        // MenuService에서 크롤링한 데이터를 가져오기
        String menuHtml = menuScraperService.scrapeMenu();

        // 모델에 데이터를 담아 menu.html로 전달
        model.addAttribute("menu", menuHtml);

        return "menu";  // menu.html 파일을 반환
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