package commons.exceptions;

import java.io.Serializable;

/**
 * @author Eduar Troyano
 * 
 *         Excepciones a nivel de DAO
 * 
 */
public class AutoevaluacionDAOException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public AutoevaluacionDAOException() {
        super();
    }

    public AutoevaluacionDAOException(String message) {
        super(message);
    }

    public AutoevaluacionDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoevaluacionDAOException(Throwable cause) {
        super(cause);
    }

}
