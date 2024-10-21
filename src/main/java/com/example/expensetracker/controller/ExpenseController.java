package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Map<String, Double> addExpense(@RequestBody Expense expense) {
        Map<String, Double> splitResults = expenseService.splitExpense(expense);
        return splitResults != null ? splitResults : new HashMap<>(); // Ensure no null is returned
    }
}
