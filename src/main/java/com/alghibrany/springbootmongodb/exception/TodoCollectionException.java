package com.alghibrany.springbootmongodb.exception;

public class TodoCollectionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TodoCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Todo with " + id + " Not Found !!";
    }

    public static String todoAllAlreadyExist() {
        return "Todo given name allready exist";
    }
}
