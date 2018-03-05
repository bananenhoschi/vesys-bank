package bank.request;

public class CreateAccountRequest implements Request {

    private static final long serialVersionUID = 1875984609705395406L;

    private final String owner;

    public CreateAccountRequest(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

}
