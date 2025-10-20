package giuseppetuccilli.u5_w2_d5.exeptions;

import giuseppetuccilli.u5_w2_d5.security.ErrorsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class ExeptionHandler {
    @ExceptionHandler(BadRequestExeption.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsPayload handleBadReq(BadRequestExeption ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDate.now());
    }

    @ExceptionHandler(NotFoundExeption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsPayload handleNotFound(NotFoundExeption ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDate.now());
    }

    @ExceptionHandler(ValidazioneFallitaExeption.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorListPayload handleValid(ValidazioneFallitaExeption ex) {
        return new ErrorListPayload(ex.getMessage(), ex.getMsgList());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsPayload genericHandler(Exception ex) {
        ex.printStackTrace();
        return new ErrorsPayload("errore generico", LocalDate.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsDTO handleUnauthorized(UnauthorizedException ex) {
        return new ErrorsDTO(ex.getMessage());
    }
}
