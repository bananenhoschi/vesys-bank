package bank.request;

public class GetOwnerRequest implements Request {

    private static final long serialVersionUID = -572227118229757309L;

    private String number;

    public GetOwnerRequest(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

}
