package bank.response;

public class GetAccountResponse implements Response {

    private static final long serialVersionUID = -8718510975127203268L;

    private boolean accountExists;

    public GetAccountResponse(boolean accountExists) {
        this.accountExists = accountExists;
    }

    public boolean isAccountExists() {
        return accountExists;
    }



}
