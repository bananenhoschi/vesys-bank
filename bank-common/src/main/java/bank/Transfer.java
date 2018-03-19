package bank;

import java.io.Serializable;

public class Transfer implements Serializable {

    private static final long serialVersionUID = -110054441639951614L;

    private final String aNumber;
    private final String bNumber;
    private final double amount;

    public Transfer(String aNumber, String bNumber, double amount) {
        this.aNumber = aNumber;
        this.bNumber = bNumber;
        this.amount = amount;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getANumber() {
        return aNumber;
    }
    
    public String getBNumber() {
        return bNumber;
    }

}
