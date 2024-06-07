package com.K1dou.Loja.virtual.exceptions;

import com.K1dou.Loja.virtual.service.ServiceSendEmail;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerException {


    @Autowired
    private ServiceSendEmail serviceSendEmail;


    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ExceptionResponseBean> handleNotFoundExceptions(MethodArgumentNotValidException ex) throws MessagingException, UnsupportedEncodingException {
        List<String> e = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        ExceptionResponseBean exceptionResponseBody = new ExceptionResponseBean(e);
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseBody);
    }

    @ExceptionHandler(ExceptionLojaVirtual.class)
    public ResponseEntity<BodyError> exceptionLoja(ExceptionLojaVirtual e) {
        BodyError bodyError = new BodyError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(bodyError);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    public ResponseEntity<BodyError> handleExceptionInternal(Exception ex, WebRequest request) throws MessagingException, UnsupportedEncodingException {

        BodyError bodyError = new BodyError();
        String msg = "";

        if (ex instanceof HttpMessageNotReadableException) {
            msg = "Não está enviando dados para o BODY corpo da requisição";
        } else {
            msg = ex.getMessage();
        }
        bodyError.setError(msg);
        bodyError.setCode(HttpStatus.BAD_REQUEST);
        ex.printStackTrace();

        serviceSendEmail.enviarEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex),"hique1276@gmail.com");

        return ResponseEntity.badRequest().body(bodyError);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    public ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) throws MessagingException, UnsupportedEncodingException {

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

        ex.printStackTrace();

        serviceSendEmail.enviarEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex),"hique1276@gmail.com");

        return new ResponseEntity<Object>(bodyError, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
