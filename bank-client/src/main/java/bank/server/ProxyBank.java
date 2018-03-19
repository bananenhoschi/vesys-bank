package bank.server;

import java.io.IOException;
import java.util.Set;

import bank.Account;
import bank.Bank;
import bank.BankDriver;
import bank.Command;
import bank.InactiveException;
import bank.OverdrawException;
import bank.Request;
import bank.Response;
import bank.Transfer;

public class ProxyBank implements Bank {

    private final BankDriver driver;

    public ProxyBank(BankDriver driver) {
        this.driver = (BankDriver) driver;
    }

    @Override
    public String createAccount(String owner) throws IOException {
        Response response = driver.handle(new Request(Command.createaccount, owner));
        return (String) response.getResult();
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        Response response = driver.handle(new Request(Command.closeaccount, number));
        return (Boolean) response.getResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getAccountNumbers() throws IOException {
        Response response = driver.handle(new Request(Command.getaccountnumbers));
        return (Set<String>) response.getResult();
    }

    @Override
    public Account getAccount(String number) throws IOException {
        Response response = driver.handle(new Request(Command.getaccount, number));
        if ((Boolean) response.getResult()) {
            return new ProxyAccount(number, driver);
        }
        return null;
    }

    @Override
    public void transfer(Account a, Account b, double amount)
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        Request request = new Request(Command.transfer, new Transfer(a.getNumber(), b.getNumber(), amount));
        driver.handle(request);
    }

}
