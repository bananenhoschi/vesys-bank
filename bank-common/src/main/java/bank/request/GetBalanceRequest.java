package bank.request;

public class GetBalanceRequest implements Request {

    private String number;

    public GetBalanceRequest(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    private static final long serialVersionUID = -7550124794360114812L;

}
