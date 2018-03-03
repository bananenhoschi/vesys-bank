package bank.request;

import bank.Account;

public class TransferRequest implements Request {

	private static final long serialVersionUID = 4712766634209668479L;
	private double amount;
	private Account from;
	private Account to;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Account getFrom() {
		return from;
	}

	public void setFrom(Account from) {
		this.from = from;
	}

	public Account getTo() {
		return to;
	}

	public void setTo(Account to) {
		this.to = to;
	}

}
