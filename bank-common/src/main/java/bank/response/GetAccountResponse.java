package bank.response;

import bank.Account;

public class GetAccountResponse implements Response {

	private static final long serialVersionUID = -8718510975127203268L;

	private Account account;
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}

}
