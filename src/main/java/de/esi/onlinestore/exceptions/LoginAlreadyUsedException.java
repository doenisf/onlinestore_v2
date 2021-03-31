package de.esi.onlinestore.exceptions;

public class LoginAlreadyUsedException extends BadRequestException {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super("Login name already used!");
    }
}
