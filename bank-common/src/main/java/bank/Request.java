package bank;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = -3676008398574833314L;

    private final Command command;

    private final Object[] value;

    public Request(Command command, Object... value) {
        this.command = command;
        this.value = value;
    }
    
    public Command getCommand() {
        return command;
    }
    
    public Object[] getValue() {
        return value;
    }

}
