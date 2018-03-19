package bank;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = -3549848071408616123L;

    private Exception exception;
    private boolean success = true;
    private Object result;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
        success = false;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }
    

}
