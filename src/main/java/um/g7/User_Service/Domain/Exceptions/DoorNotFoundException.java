package um.g7.User_Service.Domain.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DoorNotFoundException extends Exception{
    public DoorNotFoundException(String msg) {
        super(msg);
    }
}
