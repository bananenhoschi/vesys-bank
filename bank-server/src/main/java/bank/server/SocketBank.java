package bank.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

public class SocketBank implements Bank {

    private final Map<String, Account> accounts = new HashMap<>();

    @Override
    public Set<String> getAccountNumbers() {
        return accounts.entrySet().stream().filter(e -> {
            try {
                return e.getValue().isActive();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }).map(e -> e.getKey()).collect(Collectors.toSet());
    }

    @Override
    public String createAccount(String owner) throws IOException {

        Account acc = new RemoteAccount(owner);
        accounts.put(acc.getNumber(), acc);
        return acc.getNumber();
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        Account acc = accounts.get(number);
        if (acc != null) {
            if (acc.getBalance() > 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public bank.Account getAccount(String number) {
        return accounts.get(number);
    }

    @Override
    public void transfer(bank.Account from, bank.Account to, double amount)
            throws IOException, InactiveException, OverdrawException {

        if (amount < 0) {
            throw new IOException("Amount has to be greater than 0");
        }

        if (from != null && to != null && from != to) {

            if (!from.isActive()) {
                throw new InactiveException("From is inactive");
            }
            if (!to.isActive()) {
                throw new InactiveException("To is inactive");
            }
            if (from.getBalance() >= amount) {
                from.withdraw(amount);
                to.deposit(amount);
            } else {
                throw new OverdrawException();
            }
        } else {
            throw new IOException();
        }

    }

}
