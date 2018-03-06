package bank.request;

public class DepositRequest implements Request {

    private static final long serialVersionUID = 8499716481071216732L;

    private String number;

    private double amount;

    public DepositRequest(String number, double amount) {
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
