package bank.server;

import java.io.IOException;

import bank.Account;
import bank.BankDriver;
import bank.Command;
import bank.InactiveException;
import bank.OverdrawException;
import bank.Request;
import bank.Response;

public class ProxyAccount implements Account {

    private final String number;
    private BankDriver driver;

    public ProxyAccount(String number, BankDriver d) {
        this.number = number;
        this.driver = d;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public String getOwner() throws IOException {
        return (String) driver.handle(new Request(Command.getowner, getNumber())).getResult();
    }

    @Override
    public boolean isActive() throws IOException {
        return (boolean) driver.handle(new Request(Command.isactive, getNumber())).getResult();
    }

    @Override
    public void deposit(double amount) throws InactiveException, IOException {

        Response response = driver.handle(new Request(Command.deposit, getNumber(), amount));

        if (response.isSuccess()) {
            return;
        } else if (response.getException() instanceof InactiveException) {
            throw new InactiveException(response.getException());
        } else {
            throw new IOException();
        }

    }

    @Override
    public void withdraw(double amount) throws OverdrawException, InactiveException, IOException {

        Response response = driver.handle(new Request(Command.withdraw, getNumber(), amount));
        
        if (response.isSuccess()) {
            return;
        } else if (response.getException() instanceof InactiveException) {
            throw new InactiveException(response.getException());
        } else if (response.getException() instanceof OverdrawException) {
            throw new OverdrawException(response.getException());
        } else {
            throw new IOException();
        }

    }

    @Override
    public double getBalance() throws IOException {
        return (double) driver.handle(new Request(Command.getbalance, number)).getResult();
    }

}
