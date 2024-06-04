package com.K1dou.Loja.virtual.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ExceptionLojaVirtual.class)
    public ResponseEntity<BodyError> exceptionLoja(ExceptionLojaVirtual e) {
        BodyError bodyError = new BodyError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(bodyError);
    }


    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    public ResponseEntity<BodyError> handleExceptionInternal(Exception ex, WebRequest request) {

    BodyError bodyError = new BodyError();
    String msg = "";

    if (ex instanceof MethodArgumentNotValidException) {
        List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
        for (ObjectError objectError : list) {
            msg += objectError.getDefaultMessage() + "; ";
        }
    } else if (ex instanceof HttpMessageNotReadableException) {
        msg = "Não está enviando dados para o BODY corpo da requisição";
    } else {
        msg = ex.getMessage();
    }

    bodyError.setError(msg);
    bodyError.setCode(HttpStatus.BAD_REQUEST);

    return ResponseEntity.badRequest().body(bodyError);
}

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {

        BodyError bodyError = new BodyError();
        String msg = "";

        if (ex instanceof DataIntegrityViolationException) {
            msg = "Erro de integridade no banco: " + ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
        } else if (ex instanceof ConstraintViolationException) {
            msg = "Erro de chave estrangeira: " + ((ConstraintViolationException) ex).getCause().getCause().getMessage();
        } else if (ex instanceof SQLException) {
            msg = "Erro de SQL do Banco: " + ((SQLException) ex).getCause().getCause().getMessage();
        } else {
            msg = ex.getMessage();
        }
        bodyError.setError(msg);
        bodyError.setCode(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Object>(bodyError, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
