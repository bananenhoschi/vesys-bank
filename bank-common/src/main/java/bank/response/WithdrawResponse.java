package bank.response;

public class WithdrawResponse implements Response {

    private static final long serialVersionUID = -2475880742718723317L;

    private boolean success;
    private Throwable t;

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
