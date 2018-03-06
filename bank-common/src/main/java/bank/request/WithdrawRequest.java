package bank.request;

public class WithdrawRequest implements Request {

    private static final long serialVersionUID = 5299620362829825503L;

    private String number;
    private double amount;

    public WithdrawRequest(String number, double amount) {
        this.number = number;
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public double getAmount() {
        return amount;
    }
}
