package fa.training.mockproject.mockprojectfjb05group01.controller.exception;


import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.Collections;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse createErrorResponse(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDetails(Collections.singletonList(ex.getLocalizedMessage()));
        log.error("Loi tra ra: {}",errorResponse.getDetails());
        return errorResponse;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied(AccessDeniedException ex) {
        ModelAndView view = new ModelAndView("error/403");
        view.addObject("403", createErrorResponse(ex));
        return view;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView noHandlerFound(NoHandlerFoundException ex) {
        ModelAndView view = new ModelAndView("error/404");
        view.addObject("404", createErrorResponse(ex));
        return view;
    }
    @ExceptionHandler(InternalServerErrorException.class)
    public ModelAndView handleInternalErrorServer(InternalServerErrorException ex) {
        ModelAndView view = new ModelAndView("error/500");
        view.addObject("500", createErrorResponse(ex));
        return view;
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView view = new ModelAndView("error/error");
        view.addObject("error", createErrorResponse(ex));
        return view;
    }

}
