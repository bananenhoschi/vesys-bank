package bank.server;

import java.io.IOException;
import java.util.Set;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.request.CloseAccountRequest;
import bank.request.CreateAccountRequest;
import bank.request.GetAccountNumbersRequest;
import bank.request.GetAccountRequest;
import bank.request.TransferRequest;
import bank.response.CloseAccountResponse;
import bank.response.CreateAccountResponse;
import bank.response.GetAccountNumbersResponse;
import bank.response.GetAccountResponse;
import bank.response.TransferResponse;

public class RemoteBank implements Bank {

	private Driver driver;

	public RemoteBank(Driver driver) {
		this.driver = driver;
	}

	@Override
	public String createAccount(String owner) throws IOException {
		CreateAccountResponse response = driver.sendRequestAndReceiveResult(new CreateAccountRequest(owner),
				CreateAccountResponse.class);
		return response.getNumber();
	}

	@Override
	public boolean closeAccount(String number) throws IOException {
		CloseAccountResponse response = driver.sendRequestAndReceiveResult(new CloseAccountRequest(number),
				CloseAccountResponse.class);
		return response.isAccountClosed();
	}

	@Override
	public Set<String> getAccountNumbers() throws IOException {
		GetAccountNumbersResponse response = driver.sendRequestAndReceiveResult(new GetAccountNumbersRequest(),
				GetAccountNumbersResponse.class);
		return response.getAccountNumbers();
	}

	@Override
	public Account getAccount(String number) throws IOException {
		GetAccountResponse response = driver.sendRequestAndReceiveResult(new GetAccountRequest(number),
				GetAccountResponse.class);
		return response.getAccount();
	}

	@Override
	public void transfer(Account a, Account b, double amount)
			throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
		TransferRequest request = new TransferRequest();
		request.setFrom(a);
		request.setTo(b);
		request.setAmount(amount);
		driver.sendRequestAndReceiveResult(request, TransferResponse.class);
		return;
	}

}
