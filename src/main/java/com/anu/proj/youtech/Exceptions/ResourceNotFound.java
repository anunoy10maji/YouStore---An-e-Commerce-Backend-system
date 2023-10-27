package com.anu.proj.youtech.Exceptions;

public class ResourceNotFound extends RuntimeException {
    private String message;
    public ResourceNotFound(String message)
    {
        super(message);
    }
}
