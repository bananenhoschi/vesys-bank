package bank.server;

import java.io.IOException;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.request.DepositRequest;
import bank.request.GetBalanceRequest;
import bank.request.GetOwnerRequest;
import bank.request.IsActiveRequest;
import bank.request.WithdrawRequest;
import bank.response.DepositResponse;
import bank.response.GetBalanceResponse;
import bank.response.GetOwnerResponse;
import bank.response.IsActiveResponse;
import bank.response.WithdrawResponse;

public class AccountProxy implements Account {

    private final String number;
    private Driver driver;

    public AccountProxy(String number, Driver d) {
        this.number = number;
        this.driver = d;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public String getOwner() throws IOException {
        return driver.sendRequestAndReceiveResult(new GetOwnerRequest(number), GetOwnerResponse.class).getOwner();
    }

    @Override
    public boolean isActive() throws IOException {
        return driver.sendRequestAndReceiveResult(new IsActiveRequest(number), IsActiveResponse.class).isActive();
    }

    @Override
    public void deposit(double amount) throws InactiveException, IOException {

        DepositResponse response = driver.sendRequestAndReceiveResult(new DepositRequest(number, amount),
                DepositResponse.class);

        if (response.success()) {
            return;
        } else if (response.getThrowable() instanceof InactiveException) {
            throw new InactiveException(response.getThrowable());
        } else {
            throw new IOException();
        }

    }

    @Override
    public void withdraw(double amount) throws OverdrawException, InactiveException, IOException {

        WithdrawResponse response = driver.sendRequestAndReceiveResult(new WithdrawRequest(number, amount),
                WithdrawResponse.class);

        if (response.success()) {
            return;
        } else if (response.getThrowable() instanceof InactiveException) {
            throw new InactiveException(response.getThrowable());
        } else if (response.getThrowable() instanceof OverdrawException) {
            throw new OverdrawException(response.getThrowable());
        } else {
            throw new IOException();
        }

    }

    @Override
    public double getBalance() throws IOException {
        return driver.sendRequestAndReceiveResult(new GetBalanceRequest(number), GetBalanceResponse.class).getBalance();
    }

}
