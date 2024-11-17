package com.example.__project_marathon.service;

import com.example.__project_marathon.dto.MealDto;
import com.example.__project_marathon.model.Meal;
import com.example.__project_marathon.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    public List<MealDto> getAllMeals() {
        return mealRepository.findAll().stream()
                .map(meal -> MealDto.builder()
                        .id(meal.getId())
                        .name(meal.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public MealDto getMealById(Long id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new RuntimeException("Meal not found"));
        return MealDto.builder()
                .id(meal.getId())
                .name(meal.getName())
                .build();
    }

    public MealDto createMeal(MealDto mealDto) {
        Meal meal = Meal.builder()
                .name(mealDto.getName())
                .build();
        meal = mealRepository.save(meal);
        return MealDto.builder()
                .id(meal.getId())
                .name(meal.getName())
                .build();
    }

    public MealDto updateMeal(Long id, MealDto mealDto) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new RuntimeException("Meal not found"));
        meal.setName(mealDto.getName());
        meal = mealRepository.save(meal);
        return MealDto.builder()
                .id(meal.getId())
                .name(meal.getName())
                .build();
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }
}