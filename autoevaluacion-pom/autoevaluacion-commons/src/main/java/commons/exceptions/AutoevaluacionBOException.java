package commons.exceptions;

import java.io.Serializable;

/**
 * @author Euar Troyano
 * 
 *         Excepciones a nivel de BO/EJB
 *  
 */
public class AutoevaluacionBOException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public AutoevaluacionBOException() {
        super();
    }

    public AutoevaluacionBOException(String message) {
        super(message);
    }

    public AutoevaluacionBOException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoevaluacionBOException(Throwable cause) {
        super(cause);
    }

}
