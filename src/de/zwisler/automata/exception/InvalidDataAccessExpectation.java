package de.zwisler.automata.exception;

public class InvalidDataAccessExpectation extends RuntimeException {

  public InvalidDataAccessExpectation(String reason) {
    super(reason);
  }
}
