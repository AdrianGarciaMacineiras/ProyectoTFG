package com.sngular.skilltree.testutil.exception;

import java.io.IOException;

public class FileProblemException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Problem reading fixture file %s ";

  public FileProblemException(String fileName, IOException exception) {
    super(String.format(ERROR_MESSAGE, fileName), exception);
  }
}
