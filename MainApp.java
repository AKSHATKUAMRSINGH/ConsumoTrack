import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;



public class MainApp extends JFrame {
    private LoginManager loginManager = new LoginManager();
    private ExpenseTracker expenseTracker = new ExpenseTracker();
    private SavingsTracker savingsTracker = new SavingsTracker();
    private PurchaseManager purchaseManager = new PurchaseManager();
    private NotificationManager notificationManager = new NotificationManager();
    private RealTimeBudget realTimeBudget = new RealTimeBudget(10000); // Initial budget
    private ReminderManager reminderManager = new ReminderManager();
    private ConsumptionAnalysis consumptionAnalysis = new ConsumptionAnalysis();
    private ItemCategorizer itemCategorizer = new ItemCategorizer();
    private GoalSetting goalSetting = new GoalSetting();
    private ShoppingList shoppingList = new ShoppingList();
    private RewardsManager rewardsManager = new RewardsManager();
    private AnalyticsDashboard analyticsDashboard = new AnalyticsDashboard();
    private CategoryManagement categoryManagement = new CategoryManagement();

    private CardLayout cardLayout = new CardLayout(); // For smooth transitions between screens
    private JPanel mainPanel = new JPanel(cardLayout);

    // Constructor to initialize the main application window
    public MainApp() {
        setTitle("ConsumoTrack");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on screen

        mainPanel.add(createTitlePanel("Welcome to ConsumoTrack"), "TITLE");
        mainPanel.add(createRegistrationPanel(), "REGISTER");
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createMainMenuPanel(), "MAIN_MENU");

