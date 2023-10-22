package de.zwisler.automata.mutation;

import de.zwisler.automata.domain.State;

public class StateDescriptor {

  private final MutableAutomatonBuilder builder;
  private final State state;

  public StateDescriptor(State state, MutableAutomatonBuilder builder) {
    this.builder = builder;
    this.state = state;
  }

  public StateDescriptor addTransition(String a, String q) {
    builder.addTransition(state.getId(), a, q);
    return this;
  }

  public StateDescriptor addTransition(String a, int q) {
    builder.addTransition(state.getId(), a, String.valueOf(q));
    return this;
  }

  public StateDescriptor setInitial() {
    builder.addInitialState(getState());
    return this;
  }

  public StateDescriptor setFinal() {
    builder.addFinalState(getState());
    return this;
  }

  public State getState() {
    return this.state;
  }
}
