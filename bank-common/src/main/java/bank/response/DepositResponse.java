package bank.response;

public class DepositResponse implements Response {

    private static final long serialVersionUID = 384716430613183756L;
    private boolean success;
    private Throwable t;

    public DepositResponse() {
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean success() {
        return success;
    }

    public void setThrowable(Throwable t) {
        this.t = t;
    }

    public Throwable getThrowable() {
        return t;
    }

}
