package de.zwisler.automata.exception;

import java.util.Collection;

public class Assertion {

  public static void isSingleton(Collection<?> collection, String s) {
    if (collection.size() != 1) {
      throw new AssertionFailedExpectation(s);
    }
  }


}
