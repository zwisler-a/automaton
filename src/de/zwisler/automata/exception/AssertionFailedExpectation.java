package de.zwisler.automata.exception;

public class AssertionFailedExpectation extends RuntimeException {

  public AssertionFailedExpectation(String reason) {
    super(reason);
  }
}
