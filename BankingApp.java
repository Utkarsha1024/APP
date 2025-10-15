import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The main application class for the banking system.
 * It provides a command-line interface for users to interact with the bank.
 */
public class BankingApp {

    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("=========================================");
        System.out.println("  Welcome to the Simple Banking System!  ");
        System.out.println("=========================================");

        while (!exit) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        createAccount(scanner, bank);
                        break;
                    case 2:
                        performDeposit(scanner, bank);
                        break;
                    case 3:
                        performWithdrawal(scanner, bank);
                        break;
                    case 4:
                        checkBalance(scanner, bank);
                        break;
                    case 5:
                        displayAccountDetails(scanner, bank);
                        break;
                    case 6:
                        exit = true;
                        System.out.println("Thank you for using our banking system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option (1-6).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
            System.out.println(); // Add a blank line for better readability
        }
        scanner.close();
    }

    /**
     * Prints the main menu of operations to the console.
     */
    private static void printMenu() {
        System.out.println("\nPlease choose an option:");
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Funds");
        System.out.println("3. Withdraw Funds");
        System.out.println("4. Check Balance");
        System.out.println("5. Display Account Details");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Handles the logic for creating a new bank account.
     */
    private static void createAccount(Scanner scanner, Bank bank) {
        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine();
        double initialDeposit = -1;

        while (initialDeposit < 0) {
            System.out.print("Enter Initial Deposit Amount: ");
            try {
                initialDeposit = scanner.nextDouble();
                if (initialDeposit < 0) {
                    System.out.println("Initial deposit cannot be negative. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
                scanner.next(); // clear the invalid input
            }
        }
        scanner.nextLine(); // Consume newline
        bank.createAccount(name, initialDeposit);
    }

    /**
     * Handles the logic for depositing funds into an account.
     */
    private static void performDeposit(Scanner scanner, Bank bank) {
        BankAccount account = findAccount(scanner, bank);
        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            try {
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                account.deposit(amount);
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    /**
     * Handles the logic for withdrawing funds from an account.
     */
    private static void performWithdrawal(Scanner scanner, Bank bank) {
        BankAccount account = findAccount(scanner, bank);
        if (account != null) {
            System.out.print("Enter amount to withdraw: ");
            try {
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                account.withdraw(amount);
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    /**
     * Handles the logic for checking an account's balance.
     */
    private static void checkBalance(Scanner scanner, Bank bank) {
        BankAccount account = findAccount(scanner, bank);
        if (account != null) {
            System.out.printf("Current Balance: $%.2f%n", account.getBalance());
        }
    }

    /**
     * Handles the logic for displaying full account details.
     */
    private static void displayAccountDetails(Scanner scanner, Bank bank) {
        BankAccount account = findAccount(scanner, bank);
        if (account != null) {
            System.out.println(account);
        }
    }

    /**
     * Helper method to find an account by prompting for the account number.
     */
    private static BankAccount findAccount(Scanner scanner, Bank bank) {
        System.out.print("Enter Account Number (e.g., ACC1001): ");
        String accNum = scanner.nextLine();
        BankAccount account = bank.getAccount(accNum);
        if (account == null) {
            System.out.println("Account not found. Please check the account number.");
        }
        return account;
    }
}

/**
 * Manages a collection of BankAccount objects.
 * This class handles operations like creating accounts and finding them.
 */
class Bank {
    // A map to store accounts, with the account number as the key.
    private Map<String, BankAccount> accounts;
    // An atomic integer to generate unique account numbers safely.
    private static final AtomicInteger accountNumberGenerator = new AtomicInteger(1000);

    /**
     * Constructor to initialize the Bank object.
     */
    public Bank() {
        this.accounts = new HashMap<>();
    }

    /**
     * Creates a new bank account and adds it to the bank's records.
     * @param accountHolderName The name of the new account holder.
     * @param initialDeposit The initial amount to deposit into the new account.
     * @return The newly created BankAccount object.
     */
    public BankAccount createAccount(String accountHolderName, double initialDeposit) {
        // Generate a new unique account number.
        String newAccountNumber = "ACC" + accountNumberGenerator.incrementAndGet();

        BankAccount newAccount = new BankAccount(newAccountNumber, accountHolderName, initialDeposit);
        accounts.put(newAccountNumber, newAccount);

        System.out.println("Account created successfully for " + accountHolderName + " with account number " + newAccountNumber);
        return newAccount;
    }

    /**
     * Finds and returns a bank account based on the account number.
     * @param accountNumber The number of the account to find.
     * @return The BankAccount object if found, otherwise null.
     */
    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}

/**
 * Represents a single bank account.
 * This class encapsulates the account's properties and operations.
 */
class BankAccount {
    // --- Private Attributes ---
    // Encapsulation: Data is hidden and can only be accessed through methods.
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    /**
     * Constructor to initialize a BankAccount object.
     * @param accountNumber The unique number for the account.
     * @param accountHolderName The name of the account holder.
     * @param initialBalance The starting balance of the account.
     */
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    // --- Public Getters ---
    // Provides read-only access to account properties.
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Deposits a specified amount into the account.
     * The amount must be positive.
     * @param amount The amount to deposit.
     * @return true if the deposit was successful, false otherwise.
     */
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. New balance: " + balance);
            return true;
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive value.");
            return false;
        }
    }

    /**
     * Withdraws a specified amount from the account.
     * The amount must be positive and not exceed the current balance.
     * @param amount The amount to withdraw.
     * @return true if the withdrawal was successful, false otherwise.
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount. Please enter a positive value.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal failed.");
            return false;
        }
        balance -= amount;
        System.out.println("Withdrawal successful. New balance: " + balance);
        return true;
    }

    /**
     * Returns a string representation of the account details.
     * Overriding the toString() method for a custom object representation.
     * @return A formatted string with account information.
     */
    @Override
    public String toString() {
        return "----------------------------------\n" +
                "Account Number: " + accountNumber + "\n" +
                "Account Holder: " + accountHolderName + "\n" +
                "Balance: $" + String.format("%.2f", balance) + "\n" +
                "----------------------------------";
    }
}

