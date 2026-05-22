package dev.codecounty.FinnovaBank;

public class BankAccount {

    private int    accountNumber;
    private double balance;

    public BankAccount(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance       = balance;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount cannot be negative or zero.");
        }
        balance += amount;
        System.out.printf("\u20B9%.0f Deposited Successfully.%n", amount);
        System.out.printf("Updated Balance = \u20B9%.2f%n", balance);
    }

    public void withdraw(double amount)
            throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount cannot be negative or zero.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Cannot withdraw amount greater than balance.");
        }
        balance -= amount;
        System.out.printf("\u20B9%.0f Withdrawn Successfully.%n", amount);
        System.out.printf("Remaining Balance = \u20B9%.2f%n", balance);
    }

    public void checkBalance() {
        System.out.printf("Current Balance : \u20B9%.2f%n", balance);
    }

    public int    getAccountNumber()            { return accountNumber; }
    public double getBalance()                  { return balance; }
    public void   setBalance(double balance)    { this.balance = balance; }
}