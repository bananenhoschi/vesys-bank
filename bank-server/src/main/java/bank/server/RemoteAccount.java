package bank.server;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public class RemoteAccount implements Account {

    private static final long serialVersionUID = 2611581837155433509L;

    private final String number;
    private final String owner;
    private double balance;
    private boolean active = true;

    public RemoteAccount(String owner) {
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
            if (balance + amount < 0) {
                return; // TODO ich w�rde hier einen Fehler ausgeben, aber das kann nie passieren da
                        // amount > 0 ist, und der Datentyp double ist (bei int k�nnte es einen overflow
                        // geben)
            } else {
                balance += amount;
            }
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
        return s.substring(0, 9); // TODO hier w�re es gut zu pr�fen, ob diese Kontonummer nicht bereits vergeben
                                  // worden ist.
    }

}
