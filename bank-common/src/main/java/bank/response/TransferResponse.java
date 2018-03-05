package bank.response;

import bank.Account;

public class TransferResponse implements Response {

    private static final long serialVersionUID = -7217608924884722460L;

    private Account from;
    private Account to;

    private Exception exception;

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public void setException(Exception e) {
        this.exception = e;
    }

    public Exception geException() {
        return exception;
    }

}
