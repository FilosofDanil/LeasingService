package com.example.wohnungsuchen.exeptionhandlers;

import com.example.wohnungsuchen.exeptions.AuthException;
import com.example.wohnungsuchen.exeptions.RegistryException;
import com.example.wohnungsuchen.exeptions.VerifyException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.text.ParseException;

@Component
public class ExceptionResolver extends AbstractHandlerExceptionResolver {
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        final ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        logger.error(ex.getMessage(), ex);
        if (ex instanceof NotFoundException) {
            if (ex.getMessage().equals("Failed to delete")) {
                modelAndView.setStatus(HttpStatus.NO_CONTENT);
                modelAndView.addObject("message", "Resource is already deleted, or not exist!");
                return modelAndView;
            }
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", "Resource is not found!");
            return modelAndView;
        }
        if (ex instanceof AuthException) {
            modelAndView.setStatus(HttpStatus.FORBIDDEN);
            modelAndView.addObject("message", ex.getMessage());
            return modelAndView;
        }
        if (ex instanceof NullPointerException) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", "Invalid data, or these resource is not exist!");
            return modelAndView;
        }
        if (ex instanceof RegistryException) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", "This mailbox is already taken!");
            return modelAndView;
        }
        if (ex instanceof IllegalArgumentException) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", "Invalid request data!");
            return modelAndView;
        }
        if (ex instanceof ParseException) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", "Invalid data! Failed to parse your request!");
            return modelAndView;
        }
        if (ex instanceof VerifyException) {
            modelAndView.setStatus(HttpStatus.NOT_ACCEPTABLE);
            modelAndView.addObject("message", "You account has been already verified!");
            return modelAndView;
        }
        if (ex instanceof ConstraintViolationException) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", "Invalid request data!");
            return modelAndView;
        }
        if (ex instanceof IOException) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("message", "Something went wrong, while trying to add file!");
            return modelAndView;
        }
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("message", "Something went wrong on the server!");
        return modelAndView;
    }
}
