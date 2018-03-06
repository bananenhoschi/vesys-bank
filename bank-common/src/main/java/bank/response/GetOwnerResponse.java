package bank.response;

public class GetOwnerResponse implements Response {

    private static final long serialVersionUID = -4073086101085367193L;

    private String owner;

    public GetOwnerResponse(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

}
