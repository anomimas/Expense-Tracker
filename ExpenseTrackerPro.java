// ExpenseTrackerPro.java - Complete program with Indian Rupees (₹)
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExpenseTrackerPro {
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String BOLD = "\u001B[1m";
    
    static class Transaction {
        String type;
        double amount;
        String category;
        String description;
        String date;
        
        Transaction(String type, double amount, String category, String description) {
            this.type = type;
            this.amount = amount;
            this.category = category;
            this.description = description;
            this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
    }
    
    public static void main(String[] args) {
        clearScreen();
        while (true) {
            printHeader();
            printMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1: addIncome(); break;
                case 2: addExpense(); break;
                case 3: viewTransactions(); break;
                case 4: viewSummary(); break;
                case 5: categoryBreakdown(); break;
                case 6: searchTransactions(); break;
                case 7: exportToFile(); break;
                case 8: 
                    printGoodbye();
                    return;
                default: 
                    printError("Invalid choice! Press Enter to continue...");
                    scanner.nextLine();
            }
        }
    }
    
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    private static void printHeader() {
        System.out.println(BLUE + "╔" + "═".repeat(58) + "╗" + RESET);
        System.out.println(BLUE + "║" + RESET + "             " + BOLD + "🇮🇳 EXPENSE TRACKER PRO 🇮🇳" + RESET + "              " + BLUE + "║" + RESET);
        System.out.println(BLUE + "╠" + "═".repeat(58) + "╣" + RESET);
        System.out.println(BLUE + "║" + RESET + "        " + CYAN + "Take Control of Your Finances" + RESET + "             " + BLUE + "║" + RESET);
        System.out.println(BLUE + "╚" + "═".repeat(58) + "╝" + RESET);
    }
    
    private static void printMenu() {
        System.out.println("\n" + WHITE + "┌────────────────────────────────────────────────────┐" + RESET);
        System.out.println(WHITE + "│" + RESET + "                      " + BOLD + "MAIN MENU" + RESET + "                       " + WHITE + "│" + RESET);
        System.out.println(WHITE + "├────────────────────────────────────────────────────┤" + RESET);
        System.out.println(WHITE + "│" + RESET + "  " + GREEN + "1.  ➕ ADD INCOME" + RESET + "                                 " + WHITE + "│" + RESET);
        System.out.println(WHITE + "│" + RESET + "  " + RED + "2.  ➖ ADD EXPENSE" + RESET + "                                " + WHITE + "│" + RESET);
        System.out.println(WHITE + "│" + RESET + "  " + CYAN + "3.  📋 VIEW ALL TRANSACTIONS" + RESET + "                     " + WHITE + "│" + RESET);
        System.out.println(WHITE + "│" + RESET + "  " + YELLOW + "4.  📊 FINANCIAL SUMMARY" + RESET + "                         " + WHITE + "│" + RESET);
        System.out.println(WHITE + "│" + RESET + "  " + PURPLE + "5.  📈 CATEGORY BREAKDOWN" + RESET + "                        " + WHITE + "│" + RESET);
        System.out.println(WHITE + "│" + RESET + "  " + BLUE + "6.  🔍 SEARCH TRANSACTIONS" + RESET + "                         " + WHITE + "│" + RESET);
        System.out.println(WHITE + "│" + RESET + "  " + GREEN + "7.  💾 EXPORT TO FILE" + RESET + "                             " + WHITE + "│" + RESET);
        System.out.println(WHITE + "│" + RESET + "  " + RED + "8.  🚪 EXIT" + RESET + "                                       " + WHITE + "│" + RESET);
        System.out.println(WHITE + "└────────────────────────────────────────────────────┘" + RESET);
        System.out.print(BOLD + "👉 Enter your choice (1-8): " + RESET);
    }
    
    private static int getUserChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (Exception e) {
            scanner.nextLine();
            return 0;
        }
    }
    
    private static void addIncome() {
        printSection("ADD INCOME", GREEN);
        
        double amount = getAmount("Enter amount: ₹");
        if (amount <= 0) return;
        
        String category = getCategory("INCOME");
        String description = getDescription();
        
        transactions.add(new Transaction("INCOME", amount, category, description));
        printSuccess("✅ Income added successfully!");
        displayTransaction(transactions.get(transactions.size() - 1));
        pressEnterToContinue();
    }
    
    private static void addExpense() {
        printSection("ADD EXPENSE", RED);
        
        double amount = getAmount("Enter amount: ₹");
        if (amount <= 0) return;
        
        String category = getCategory("EXPENSE");
        String description = getDescription();
        
        transactions.add(new Transaction("EXPENSE", amount, category, description));
        printSuccess("✅ Expense added successfully!");
        displayTransaction(transactions.get(transactions.size() - 1));
        pressEnterToContinue();
    }
    
    private static double getAmount(String prompt) {
        while (true) {
            System.out.print(YELLOW + prompt + RESET);
            try {
                double amount = scanner.nextDouble();
                scanner.nextLine();
                if (amount <= 0) {
                    printError("Amount must be positive!");
                    continue;
                }
                return amount;
            } catch (Exception e) {
                printError("Invalid amount! Please enter a number.");
                scanner.nextLine();
            }
        }
    }
    
    private static String getCategory(String type) {
        String[] categories;
        if (type.equals("INCOME")) {
            categories = new String[]{"Salary", "Freelance", "Business", "Investment", "Rental Income", 
                                     "Gift", "Refund", "Bonus", "Other Income"};
        } else {
            categories = new String[]{"Food & Dining", "Groceries", "Transportation", "Fuel", 
                                     "Shopping", "Entertainment", "Movies", "Bills & Utilities", 
                                     "Electricity", "Water Bill", "Internet", "Mobile Recharge",
                                     "Healthcare", "Medicine", "Education", "Rent", "EMI Payments",
                                     "Travel", "Hotel", "Insurance", "Other Expenses"};
        }
        
        System.out.println("\n" + CYAN + "📌 Select category:" + RESET);
        System.out.println("┌─────────────────────┐");
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("│ %2d. %-16s │%n", i+1, categories[i]);
            if (i < categories.length - 1) System.out.println("├─────────────────────┤");
        }
        System.out.println("└─────────────────────┘");
        
        while (true) {
            System.out.print("Enter choice (1-" + categories.length + "): ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= categories.length) {
                    return categories[choice - 1];
                }
                printError("Invalid choice!");
            } catch (Exception e) {
                printError("Invalid input!");
                scanner.nextLine();
            }
        }
    }
    
    private static String getDescription() {
        System.out.print(CYAN + "📝 Description: " + RESET);
        return scanner.nextLine();
    }
    
    private static void viewTransactions() {
        if (transactions.isEmpty()) {
            printWarning("No transactions yet!");
            pressEnterToContinue();
            return;
        }
        
        printSection("ALL TRANSACTIONS", CYAN);
        
        System.out.println(BLUE + "┌────┬─────────────────────┬──────────┬──────────────────────┬──────────────┬──────────────────────┐" + RESET);
        System.out.println(BLUE + "│ #  │ Date & Time         │ Type     │ Category             │ Amount       │ Description" + RESET);
        System.out.println(BLUE + "├────┼─────────────────────┼──────────┼──────────────────────┼──────────────┼──────────────────────┤" + RESET);
        
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            String typeColor = t.type.equals("INCOME") ? GREEN : RED;
            String typeSymbol = t.type.equals("INCOME") ? "⬆" : "⬇";
            
            System.out.printf(BLUE + "│" + RESET + " %2d " + BLUE + "│" + RESET + " %-19s " + BLUE + "│" + RESET + " " + typeColor + "%-8s" + RESET + " " + BLUE + "│" + RESET + " %-20s " + BLUE + "│" + RESET + " ₹%-10.2f " + BLUE + "│" + RESET + " %-20s " + BLUE + "│%n" + RESET,
                i+1, t.date, typeSymbol + " " + t.type, truncate(t.category, 20), t.amount, truncate(t.description, 20));
            
            if (i < transactions.size() - 1) {
                System.out.println(BLUE + "├────┼─────────────────────┼──────────┼──────────────────────┼──────────────┼──────────────────────┤" + RESET);
            }
        }
        
        System.out.println(BLUE + "└────┴─────────────────────┴──────────┴──────────────────────┴──────────────┴──────────────────────┘" + RESET);
        System.out.println(CYAN + "\n📊 Total transactions: " + transactions.size() + RESET);
        pressEnterToContinue();
    }
    
    private static String truncate(String text, int length) {
        if (text.length() <= length) return text;
        return text.substring(0, length - 3) + "...";
    }
    
    private static void viewSummary() {
        double income = 0, expense = 0;
        
        for (Transaction t : transactions) {
            if (t.type.equals("INCOME")) income += t.amount;
            else expense += t.amount;
        }
        
        double balance = income - expense;
        
        printSection("FINANCIAL SUMMARY", YELLOW);
        
        System.out.println(YELLOW + "┌─────────────────────────────────────┐" + RESET);
        System.out.printf(YELLOW + "│" + RESET + " %-35s " + YELLOW + "│%n" + RESET, "💰 INCOME:  ₹" + String.format("%.2f", income));
        System.out.println(YELLOW + "├─────────────────────────────────────┤" + RESET);
        System.out.printf(YELLOW + "│" + RESET + " %-35s " + YELLOW + "│%n" + RESET, "💸 EXPENSE: ₹" + String.format("%.2f", expense));
        System.out.println(YELLOW + "├─────────────────────────────────────┤" + RESET);
        
        if (balance >= 0) {
            System.out.printf(YELLOW + "│" + RESET + " %-35s " + YELLOW + "│%n" + RESET, "✅ BALANCE:  ₹" + String.format("%.2f", balance));
        } else {
            System.out.printf(YELLOW + "│" + RESET + " %-35s " + YELLOW + "│%n" + RESET, "⚠️ BALANCE:  ₹" + String.format("%.2f", balance));
        }
        System.out.println(YELLOW + "└─────────────────────────────────────┘" + RESET);
        
        if (income > 0) {
            double savingsRate = (balance / income) * 100;
            System.out.println("\n" + CYAN + "📊 Savings Rate: " + String.format("%.1f%%", savingsRate) + RESET);
            
            if (savingsRate >= 20) {
                printSuccess("🎉 शाबाश! Great job! You're saving more than 20%!");
            } else if (savingsRate < 0) {
                printWarning("⚠️ खर्च कम करें! You're spending more than you earn!");
            } else if (savingsRate < 10) {
                printWarning("📉 Try to increase your savings rate!");
            }
        }
        
        pressEnterToContinue();
    }
    
    private static void categoryBreakdown() {
        HashMap<String, Double> categories = new HashMap<>();
        double total = 0;
        
        for (Transaction t : transactions) {
            if (t.type.equals("EXPENSE")) {
                categories.put(t.category, categories.getOrDefault(t.category, 0.0) + t.amount);
                total += t.amount;
            }
        }
        
        if (categories.isEmpty()) {
            printWarning("No expenses yet!");
            pressEnterToContinue();
            return;
        }
        
        printSection("EXPENSE BREAKDOWN", PURPLE);
        
        System.out.println(PURPLE + "┌─────────────────────────────────────────────────┐" + RESET);
        System.out.println(PURPLE + "│" + RESET + "  Category                Amount     %     Progress   " + PURPLE + "│" + RESET);
        System.out.println(PURPLE + "├─────────────────────────────────────────────────┤" + RESET);
        
        // Sort categories by amount (highest first)
        ArrayList<Map.Entry<String, Double>> list = new ArrayList<>(categories.entrySet());
        list.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        
        for (Map.Entry<String, Double> entry : list) {
            double percent = (entry.getValue() / total) * 100;
            int barLength = (int)(percent / 2);
            
            System.out.printf(PURPLE + "│" + RESET + "  %-20s ₹" + YELLOW + "%-8.2f" + RESET + " %5.1f%% ", 
                truncate(entry.getKey(), 20), entry.getValue(), percent);
            
            System.out.print("[");
            for (int i = 0; i < barLength; i++) System.out.print(GREEN + "█" + RESET);
            for (int i = barLength; i < 25; i++) System.out.print("░");
            System.out.println("] " + PURPLE + "│" + RESET);
        }
        
        System.out.println(PURPLE + "└─────────────────────────────────────────────────┘" + RESET);
        System.out.printf("\n%s💰 Total Expenses: ₹%.2f%s\n", CYAN, total, RESET);
        
        pressEnterToContinue();
    }
    
    private static void searchTransactions() {
        if (transactions.isEmpty()) {
            printWarning("No transactions to search!");
            pressEnterToContinue();
            return;
        }
        
        printSection("SEARCH TRANSACTIONS", BLUE);
        
        System.out.println("Search by:");
        System.out.println("1. 🔍 Description");
        System.out.println("2. 📂 Category");
        System.out.println("3. 💰 Amount range");
        System.out.print("Choose option (1-3): ");
        
        int option;
        try {
            option = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            return;
        }
        
        ArrayList<Transaction> results = new ArrayList<>();
        
        switch (option) {
            case 1:
                System.out.print("Enter description to search: ");
                String desc = scanner.nextLine().toLowerCase();
                for (Transaction t : transactions) {
                    if (t.description.toLowerCase().contains(desc)) {
                        results.add(t);
                    }
                }
                break;
            case 2:
                System.out.print("Enter category: ");
                String cat = scanner.nextLine();
                for (Transaction t : transactions) {
                    if (t.category.equalsIgnoreCase(cat)) {
                        results.add(t);
                    }
                }
                break;
            case 3:
                System.out.print("Enter minimum amount: ₹");
                double min = scanner.nextDouble();
                System.out.print("Enter maximum amount: ₹");
                double max = scanner.nextDouble();
                scanner.nextLine();
                for (Transaction t : transactions) {
                    if (t.amount >= min && t.amount <= max) {
                        results.add(t);
                    }
                }
                break;
            default:
                printError("Invalid option!");
                return;
        }
        
        if (results.isEmpty()) {
            printWarning("No matching transactions found!");
        } else {
            System.out.println("\n" + GREEN + "Found " + results.size() + " matching transactions:" + RESET);
            for (Transaction t : results) {
                displayTransaction(t);
            }
        }
        
        pressEnterToContinue();
    }
    
    private static void displayTransaction(Transaction t) {
        String typeColor = t.type.equals("INCOME") ? GREEN : RED;
        System.out.println("\n" + CYAN + "┌──────────────────────────────────┐" + RESET);
        System.out.printf(CYAN + "│" + RESET + " %-30s " + CYAN + "│%n" + RESET, "📅 Date: " + t.date);
        System.out.println(CYAN + "├──────────────────────────────────┤" + RESET);
        System.out.printf(CYAN + "│" + RESET + " %-30s " + CYAN + "│%n" + RESET, "📌 Type: " + typeColor + t.type + RESET);
        System.out.printf(CYAN + "│" + RESET + " %-30s " + CYAN + "│%n" + RESET, "💰 Amount: ₹" + String.format("%.2f", t.amount));
        System.out.printf(CYAN + "│" + RESET + " %-30s " + CYAN + "│%n" + RESET, "📂 Category: " + t.category);
        System.out.printf(CYAN + "│" + RESET + " %-30s " + CYAN + "│%n" + RESET, "📝 Description: " + t.description);
        System.out.println(CYAN + "└──────────────────────────────────┘" + RESET);
    }
    
    private static void exportToFile() {
        if (transactions.isEmpty()) {
            printWarning("No transactions to export!");
            pressEnterToContinue();
            return;
        }
        
        printSection("EXPORT DATA", GREEN);
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "Expense_Report_India_" + timestamp + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("=".repeat(80));
            writer.println("                    🇮🇳 EXPENSE TRACKER PRO - INDIA REPORT 🇮🇳");
            writer.println("=".repeat(80));
            writer.println("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("=".repeat(80));
            
            double income = 0, expense = 0;
            
            writer.println("\nTRANSACTIONS:");
            writer.println("-".repeat(80));
            writer.printf("%-20s %-10s %-25s %-12s %s%n", "Date", "Type", "Category", "Amount (₹)", "Description");
            writer.println("-".repeat(80));
            
            for (Transaction t : transactions) {
                writer.printf("%-20s %-10s %-25s ₹%-11.2f %s%n", 
                    t.date, t.type, t.category, t.amount, t.description);
                
                if (t.type.equals("INCOME")) income += t.amount;
                else expense += t.amount;
            }
            
            writer.println("-".repeat(80));
            writer.println("\nSUMMARY:");
            writer.println("=".repeat(80));
            writer.printf("Total Income:   ₹%.2f%n", income);
            writer.printf("Total Expense:  ₹%.2f%n", expense);
            writer.printf("Balance:        ₹%.2f%n", income - expense);
            
            if (income > 0) {
                double savingsRate = ((income - expense) / income) * 100;
                writer.printf("Savings Rate:   %.1f%%%n", savingsRate);
            }
            
            writer.println("=".repeat(80));
            writer.println("\nCATEGORY WISE BREAKDOWN:");
            writer.println("-".repeat(80));
            
            HashMap<String, Double> categories = new HashMap<>();
            for (Transaction t : transactions) {
                if (t.type.equals("EXPENSE")) {
                    categories.put(t.category, categories.getOrDefault(t.category, 0.0) + t.amount);
                }
            }
            
            for (Map.Entry<String, Double> entry : categories.entrySet()) {
                double percent = (entry.getValue() / expense) * 100;
                writer.printf("%-25s ₹%-10.2f (%.1f%%)%n", entry.getKey(), entry.getValue(), percent);
            }
            
            writer.println("=".repeat(80));
            
            printSuccess("✅ Report exported successfully!");
            System.out.println(CYAN + "📁 Filename: " + filename + RESET);
            
        } catch (Exception e) {
            printError("Error exporting: " + e.getMessage());
        }
        
        pressEnterToContinue();
    }
    
    private static void printSection(String title, String color) {
        clearScreen();
        System.out.println("\n" + color + "╔" + "═".repeat(50) + "╗" + RESET);
        System.out.println(color + "║" + RESET + "          " + BOLD + title + RESET + "          ".repeat(Math.max(0, 5 - title.length()/8)) + color + "║" + RESET);
        System.out.println(color + "╚" + "═".repeat(50) + "╝" + RESET);
    }
    
    private static void printSuccess(String message) {
        System.out.println(GREEN + message + RESET);
    }
    
    private static void printError(String message) {
        System.out.println(RED + message + RESET);
    }
    
    private static void printWarning(String message) {
        System.out.println(YELLOW + message + RESET);
    }
    
    private static void printGoodbye() {
        clearScreen();
        System.out.println(PURPLE + "╔" + "═".repeat(50) + "╗" + RESET);
        System.out.println(PURPLE + "║" + RESET + "          " + BOLD + "धन्यवाद! THANK YOU FOR USING" + RESET + "         " + PURPLE + "║" + RESET);
        System.out.println(PURPLE + "║" + RESET + "          " + BOLD + "EXPENSE TRACKER PRO!" + RESET + "               " + PURPLE + "║" + RESET);
        System.out.println(PURPLE + "║" + RESET + "                                              " + PURPLE + "║" + RESET);
        System.out.println(PURPLE + "║" + RESET + "         " + CYAN + "💪 पैसे की बचत करें, अमीर बनें!" + RESET + "           " + PURPLE + "║" + RESET);
        System.out.println(PURPLE + "╚" + "═".repeat(50) + "╝" + RESET);
        System.out.println();
    }
    
    private static void pressEnterToContinue() {
        System.out.print("\n" + CYAN + "Press Enter to continue..." + RESET);
        scanner.nextLine();
    }
}