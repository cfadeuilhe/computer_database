package com.excilys.computerdatabase.exceptions;

public class PersistenceException extends Exception{

    public PersistenceException(String message) {
        super(message);
    }
    
    public PersistenceException(Throwable cause){
        super(cause);
    }
}
