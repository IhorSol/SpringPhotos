package Classes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ihor Solohub.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhotoErrorException extends RuntimeException {
}