        add(mainPanel);
        cardLayout.show(mainPanel, "TITLE");
    }

    // Method to create a title panel with a welcome message
    private JPanel createTitlePanel(String message) {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel(message, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Get Started");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(60, 179, 113));
        startButton.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER"));

        titlePanel.add(startButton, BorderLayout.SOUTH);
        return titlePanel;
    }

    // Method to create the registration screen
    private JPanel createRegistrationPanel() {
        JPanel registrationPanel = new JPanel(new BorderLayout());
        registrationPanel.setBackground(new Color(255, 255, 255));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(new Color(255, 255, 255));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        formPanel.add(new JLabel("New Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("New Password:"));
        formPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(60, 179, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (loginManager.registerUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration Successful! Please log in.");
                cardLayout.show(mainPanel, "LOGIN");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists. Try a different one.");
            }
        });

        registrationPanel.add(new JLabel("Register Account", SwingConstants.CENTER), BorderLayout.NORTH);
        registrationPanel.add(formPanel, BorderLayout.CENTER);
        registrationPanel.add(registerButton, BorderLayout.SOUTH);
        return registrationPanel;
    }

    // Method to create the login screen
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(new Color(255, 255, 255));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(new Color(255, 255, 255));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(60, 179, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (loginManager.loginUser(username, password)) {
                cardLayout.show(mainPanel, "MAIN_MENU");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.");
            }
        });

        loginPanel.add(new JLabel("Login to Account", SwingConstants.CENTER), BorderLayout.NORTH);
        loginPanel.add(formPanel, BorderLayout.CENTER);
        loginPanel.add(loginButton, BorderLayout.SOUTH);
        return loginPanel;
    }

    // Method to display the main menu
    private JPanel createMainMenuPanel() {
        JPanel mainMenuPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        mainMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainMenuPanel.setBackground(new Color(245, 245, 245));

        JButton expenseButton = new JButton("Expense Tracker");
        JButton purchaseButton = new JButton("Purchase Manager");
        JButton notificationButton = new JButton("Notification Manager");
        JButton budgetButton = new JButton("Real-Time Budget");
        JButton reminderButton = new JButton("Reminder Manager");
        JButton consumptionButton = new JButton("Consumption Analysis");
        JButton categorizerButton = new JButton("Item Categorizer");
        JButton goalButton = new JButton("Goal Setting");
        JButton savingsButton = new JButton("Savings Tracker");
        JButton shoppingButton = new JButton("Shopping List");
        JButton rewardsButton = new JButton("Rewards Manager");
        JButton analyticsButton = new JButton("Analytics Dashboard");
        JButton exitButton = new JButton("Exit");

        // Adding Action Listeners to each button
        styleButton(expenseButton, e -> showExpenseTracker());
        styleButton(purchaseButton, e -> showPurchaseManager());
        styleButton(notificationButton, e -> showNotificationManager());
        styleButton(budgetButton, e -> adjustBudget());
        styleButton(reminderButton, e -> showReminderManager());
        styleButton(consumptionButton, e -> showConsumptionAnalysis());
        styleButton(categorizerButton, e -> showItemCategorizer());
        styleButton(goalButton, e -> showGoalSetting());
        styleButton(savingsButton, e -> addSavings());
        styleButton(shoppingButton, e -> showShoppingList());
        styleButton(rewardsButton, e -> showRewardsManager());
        styleButton(analyticsButton, e -> showAnalyticsDashboard());
        styleButton(exitButton, e -> System.exit(0));

        mainMenuPanel.add(new JLabel("Main Menu", SwingConstants.CENTER));
        mainMenuPanel.add(expenseButton);
        mainMenuPanel.add(purchaseButton);
        mainMenuPanel.add(notificationButton);
        mainMenuPanel.add(budgetButton);
        mainMenuPanel.add(reminderButton);
        mainMenuPanel.add(consumptionButton);
        mainMenuPanel.add(categorizerButton);
        mainMenuPanel.add(goalButton);
        mainMenuPanel.add(savingsButton);
        mainMenuPanel.add(shoppingButton);
        mainMenuPanel.add(rewardsButton);
        mainMenuPanel.add(analyticsButton);
        mainMenuPanel.add(exitButton);
        return mainMenuPanel;
    }

    // Method to style buttons
    private void styleButton(JButton button, ActionListener actionListener) {
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.addActionListener(actionListener);
    }

    // Show the Expense Tracker
  

    private void showExpenseTracker() {
        String input = JOptionPane.showInputDialog(this, "Enter expense amount:");
        if (input != null && !input.isEmpty()) {
            try {
                double amount = Double.parseDouble(input);
                expenseTracker.addExpense(loginManager.getCurrentUserId(), amount); // Pass user ID
                JOptionPane.showMessageDialog(this, "Expense added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            }
        }
    }
    

    // Show the Purchase Manager
    private void showPurchaseManager() {
        String category = JOptionPane.showInputDialog(this, "Enter category:");
        String item = JOptionPane.showInputDialog(this, "Enter item:");
        if (category != null && item != null) {
            purchaseManager.addPurchase(category, item);
            JOptionPane.showMessageDialog(this, "Purchase added successfully!");
        }
    }

    // Show the Notification Manager
    private void showNotificationManager() {
        String message = JOptionPane.showInputDialog(this, "Enter notification message:");
        if (message != null) {
            notificationManager.addNotification(message);
            JOptionPane.showMessageDialog(this, "Notification added successfully!");
        }
    }

    // Adjust Budget based on Expenses
    private void adjustBudget() {
        double expenses = expenseTracker.getTotalExpenses(loginManager.getCurrentUserId()); // Pass user ID
        realTimeBudget.updateBudget(expenses);
        JOptionPane.showMessageDialog(this, "Remaining Budget: " + realTimeBudget.getRemainingBudget());
    }
    // Show the Reminder Manager
    private void showReminderManager() {
        String reminder = JOptionPane.showInputDialog(this, "Enter reminder:");
        if (reminder != null) {
            reminderManager.addReminder(reminder);
            JOptionPane.showMessageDialog(this, "Reminder added successfully!");
        }
    }


    // Show the Consumption Analysis
    private void showConsumptionAnalysis() {
        int userId = loginManager.getCurrentUserId(); // Get the current user ID
        List<Double> userExpenses = expenseTracker.getAllExpenses(userId); // Get expenses for the user
    
        // Check if there are any expenses
        if (userExpenses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No expenses recorded for this user.");
            return;
        }
    
        int count = expenseTracker.getNumberOfExpenses(userId);
        double total = expenseTracker.getTotalExpenses(userId);
        double average = (count > 0) ? (total / count) : 0; // Avoid division by zero
     // Use user ID for count
    
        JOptionPane.showMessageDialog(this, "Total Consumption: " + total + "\nAverage Consumption: " + average);
    }
    

    // Show the Item Categorizer
    private void showItemCategorizer() {
        String item = JOptionPane.showInputDialog(this, "Enter item:");
        String category = JOptionPane.showInputDialog(this, "Enter category:");
        if (item != null && category != null) {
            itemCategorizer.addItem(item, category);
            JOptionPane.showMessageDialog(this, "Item categorized successfully!");
        }
    }

    // Show the Goal Setting
    private void showGoalSetting() {
        String goal = JOptionPane.showInputDialog(this, "Enter financial goal:");
        if (goal != null) {
            goalSetting.setGoal(goal);
            JOptionPane.showMessageDialog(this, "Goal set successfully!");
        }
    }

    // Add Savings
    private void addSavings() {
        String input = JOptionPane.showInputDialog(this, "Enter savings amount:");
        if (input != null && !input.isEmpty()) {
            try {
                double amount = Double.parseDouble(input);
                savingsTracker.addSavings(amount, loginManager.getCurrentUserId());
                JOptionPane.showMessageDialog(this, "Savings added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            }
        }
    }

    // Show the Shopping List
    private void showShoppingList() {
        String item = JOptionPane.showInputDialog(this, "Enter shopping item:");
        if (item != null) {
            shoppingList.addItem(item);
            JOptionPane.showMessageDialog(this, "Item added to shopping list!");
        }
    }

    // Show the Rewards Manager
    private void showRewardsManager() {
        String reward = JOptionPane.showInputDialog(this, "Enter reward:");
        if (reward != null) {
            rewardsManager.addReward(reward);
            JOptionPane.showMessageDialog(this, "Reward added successfully!");
        }
    }

    // Show the Analytics Dashboard
    private void showAnalyticsDashboard() {
        String report = analyticsDashboard.generateReport(expenseTracker, realTimeBudget);
        JOptionPane.showMessageDialog(this, report);
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.setVisible(true);
        });
    }
}


class LoginManager {
    private static int currentUserId; // Store the current user's ID

    public boolean registerUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = THE_Connection.getTheConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true; // Registration successful
        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
            return false; // Registration failed
        }
    }

    public boolean loginUser(String username, String password) {
        String query = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (Connection conn = THE_Connection.getTheConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                currentUserId = rs.getInt("id"); // Get user ID
                return true; // Login successful
            }
            return false; // Login failed
        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
            return false; // Login failed
        }
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }
}


