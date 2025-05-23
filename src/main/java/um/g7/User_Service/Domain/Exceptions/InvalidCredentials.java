package um.g7.User_Service.Domain.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCredentials extends Exception{
    public InvalidCredentials(String msg) {super(msg);}
}
