package az.cargora.cargora.exception.customExceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("User not found");
    }

    

}
