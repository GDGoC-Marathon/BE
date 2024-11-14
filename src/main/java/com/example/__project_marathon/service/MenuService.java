package com.example.__project_marathon.service;

import com.example.__project_marathon.model.Meal;
import com.example.__project_marathon.model.Menu;
import com.example.__project_marathon.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

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

    public Menu incrementMenuCountBy(Long id, int amount) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.setCount(menu.getCount() + amount);
        return menuRepository.save(menu);
    }

    public Menu decrementMenuCountBy(Long id, int amount) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.setCount(menu.getCount() - amount);
        return menuRepository.save(menu);
    }

    public void saveMenu(Menu menuEntity, String[] mealNames) {
        for (String mealName : mealNames) {
            mealName = mealName.replace("<br>", "").replace("&amp;", "&").trim();
            if (!mealName.isEmpty()) {
                Meal meal = Meal.builder()
                        .name(mealName)
                        .menu(menuEntity)
                        .build();
                menuEntity.addMeal(meal);
            }
        }
        menuRepository.save(menuEntity);
    }
}