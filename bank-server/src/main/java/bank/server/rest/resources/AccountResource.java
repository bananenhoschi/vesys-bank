package bank.server.rest.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import bank.InactiveException;
import bank.OverdrawException;

@Path("account")
public class AccountResource {

    @GET
    @Path("{number}/owner")
    public String getOwner() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @GET
    @Path("{number}/active")
    public boolean isActive() throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @POST
    @Path("{number}/deposit/{amount}")
    public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
        // TODO Auto-generated method stub

    }

    @POST
    @Path("{number}/withdraw/{amount}")
    public void withdraw(double amount)
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        // TODO Auto-generated method stub

    }

    @GET
    @Path("{number}/balance")
    public double getBalance() throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }

}
