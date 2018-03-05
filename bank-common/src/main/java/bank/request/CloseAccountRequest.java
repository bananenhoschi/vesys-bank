package bank.request;

public class CloseAccountRequest implements Request {

    private final String number;

    public CloseAccountRequest(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    private static final long serialVersionUID = -9071609830246676722L;

}
