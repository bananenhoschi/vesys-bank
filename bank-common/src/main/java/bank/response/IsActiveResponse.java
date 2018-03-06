package bank.response;

public class IsActiveResponse implements Response {

    private static final long serialVersionUID = 5255695062015824600L;

    private boolean isActive;

    public IsActiveResponse(boolean isActive) {
    this.isActive = isActive;
    }
    
    public boolean isActive() {
        return isActive;
    }

}