class ExpenseTracker {
    public void addExpense(int userId, double amount) {
        String query = "INSERT INTO expenses (user_id, amount) VALUES (?, ?)";
        try (Connection conn = THE_Connection.getTheConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
        }
    }

    public double getTotalExpenses(int userId) {
        String query = "SELECT SUM(amount) FROM expenses WHERE user_id = ?";
        try (Connection conn = THE_Connection.getTheConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1); // Return total expenses
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
        }
        return 0; // No expenses found
    }

    public List<Double> getAllExpenses(int userId) {
        List<Double> expenses = new ArrayList<>();
        String query = "SELECT amount FROM expenses WHERE user_id = ?";
        try (Connection conn = THE_Connection.getTheConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                expenses.add(rs.getDouble("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
        }
        return expenses; // Return all expenses
    }

    public int getNumberOfExpenses(int userId) {
        String query = "SELECT COUNT(*) FROM expenses WHERE user_id = ?";
        try (Connection conn = THE_Connection.getTheConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Return number of expenses
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
        }
        return 0; // No expenses found
    }
}


// Class for managing savings
class SavingsTracker {
    private double totalSavings = 0;

    public void addSavings(double amount, int userId) {
        String query = "INSERT INTO savings (user_id, amount) VALUES (?, ?)";
        try (Connection conn = THE_Connection.getTheConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
            totalSavings += amount;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getTotalSavings(int userId) {
        String query = "SELECT SUM(amount) FROM savings WHERE user_id = ?";
        try (Connection conn = THE_Connection.getTheConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

// Class for managing purchases
class PurchaseManager {
    public void addPurchase(String category, String item) {
        String query = "INSERT INTO purchases (category, item) VALUES (?, ?)";
        try (Connection conn = THE_Connection.getTheConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category);
            stmt.setString(2, item);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
        }
    }
}

// Class for managing notifications
class NotificationManager {
    public void addNotification(String message) {
        String query = "INSERT INTO notifications (message) VALUES (?)";
        try (Connection conn = THE_Connection.getTheConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
        }
    }
}


// Class for managing the real-time budget
class RealTimeBudget {
    private double initialBudget;
    private double remainingBudget;

    public RealTimeBudget(double initialBudget) {
        this.initialBudget = initialBudget;
        this.remainingBudget = initialBudget;
    }

    public void updateBudget(double expenses) {
        remainingBudget -= expenses;
    }

    public double getRemainingBudget() {
        return remainingBudget;
    }
}

// Class for managing reminders
class ReminderManager {
    private List<String> reminders = new ArrayList<>();

    public void addReminder(String reminder) {
        reminders.add(reminder);
    }

    public List<String> getReminders() {
        return reminders;
    }
}

// Class for consumption analysis
class ConsumptionAnalysis {
   
        public double analyzeConsumption(List<Double> expenses) {
            double total = 0;
            for (double expense : expenses) {
                total += expense;
            }
            return total; // Return total consumption
        }
    }
    


// Class for categorizing items
class ItemCategorizer {
    private Map<String, List<String>> categorizedItems = new HashMap<>();

    public void addItem(String item, String category) {
        categorizedItems.putIfAbsent(category, new ArrayList<>());
        categorizedItems.get(category).add(item);
    }

    public Map<String, List<String>> getCategorizedItems() {
        return categorizedItems;
    }
}

// Class for setting goals
class GoalSetting {
    private String goal;

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getGoal() {
        return goal;
    }
}

// Class for managing the shopping list
class ShoppingList {
    private List<String> shoppingItems = new ArrayList<>();

    public void addItem(String item) {
        shoppingItems.add(item);
    }

    public List<String> getShoppingItems() {
        return shoppingItems;
    }
}

// Class for managing rewards
class RewardsManager {
    private List<String> rewards = new ArrayList<>();

    public void addReward(String reward) {
        rewards.add(reward);
    }

    public List<String> getRewards() {
        return rewards;
    }
}

// Class for generating an analytics dashboard
class AnalyticsDashboard {
    public String generateReport(ExpenseTracker expenseTracker, RealTimeBudget realTimeBudget) {
        double totalExpenses = expenseTracker.getTotalExpenses(LoginManager.getCurrentUserId());
        double remainingBudget = realTimeBudget.getRemainingBudget();
        return String.format("Total Expenses: %.2f%nRemaining Budget: %.2f%n", totalExpenses, remainingBudget);
    }
}

// Class for managing categories
class CategoryManagement {
    private Map<String, List<String>> categories = new HashMap<>();

    public void addCategory(String category, List<String> items) {
        categories.put(category, items);
    }

    public Map<String, List<String>> getCategories() {
        return categories;
    }
}