package Classes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ihor Solohub.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhotoNotFoundException extends RuntimeException {
}
