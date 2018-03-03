package bank.request;

public class GetAccountRequest implements Request {

	private static final long serialVersionUID = 7880718382926831796L;

	private String number;

	public GetAccountRequest(String number) {
		this.number = number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

}
