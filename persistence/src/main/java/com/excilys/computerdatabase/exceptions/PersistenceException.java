package com.excilys.computerdatabase.exceptions;

public class PersistenceException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = -1321684846245955899L;

    public PersistenceException(String message) {
        super(message);
    }
    
    public PersistenceException(Throwable cause){
        super(cause);
    }
}
