package com.example.__project_marathon.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name = "MENU")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Meal> meals = new ArrayList<>();

    @Column(nullable = false)
    private Long areaId;  // 추후 fk로 설정 예정

    public void addMeal(Meal meal) {
        meals.add(meal);
        meal.setMenu(this);
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
        meal.setMenu(null);
    }
}