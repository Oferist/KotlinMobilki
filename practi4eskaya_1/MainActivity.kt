package com.example.practi4eskaya_1

import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.Scanner

// Class to store information about a specific expense
data class Expense(
    val amount: Double,
    val category: String,
    val date: LocalDate
) {
    constructor(amount: Double, category: String) : this(
        amount,
        category,
        LocalDate.now()
    )
    // Method to display information about the expense
    fun displayExpense() {
        println("Amount: $amount, Category: $category, Date: $date")
    }
}

// Class to manage the list of all expenses
class ExpenseTracker {
    private val expenses = mutableListOf<Expense>()

    // Method to add a new expense to the list
    fun addExpense(amount: Double, category: String, date: LocalDate) {
        val expense = Expense(amount, category, date)
        expenses.add(expense)
        println("New expense added: $amount, category: $category, date: $date")
    }

    // Method to display all expenses
    fun displayAllExpenses() {
        if (expenses.isEmpty()) {
            println("No expenses recorded.")
        } else {
            println("All expenses:")
            expenses.forEach { it.displayExpense() }
        }
    }

    // Method to calculate the sum of all expenses by category
    fun calculateExpensesByCategory() {
        if (expenses.isEmpty()) {
            println("No expenses recorded.")
            return
        }
        val categoryTotals = expenses.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        println("Total expenses by category:")
        categoryTotals.forEach { (category, total) ->
            println("Category: $category, Total: $total")
        }
    }
}

// Example with a dynamic menu
fun main() {
    val expenseTracker = ExpenseTracker()
    val scanner = Scanner(System.`in`)

    while (true) {
        println(
            """
            Choose an action:
            1. Add a new expense
            2. Show all expenses
            3. Show total expenses by categories
            4. Exit
            """.trimIndent()
        )
        print("Enter the action number: ")
        when (scanner.nextLine().trim()) {
            "1" -> {
                try {
                    print("Enter the expense amount: ")
                    val amount = scanner.nextLine().toDouble()

                    print("Enter the expense category: ")
                    val category = scanner.nextLine()

                    print("Enter the expense date (in the format YYYY-MM-DD): ")
                    val date = LocalDate.parse(scanner.nextLine())

                    expenseTracker.addExpense(amount, category, date)
                } catch (e: NumberFormatException) {
                    println("Error: Amount must be a number.")
                } catch (e: DateTimeParseException) {
                    println("Error: Date must be in the format YYYY-MM-DD.")
                } catch (e: Exception) {
                    println("An error occurred: ${e.message}")
                }
            }

            "2" -> {
                expenseTracker.displayAllExpenses()
            }

            "3" -> {
                expenseTracker.calculateExpensesByCategory()
            }

            "4" -> {
                println("Exiting the program. Goodbye!")
                break
            }

            else -> {
                println("Error: Please choose a valid action.")
            }
        }
        println()
    }
}
