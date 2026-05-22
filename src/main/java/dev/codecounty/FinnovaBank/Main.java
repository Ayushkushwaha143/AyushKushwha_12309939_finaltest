package dev.codecounty.FinnovaBank;

import java.util.*;
import java.text.SimpleDateFormat;

public class Main {

    static HashMap<Integer, Customer> customers    = new HashMap<>();
    static ArrayList<Loan>            loans        = new ArrayList<>();
    static LinkedList<String>         transactions = new LinkedList<>();
    static TreeMap<Integer, Double>   loanReport   = new TreeMap<>();  // sorted by customer ID

    static Scanner sc = new Scanner(System.in);

    private static String timestamp() {
        return new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date());
    }

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("       WELCOME TO FINNOVA BANK");
        System.out.println("=========================================");
        System.out.println("Loading customer records...");
        customers = FileHandler.loadCustomers();

        System.out.println("Loading loan records...");
        loans = FileHandler.loadLoans();

        System.out.println("Loading transactions...");
        transactions = FileHandler.loadTransactions();

        for (Loan l : loans) {
            loanReport.put(l.getCustomerId(), l.getPrincipalAmount());
        }

        System.out.println("Data Loaded Successfully.\n");

        boolean running = true;
        while (running) {
            printMenu();
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1  -> addCustomer();
                    case 2  -> viewCustomers();
                    case 3  -> createLoan();
                    case 4  -> calculateSI();
                    case 5  -> calculateCI();
                    case 6  -> depositMoney();
                    case 7  -> withdrawMoney();
                    case 8  -> viewTransactions();
                    case 9  -> searchCustomer();
                    case 10 -> generateReport();
                    case 11 -> { running = false; exitApp(); }
                    default -> System.out.println("Invalid Choice.\nPlease Enter Between 1 - 11");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Choice.\nPlease Enter Between 1 - 11");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=========================================");
        System.out.println("              MAIN MENU");
        System.out.println("=========================================");
        System.out.println(" 1.  Add Customer");
        System.out.println(" 2.  View Customers");
        System.out.println(" 3.  Create Loan");
        System.out.println(" 4.  Calculate Simple Interest");
        System.out.println(" 5.  Calculate Compound Interest");
        System.out.println(" 6.  Deposit Money");
        System.out.println(" 7.  Withdraw Money");
        System.out.println(" 8.  View Transactions");
        System.out.println(" 9.  Search Customer");
        System.out.println(" 10. Generate Reports");
        System.out.println(" 11. Exit");
        System.out.println("=========================================");
        System.out.print("Enter your choice: ");
    }

    private static void addCustomer() {
        try {
            System.out.print("Enter Customer ID      : ");
            int id = Integer.parseInt(sc.nextLine().trim());

            if (customers.containsKey(id)) {
                System.out.println("ERROR: Customer ID " + id + " already exists.");
                return;
            }

            System.out.print("Enter Name             : ");
            String name = sc.nextLine().trim();

            System.out.print("Enter Phone Number     : ");
            String phone = sc.nextLine().trim();

            System.out.print("Enter Account Number   : ");
            int accNo = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter Initial Balance  : ");
            double balance = Double.parseDouble(sc.nextLine().trim());
            if (balance < 0) throw new InvalidAmountException("Balance cannot be negative.");

            System.out.print("Enter Monthly Salary   : ");
            double salary = Double.parseDouble(sc.nextLine().trim());
            if (salary < 0) throw new InvalidAmountException("Salary cannot be negative.");

            Customer c = new Customer(id, name, phone, accNo, balance, salary);
            customers.put(id, c);
            System.out.println("Customer Added Successfully.");

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input — please enter numeric values.");
        } catch (InvalidAmountException e) {
            System.out.println("ERROR: InvalidAmountException — " + e.getMessage());
        } finally {

        }
    }

    private static void viewCustomers() {
        System.out.println("\n=========================================");
        System.out.println("           CUSTOMER DETAILS");
        System.out.println("=========================================");
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer c : customers.values()) {
            c.displayDetails();
        }
    }

    private static void createLoan() {
        try {
            System.out.print("Enter Customer ID        : ");
            int custId = Integer.parseInt(sc.nextLine().trim());

            Customer c = customers.get(custId);
            if (c == null) throw new Exception("Customer Not Found.");

            System.out.print("Enter Loan ID            : ");
            String loanId = sc.nextLine().trim();

            System.out.print("Enter Loan Type          : ");
            String loanType = sc.nextLine().trim();

            System.out.print("Enter Principal Amount   : ");
            double principal = Double.parseDouble(sc.nextLine().trim());
            if (principal <= 0) throw new InvalidAmountException("Principal must be positive.");

            System.out.print("Enter Rate Of Interest   : ");
            double rate = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter Time (Years)       : ");
            int time = Integer.parseInt(sc.nextLine().trim());

            double eligible = c.getMaxLoanEligibility();
            System.out.println();
            System.out.printf("Salary         = \u20B9%.0f%n", c.getSalary());
            System.out.printf("Eligible Loan  = \u20B9%.0f%n", eligible);

            if (principal > eligible) {
                throw new InvalidLoanException(
                        "Requested loan \u20B9" + (int) principal
                                + " exceeds eligible limit \u20B9" + (int) eligible);
            }

            System.out.println("Loan Approved.");

            Loan loan = new Loan(loanId, custId, loanType, principal, rate, time);
            loans.add(loan);

            c.setLoanTaken(true);
            customers.put(custId, c);

            loanReport.put(custId, principal);

            String entry = "[" + timestamp() + "] Loan Created For Customer " + custId
                    + " | Loan Amount = \u20B9" + (int) principal;
            transactions.add(entry);
            FileHandler.appendTransaction(entry);

            System.out.println("Loan Created Successfully.");

        } catch (InvalidLoanException e) {
            System.out.println("ERROR: InvalidLoanException — " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("ERROR: InvalidAmountException — " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input format.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        } finally {

        }
    }

    private static void calculateSI() {
        try {
            System.out.print("Enter Principal Amount : ");
            double p = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter Rate (%)         : ");
            double r = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter Time (Years)     : ");
            int t = Integer.parseInt(sc.nextLine().trim());

            if (p <= 0 || r <= 0 || t <= 0)
                throw new InvalidAmountException("All values must be positive.");

            // reuse Loan class methods
            Loan temp = new Loan("TEMP", 0, "Calc", p, r, t);
            double si = temp.calculateSimpleInterest();

            System.out.printf("%nSimple Interest = \u20B9%.2f%n", si);
            System.out.printf("Total Amount    = \u20B9%.2f%n", p + si);

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input.");
        } catch (InvalidAmountException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("ERROR: Arithmetic Exception — " + e.getMessage());
        }
    }

    private static void calculateCI() {
        try {
            System.out.print("Enter Principal Amount : ");
            double p = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter Rate (%)         : ");
            double r = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter Time (Years)     : ");
            int t = Integer.parseInt(sc.nextLine().trim());

            if (p <= 0 || r <= 0 || t <= 0)
                throw new InvalidAmountException("All values must be positive.");

            Loan temp = new Loan("TEMP", 0, "Calc", p, r, t);
            double ci = temp.calculateCompoundInterest();

            System.out.printf("%nCompound Interest = \u20B9%.2f%n", ci);
            System.out.printf("Total Amount      = \u20B9%.2f%n", p + ci);

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input.");
        } catch (InvalidAmountException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("ERROR: Arithmetic Exception — " + e.getMessage());
        }
    }

    private static void depositMoney() {
        try {
            System.out.print("Enter Account Number   : ");
            int accNo = Integer.parseInt(sc.nextLine().trim());

            Customer c = findByAccount(accNo);
            if (c == null) throw new Exception("Account " + accNo + " not found.");

            System.out.print("Enter Deposit Amount   : ");
            double amount = Double.parseDouble(sc.nextLine().trim());

            BankAccount ba = new BankAccount(accNo, c.getBalance());
            ba.deposit(amount);   // throws InvalidAmountException

            c.setBalance(ba.getBalance());
            customers.put(c.getPersonId(), c);

            String entry = "[" + timestamp() + "] Account " + accNo
                    + " Deposited \u20B9" + (int) amount;
            transactions.add(entry);
            FileHandler.appendTransaction(entry);

        } catch (InvalidAmountException e) {
            System.out.println("ERROR: InvalidAmountException — " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input format.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        } finally {

        }
    }

    private static void withdrawMoney() {
        try {
            System.out.print("Enter Account Number   : ");
            int accNo = Integer.parseInt(sc.nextLine().trim());

            Customer c = findByAccount(accNo);
            if (c == null) throw new Exception("Account " + accNo + " not found.");

            System.out.print("Enter Withdraw Amount  : ");
            double amount = Double.parseDouble(sc.nextLine().trim());

            BankAccount ba = new BankAccount(accNo, c.getBalance());
            ba.withdraw(amount);  // throws InvalidAmountException or InsufficientBalanceException

            c.setBalance(ba.getBalance());
            customers.put(c.getPersonId(), c);

            String entry = "[" + timestamp() + "] Account " + accNo
                    + " Withdrawn \u20B9" + (int) amount;
            transactions.add(entry);
            FileHandler.appendTransaction(entry);

        } catch (InsufficientBalanceException e) {
            System.out.println("ERROR: Insufficient Balance Exception");
            System.out.println(e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("ERROR: InvalidAmountException — " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input format.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        } finally {

        }
    }

    private static void viewTransactions() {
        System.out.println("\n=========================================");
        System.out.println("         TRANSACTION HISTORY");
        System.out.println("=========================================");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        for (String t : transactions) {
            System.out.println(t);
        }
    }

    private static void searchCustomer() {
        try {
            System.out.print("Enter Customer ID : ");
            int id = Integer.parseInt(sc.nextLine().trim());

            Customer c = customers.get(id);
            if (c == null) throw new Exception("Customer Not Found");

            System.out.println("Customer Found");
            c.displayDetails();

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void generateReport() {
        System.out.println("\n=========================================");
        System.out.println("             BANK REPORT");
        System.out.println("=========================================");

        double totalLoans    = 0;
        double highestLoan   = 0;
        double totalInterest = 0;
        String highestName   = "N/A";

        for (Loan l : loans) {
            totalLoans   += l.getPrincipalAmount();
            totalInterest += l.calculateCompoundInterest();
            if (l.getPrincipalAmount() > highestLoan) {
                highestLoan = l.getPrincipalAmount();
                Customer c  = customers.get(l.getCustomerId());
                highestName = (c != null) ? c.getName() : "Unknown";
            }
        }

        double totalBalance = 0;
        for (Customer c : customers.values()) {
            totalBalance += c.getBalance();
        }

        System.out.println("Total Customers              : " + customers.size());
        System.out.printf( "Total Loans Issued           : \u20B9%.0f%n", totalLoans);
        System.out.printf( "Highest Loan Amount          : \u20B9%.0f%n", highestLoan);
        System.out.printf( "Total Bank Balance           : \u20B9%.0f%n", totalBalance);
        System.out.printf( "Interest Generated           : \u20B9%.2f%n", totalInterest);
        System.out.println("Customer With Highest Loan   : " + highestName);

        if (!loanReport.isEmpty()) {
            System.out.println("\n--- Sorted Loan Report (by Customer ID) ---");
            for (Map.Entry<Integer, Double> entry : loanReport.entrySet()) {
                System.out.printf("Customer %-5d  =>  \u20B9%.0f%n",
                        entry.getKey(), entry.getValue());
            }
        }
    }

    private static void exitApp() {
        System.out.println("\nSaving customer data...");
        FileHandler.saveCustomers(customers);

        System.out.println("Saving loan data...");
        FileHandler.saveLoans(loans);

        System.out.println("Saving transaction history...");
        FileHandler.saveTransactions(transactions);

        System.out.println("Data Saved Successfully.");
        System.out.println("\nThank You For Using FinNova Bank");
    }

    private static Customer findByAccount(int accNo) {
        for (Customer c : customers.values()) {
            if (c.getAccountNumber() == accNo) return c;
        }
        return null;
    }
}