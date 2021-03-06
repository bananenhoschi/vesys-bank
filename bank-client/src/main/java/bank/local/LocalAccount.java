package bank.local;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public class LocalAccount implements Account {

    private static final long serialVersionUID = 1L;

    private final String number;
    private final String owner;
    private double balance;
    private boolean active = true;

    LocalAccount(String owner) {
        this.owner = owner;
        this.number = randomNumber();
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void deposit(double amount) throws InactiveException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount is less than 0.0.");
        }

        if (isActive()) {
            balance += amount;
        } else {
            throw new InactiveException();
        }
    }

    @Override
    public void withdraw(double amount) throws InactiveException, OverdrawException {

        if (amount < 0) {
            throw new IllegalArgumentException("Amount is less than 0.0.");
        }

        if (isActive()) {
            if (amount > balance) {
                throw new OverdrawException();
            } else {
                balance = balance - amount;
            }
        } else {
            throw new InactiveException();
        }
    }

    private String randomNumber() {
        long timeSeed = System.nanoTime();
        double randSeed = Math.random() * 1000;
        long midSeed = (long) (timeSeed * randSeed);

        String s = midSeed + "";
        return s.substring(0, 9);
    }

}
