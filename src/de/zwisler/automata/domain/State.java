package de.zwisler.automata.domain;

import java.util.Objects;

public class State {

  private static Long stateCounter = 1L;

  private String id;

  public State(String id) {
    this.id = id;
  }

  public State() {
    this.id = String.valueOf(stateCounter++);
  }

  public State(State state) {
    this.id = state.getId();
  }

  public String getId() {
    return id;
  }

  private void setId(String id) {
    this.id = id;
  }

  public void indentify(State state) {
    this.setId(state.getId());
  }


  @Override
  public String toString() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    State state = (State) o;
    return Objects.equals(id, state.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
