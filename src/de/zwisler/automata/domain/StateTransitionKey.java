package de.zwisler.automata.domain;

import java.util.Objects;

public record StateTransitionKey(
    State fromState,
    String letter
) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StateTransitionKey that = (StateTransitionKey) o;
    return Objects.equals(fromState, that.fromState) && Objects.equals(letter, that.letter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromState, letter);
  }
}
