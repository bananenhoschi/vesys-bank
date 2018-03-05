package bank.response;

public class CreateAccountResponse implements Response {

    private static final long serialVersionUID = 7183191349034929043L;

    private String number;

    public CreateAccountResponse(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
