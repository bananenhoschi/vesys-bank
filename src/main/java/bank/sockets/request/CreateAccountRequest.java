package bank.sockets.request;

public class CreateAccountRequest implements Request{

	private String owner;

	public CreateAccountRequest(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
	}
	
	
}
