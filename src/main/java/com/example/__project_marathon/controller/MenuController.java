package com.example.__project_marathon.controller;

import com.example.__project_marathon.model.Menu;
import com.example.__project_marathon.service.ProfessorMenuScraperService;
import com.example.__project_marathon.service.StudentMenuScraperService;
import com.example.__project_marathon.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
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
    public String getStudent() throws Exception {
        return studentMenuScraperService.scrapeMenu();
    }

    @GetMapping("/professor-lunch-menu")
    public String getProfessor() throws IOException {
        return professorMenuScraperService.scrapeMenu();
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
    public List<Menu> getAdminPage() {
        return menuService.getAllMenus();
    }
}