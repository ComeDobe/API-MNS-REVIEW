package com.dobe.locmns.handlers;

import com.dobe.locmns.exceptions.ObjectValidationException;
import com.dobe.locmns.exceptions.OperationNonPermittedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ObjectValidationException.class})
    public ResponseEntity<ExceptionRepresentation> handleException(ObjectValidationException exception){
        ExceptionRepresentation representation=ExceptionRepresentation.builder()
                .errorMessage("une exception invalide s'est produite")
                .errorSource(exception.getViolationSource())
                .validationErrors((exception.getViolations()))
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(representation);
    }
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ExceptionRepresentation> handleException(EntityNotFoundException exception){
        ExceptionRepresentation representation=ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(representation);
    }
    @ExceptionHandler({OperationNonPermittedException.class})
    public ResponseEntity<ExceptionRepresentation> handleException(OperationNonPermittedException exception){
        ExceptionRepresentation representation=ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(representation);

    }
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ExceptionRepresentation> handleException(DataIntegrityViolationException exception){
        ExceptionRepresentation representation=ExceptionRepresentation.builder()
                .errorMessage("l'utilisateur existe deja avec l'adresse email fournie et/ou avec les memes informations")
                .build();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(representation);
    }
    @ExceptionHandler({DisabledException.class})
    public ResponseEntity<ExceptionRepresentation> handleException(DisabledException exception){
        ExceptionRepresentation representation=ExceptionRepresentation.builder()
                .errorMessage("tu ne peux acceder à ce compte car il n'est pas encore activé")
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(representation);
    }
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ExceptionRepresentation> handleException(BadCredentialsException exception){
        ExceptionRepresentation representation=ExceptionRepresentation.builder()
                .errorMessage("les identifiants fournis sont incorrects")
                .build();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(representation);

    }
}
