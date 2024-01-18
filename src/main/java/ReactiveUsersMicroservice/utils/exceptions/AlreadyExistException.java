package ReactiveUsersMicroservice.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exists")
public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException() {
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
