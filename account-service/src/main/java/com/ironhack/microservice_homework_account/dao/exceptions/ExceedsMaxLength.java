package com.ironhack.microservice_homework_account.dao.exceptions;

public class ExceedsMaxLength extends Exception {
  public ExceedsMaxLength(String message) {
    super(message);
  }
}
