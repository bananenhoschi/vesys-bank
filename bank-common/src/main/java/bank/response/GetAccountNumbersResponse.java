package bank.response;

import java.util.Set;

public class GetAccountNumbersResponse implements Response {

	private static final long serialVersionUID = 854257203997006978L;

	private Set<String> accountNumbers;

	public void setAccountNumbers(Set<String> accountNumbers) {
		this.accountNumbers = accountNumbers;
	}

	public Set<String> getAccountNumbers() {
		return accountNumbers;
	}

}
