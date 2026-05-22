package dev.codecounty.FinnovaBank;

public class Customer extends Person {

    public static int totalCustomers = 0;

    private int     accountNumber;
    private double  balance;
    private boolean loanTaken;
    private double  salary;


    public Customer(int personId, String name, String phoneNumber,
                    int accountNumber, double balance, double salary, boolean loanTaken) {
        super(personId, name, phoneNumber);
        this.accountNumber = accountNumber;
        this.balance       = balance;
        this.salary        = salary;
        this.loanTaken     = loanTaken;
        totalCustomers++;
    }

    public Customer(int personId, String name, String phoneNumber,
                    int accountNumber, double balance, double salary) {
        this(personId, name, phoneNumber, accountNumber, balance, salary, false);
    }

    @Override
    public void displayDetails() {
        System.out.println("-----------------------------------------");
        System.out.println("Customer ID  : " + getPersonId());
        System.out.println("Name         : " + getName());
        System.out.println("Phone        : " + getPhoneNumber());
        System.out.println("Account No   : " + accountNumber);
        System.out.printf( "Balance      : \u20B9%.0f%n", balance);
        System.out.printf( "Salary       : \u20B9%.0f%n", salary);
        System.out.println("Loan Taken   : " + loanTaken);
        System.out.println("-----------------------------------------");
    }

    public double getMaxLoanEligibility() {
        if      (salary < 25000)  return 200000;
        else if (salary <= 50000) return 500000;
        else                      return 1000000;
    }

    public String toCSV() {
        return getPersonId() + "," + getName() + "," + getPhoneNumber() + ","
                + accountNumber + "," + balance   + "," + salary + "," + loanTaken;
    }

    public int     getAccountNumber()               { return accountNumber; }
    public void    setAccountNumber(int acc)        { this.accountNumber = acc; }

    public double  getBalance()                     { return balance; }
    public void    setBalance(double balance)       { this.balance = balance; }

    public boolean isLoanTaken()                    { return loanTaken; }
    public void    setLoanTaken(boolean loanTaken)  { this.loanTaken = loanTaken; }

    public double  getSalary()                      { return salary; }
    public void    setSalary(double salary)         { this.salary = salary; }
}