package bank.response;

public class CloseAccountResponse implements Response {

    private static final long serialVersionUID = 6045949258681325236L;

    private boolean isAccountClosed;

    public CloseAccountResponse(boolean isAccountClosed) {
        this.isAccountClosed = isAccountClosed;
    }

    public boolean isAccountClosed() {
        return isAccountClosed;
    }

}
