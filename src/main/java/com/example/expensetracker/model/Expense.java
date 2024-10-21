package com.example.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @ManyToMany
    private List<User> participants;

    @Enumerated(EnumType.STRING)
    private SplitMethod splitMethod;

    @ElementCollection
    private Map<String, Double> exactAmounts = new HashMap<>(); // Initialize as an empty map

    @ElementCollection
    private Map<String, Double> percentageSplits = new HashMap<>(); // Initialize as an empty map
}
