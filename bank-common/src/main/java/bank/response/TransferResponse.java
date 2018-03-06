package bank.response;

public class TransferResponse implements Response {

    private static final long serialVersionUID = -7217608924884722460L;

    private boolean success;

    private Throwable t;

    public boolean success() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setThrowable(Throwable t) {
        this.t = t;
    }

    public Throwable getThrowable() {
        return t;
    }

}
