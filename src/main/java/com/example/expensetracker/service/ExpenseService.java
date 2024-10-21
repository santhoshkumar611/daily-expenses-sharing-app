package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.SplitMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

//    Service class for expense
    public Map<String, Double> splitExpense(Expense expense) {
        if (expense.getSplitMethod() == SplitMethod.EQUAL) {
            return calculateEqualSplit(expense);
        } else if (expense.getSplitMethod() == SplitMethod.EXACT) {
            return removeNullKeys(expense.getExactAmounts());
        } else if (expense.getSplitMethod() == SplitMethod.PERCENTAGE) {
            return calculatePercentageSplit(expense);
        }
        throw new IllegalArgumentException("Invalid split method");
    }

    private Map<String, Double> removeNullKeys(Map<String, Double> map) {
        // Remove entries with null keys
        return map.entrySet()
                .stream()
                .filter(entry -> entry.getKey() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, Double> calculateEqualSplit(Expense expense) {
        double totalAmount = expense.getAmount();
        int numberOfParticipants = expense.getParticipants().size();

        // Validate participants
        if (numberOfParticipants == 0) {
            throw new IllegalArgumentException("There must be at least one participant.");
        }

        double splitAmount = totalAmount / numberOfParticipants;

        Map<String, Double> equalSplits = new HashMap<>();
        for (var participant : expense.getParticipants()) {
            if (participant.getId() != null) { // Check for null ID
                equalSplits.put(participant.getId().toString(), splitAmount);
            }
        }
        return equalSplits;
    }

    private Map<String, Double> calculatePercentageSplit(Expense expense) {
        double totalAmount = expense.getAmount();
        Map<String, Double> percentageSplits = new HashMap<>();

        for (var participant : expense.getParticipants()) {
            String userId = participant.getId() != null ? participant.getId().toString() : null; // Safeguard for null ID
            double percentage = expense.getPercentageSplits().getOrDefault(userId, 0.0);
            double splitAmount = (percentage / 100) * totalAmount;

            if (userId != null) { // Check for null ID before adding to the map
                percentageSplits.put(userId, splitAmount);
            }
        }
        return percentageSplits;
    }
}
