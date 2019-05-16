package com.quick.start.application.controllers;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.io.FileNotFoundException;
import java.util.Locale;

/**
 * Class that execites the treatment of all exceptions thrown by the system and that
 * are not treated. The function of this is to encapsulate all messages of exception
 * thrown by a JSON that will be returned to the frontend in answer to requests with
 * the code corresponding to the error.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Class that represents the JSON object to be returned with the error message
     */
    private static class BackendError {
        /**
         * The error message
         */
        @SuppressWarnings("unused")
        public String message;

        /**
         * Class constructor
         *
         * @param message Message to be returned to the frontend.
         */
        public BackendError(String message) {
            this.message = message;
        }
    }

    @Autowired
    private MessageSource msgs;

    /**
     * Method that treats the exceptions of the requisitions that vioalte all 
     * configured restrictions in the database
     *
     * @param e The object with the exception to be treated.
     * @return The object containing the message to be send to the frontend
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BackendError handleDataIntegrityException(DataIntegrityViolationException e) {

        String msg = "An invalid operation was done. Check the data and try again.";

        Throwable throwable = e.getCause();
        if (throwable instanceof ConstraintViolationException) {
            // Check if the constraint was vioalted
            ConstraintViolationException ex = (ConstraintViolationException) throwable;
            String constrName = ex.getConstraintName();
            if (constrName != null)
                // Gets the message to be returned by the error
                msg = this.msgs.getMessage(
                        "error.data-integrity." + constrName,
                        null,
                        msg,
                        Locale.getDefault());
        }
        // Adjust the customized message
        return new BackendError(msg);
    }

    /**
     * Message that treats all of the exceptions of validation of data.
     *
     * @param e The object of the exception to be treated.
     * @return The object to be sent to the frontend containing the message.
     */
    @ExceptionHandler({ValidationException.class, PropertyReferenceException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BackendError handleInputException(RuntimeException e) {
        return new BackendError("Invalid data");
    }

    /**
     * Method that deals with the exceptions thrown when the user inputs invalid data
     * @param e The object with the exception to be treated.
     * @return The object to be sent to the frontend containing the message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BackendError handleInputException(IllegalArgumentException e) {
        return new BackendError(e.getMessage());
    }

    /**
     * Method that treats all the exceptions that happendd on spring transactions.
     *
     * @param e The object with the exception to be treated.
     * @return The object to be sent to the frontend containing the message.
     */
    @ExceptionHandler(TransactionSystemException.class)
    @ResponseBody
    public ResponseEntity<BackendError> handleTransactionException(TransactionSystemException e) {
        String message = e.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        Throwable cause = e.getRootCause();
        // se for um problema de validação
        if (cause instanceof ValidationException) {
            status = HttpStatus.BAD_REQUEST;
            message = "Invalid data";
        }

        return new ResponseEntity<>(new BackendError(message), status);
    }

    /**
     * Method that deals with forbidden access exception
     *
     * @param e The object with the exception to be treated.
     * @return The object to be sent to the frontend containing the message.
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public BackendError handleAccessDeniedException(AccessDeniedException e) {
        return new BackendError(e.getMessage());
    }

    /**
     * Method that deals with the exceptions of when a server resource is not found.
     *
     * @param e The object with the exception to be treated.
     * @return The object to be sent to the frontend containing the message.
     */
    @ExceptionHandler({ResourceNotFoundException.class, FileNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public BackendError handleResourceNotFoundException(Exception e) {
        return new BackendError("Resource/File not found");
    }

    /**
     * Method that treats all of the exceptions that were not treated by the system.
     *
     * @param e The object with the exception to be treated.
     * @return The object to be sent to the frontend containing the message.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BackendError handleGenericException(Exception e) {
        // loga
        logger.error("Server error", e);

        return new BackendError(e.getClass().getCanonicalName() + ": " + e.getMessage());
    }
}
