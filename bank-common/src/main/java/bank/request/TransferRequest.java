package bank.request;

import bank.Account;

public class TransferRequest implements Request {

    private static final long serialVersionUID = 4712766634209668479L;
    private double amount;
    private String from;
    private String to;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
