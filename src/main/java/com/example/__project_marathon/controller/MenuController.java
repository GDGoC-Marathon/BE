package com.example.__project_marathon.controller;

import com.example.__project_marathon.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/lunch-menu")
    public String getLunchMenu(Model model) throws Exception {
        // MenuService에서 크롤링한 데이터를 가져오기
        String menuHtml = menuService.scrapeMenu();

        // 모델에 데이터를 담아 menu.html로 전달
        model.addAttribute("menu", menuHtml);

        return "menu";  // menu.html 파일을 반환
    }
}