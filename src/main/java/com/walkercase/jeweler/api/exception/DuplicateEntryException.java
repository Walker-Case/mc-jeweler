package com.walkercase.jeweler.api.exception;

/**
 * Thrown when a duplicate is about to be added.
 */
public class DuplicateEntryException extends RuntimeException{

    public DuplicateEntryException(String msg){
        super(msg);
    }

}
