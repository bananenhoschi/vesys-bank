package bank.response;

public class GetBalanceResponse implements Response {
    
    private static final long serialVersionUID = 1850436329002370164L;
    
    private double balance;

    public GetBalanceResponse(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

}
