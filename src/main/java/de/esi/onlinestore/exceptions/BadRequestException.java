package de.esi.onlinestore.exceptions;



public class BadRequestException extends Exception {

    private static final long serialVersionUID = 1L;



    public BadRequestException(String message) {
        super(message);
    }


}
