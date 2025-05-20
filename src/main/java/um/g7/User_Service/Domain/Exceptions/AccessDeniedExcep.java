package um.g7.User_Service.Domain.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AccessDeniedExcep extends Exception{
    public AccessDeniedExcep(String msg) {
        super(msg);
    }
}
