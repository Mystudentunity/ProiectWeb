package Proiect.ProiectWeb.Controller;

import Proiect.ProiectWeb.Service.OnlineException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OnlineExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(OnlineException.class)
    public ResponseEntity<String> handlerOnlineException(OnlineException exception, WebRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(exception.getError() == OnlineException.ErrorCode.GUEST_NOT_FOUND){
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(exception.getError().name(), status);
    }
}
