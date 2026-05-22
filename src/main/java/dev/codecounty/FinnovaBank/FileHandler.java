package dev.codecounty.FinnovaBank;

import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String CUSTOMERS_FILE    = "customers.txt";
    private static final String LOANS_FILE        = "loans.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public static HashMap<Integer, Customer> loadCustomers() {
        HashMap<Integer, Customer> map = new HashMap<>();
        File file = new File(CUSTOMERS_FILE);

        if (!file.exists()) {
            System.out.println("ERROR: customers.txt file not found.");
            System.out.println("Creating new file...");
            try { file.createNewFile(); }
            catch (IOException e) { System.out.println("Could not create customers.txt"); }
            return map;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                // CSV: personId,name,phone,accountNumber,balance,salary,loanTaken
                String[] p = line.split(",");
                if (p.length < 7) continue;
                int     id      = Integer.parseInt(p[0].trim());
                String  name    = p[1].trim();
                String  phone   = p[2].trim();
                int     accNo   = Integer.parseInt(p[3].trim());
                double  balance = Double.parseDouble(p[4].trim());
                double  salary  = Double.parseDouble(p[5].trim());
                boolean loan    = Boolean.parseBoolean(p[6].trim());
                map.put(id, new Customer(id, name, phone, accNo, balance, salary, loan));
            }
        } catch (IOException e) {
            System.out.println("Error reading customers.txt: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Corrupted data in customers.txt: " + e.getMessage());
        }
        return map;
    }

    public static void saveCustomers(HashMap<Integer, Customer> customers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMERS_FILE, false))) {
            for (Customer c : customers.values()) {
                bw.write(c.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    public static ArrayList<Loan> loadLoans() {
        ArrayList<Loan> list = new ArrayList<>();
        File file = new File(LOANS_FILE);

        if (!file.exists()) {
            System.out.println("ERROR: loans.txt file not found.");
            System.out.println("Creating new file...");
            try { file.createNewFile(); }
            catch (IOException e) { System.out.println("Could not create loans.txt"); }
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split(",");
                if (p.length < 6) continue;
                String loanId    = p[0].trim();
                int    custId    = Integer.parseInt(p[1].trim());
                String loanType  = p[2].trim();
                double principal = Double.parseDouble(p[3].trim());
                double rate      = Double.parseDouble(p[4].trim());
                int    time      = Integer.parseInt(p[5].trim());
                list.add(new Loan(loanId, custId, loanType, principal, rate, time));
            }
        } catch (IOException e) {
            System.out.println("Error reading loans.txt: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Corrupted data in loans.txt: " + e.getMessage());
        }
        return list;
    }

    public static void saveLoans(ArrayList<Loan> loans) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOANS_FILE, false))) {
            for (Loan l : loans) {
                bw.write(l.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving loans: " + e.getMessage());
        }
    }

    public static LinkedList<String> loadTransactions() {
        LinkedList<String> list = new LinkedList<>();
        File file = new File(TRANSACTIONS_FILE);

        if (!file.exists()) {
            System.out.println("ERROR: transactions.txt file not found.");
            System.out.println("Creating new file...");
            try { file.createNewFile(); }
            catch (IOException e) { System.out.println("Could not create transactions.txt"); }
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) list.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }
        return list;
    }

    public static void appendTransaction(String entry) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            bw.write(entry);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing transaction: " + e.getMessage());
        }
    }

    public static void saveTransactions(LinkedList<String> transactions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, false))) {
            for (String t : transactions) {
                bw.write(t);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }
}