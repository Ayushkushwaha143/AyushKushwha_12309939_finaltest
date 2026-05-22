package dev.codecounty.FinnovaBank;

public class Loan {

    private String loanId;
    private int    customerId;
    private String loanType;
    private double principalAmount;
    private double rateOfInterest;
    private int    timeInYears;

    public Loan(String loanId, int customerId, String loanType,
                double principalAmount, double rateOfInterest, int timeInYears) {
        this.loanId          = loanId;
        this.customerId      = customerId;
        this.loanType        = loanType;
        this.principalAmount = principalAmount;
        this.rateOfInterest  = rateOfInterest;
        this.timeInYears     = timeInYears;
    }

    public double calculateSimpleInterest() {
        return (principalAmount * rateOfInterest * timeInYears) / 100;
    }

    public double calculateCompoundInterest() {
        double amount = principalAmount * Math.pow(1 + rateOfInterest / 100.0, timeInYears);
        return amount - principalAmount;
    }

    public void displayLoan() {
        System.out.println("-----------------------------------------");
        System.out.println("Loan ID      : " + loanId);
        System.out.println("Customer ID  : " + customerId);
        System.out.println("Loan Type    : " + loanType);
        System.out.printf( "Principal    : \u20B9%.0f%n",  principalAmount);
        System.out.println("Rate         : " + rateOfInterest + "%");
        System.out.println("Time         : " + timeInYears + " Years");
        System.out.printf( "SI           : \u20B9%.2f%n",  calculateSimpleInterest());
        System.out.printf( "CI           : \u20B9%.2f%n",  calculateCompoundInterest());
        System.out.println("-----------------------------------------");
    }

    public String toCSV() {
        return loanId + "," + customerId + "," + loanType + ","
                + principalAmount + "," + rateOfInterest + "," + timeInYears;
    }

    public String getLoanId()                               { return loanId; }
    public void   setLoanId(String loanId)                  { this.loanId = loanId; }

    public int    getCustomerId()                           { return customerId; }
    public void   setCustomerId(int customerId)             { this.customerId = customerId; }

    public String getLoanType()                             { return loanType; }
    public void   setLoanType(String loanType)              { this.loanType = loanType; }

    public double getPrincipalAmount()                      { return principalAmount; }
    public void   setPrincipalAmount(double principalAmount){ this.principalAmount = principalAmount; }

    public double getRateOfInterest()                       { return rateOfInterest; }
    public void   setRateOfInterest(double rate)            { this.rateOfInterest = rate; }

    public int    getTimeInYears()                          { return timeInYears; }
    public void   setTimeInYears(int timeInYears)           { this.timeInYears = timeInYears; }
}