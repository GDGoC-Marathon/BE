package com.example.__project_marathon.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "MEAL")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    public static class MealBuilder {
        public MealBuilder menu(Menu menu) {
            this.menu = menu;
            return this;
        }
    }
}