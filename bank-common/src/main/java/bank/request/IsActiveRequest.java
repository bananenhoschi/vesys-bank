package bank.request;

public class IsActiveRequest implements Request {

    private String number;

    public IsActiveRequest(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    private static final long serialVersionUID = 2633839102624840187L;

}
