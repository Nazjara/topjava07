package ru.javawebinar.topjava.util.exception;

import org.springframework.validation.BindingResult;

/**
 * Created by Nazar on 11.08.2016.
 */
public class ValidationException extends RuntimeException
{
    public ValidationException(BindingResult result)
    {
        super(getDescription(result));
    }

    public static String getDescription(BindingResult result)
    {
        StringBuilder sb = new StringBuilder();
        result.getFieldErrors().forEach(fieldError -> sb.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage()));
        return sb.toString();
    }

}
