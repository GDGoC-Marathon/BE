package com.example.__project_marathon.controller;

import com.example.__project_marathon.dto.MealDto;
import com.example.__project_marathon.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

//    @Autowired
//    private MealService mealService;
//
//    @GetMapping
//    public List<MealDto> getAllMeals() {
//        return mealService.getAllMeals();
//    }
//
//    @GetMapping("/{id}")
//    public MealDto getMealById(@PathVariable Long id) {
//        return mealService.getMealById(id);
//    }
//
//    @PostMapping
//    public MealDto createMeal(@RequestBody MealDto mealDto) {
//        return mealService.createMeal(mealDto);
//    }
//
//    @PutMapping("/{id}")
//    public MealDto updateMeal(@PathVariable Long id, @RequestBody MealDto mealDto) {
//        return mealService.updateMeal(id, mealDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteMeal(@PathVariable Long id) {
//        mealService.deleteMeal(id);
//    }
}