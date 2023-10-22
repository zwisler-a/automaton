package de.zwisler.automata.mutation;

import de.zwisler.automata.domain.State;
import de.zwisler.automata.domain.Transistion;
import de.zwisler.automata.exception.InvalidDataAccessExpectation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class MutableAutomatonBuilder {

  private List<Transistion> transitions = new ArrayList<>();
  private List<State> states = new ArrayList<>();
  private List<State> finalStates = new ArrayList<>();
  private List<State> initialState = new ArrayList<>();

  private final Map<StateDescriptor, UnaryOperator<StateDescriptor>> stateDescriptorFunctionMap = new HashMap<>();

  public MutableAutomatonBuilder() {
  }

  public MutableAutomatonBuilder withTransitions(Collection<Transistion> transitions) {
    this.transitions = new ArrayList<>(transitions);
    return this;
  }

  public MutableAutomatonBuilder withStates(Collection<State> states) {
    this.states = new ArrayList<>(states);
    return this;
  }

  public MutableAutomatonBuilder withFinalStates(Collection<State> finalStates) {
    this.finalStates = new ArrayList<>(finalStates);
    return this;
  }

  public MutableAutomatonBuilder withInitialState(Collection<State> initialState) {
    this.initialState = new ArrayList<>(initialState);
    return this;
  }

  public MutableAutomatonBuilder addTransition(Transistion transistion) {
    this.transitions.add(transistion);
    return this;
  }

  public MutableAutomatonBuilder addTransition(String q, String a, String p) {
    this.transitions.add(new Transistion(getStateById(q), a, getStateById(p)));
    return this;
  }

  public MutableAutomatonBuilder addTransition(State q, String a, String p) {
    this.transitions.add(new Transistion(q, a, getStateById(p)));
    return this;
  }

  public MutableAutomatonBuilder addTransition(int q, String a, int p) {
    this.transitions.add(new Transistion(getStateById(String.valueOf(q)), a, getStateById(String.valueOf(p))));
    return this;
  }

  public MutableAutomatonBuilder addTransitions(Collection<Transistion> transitions) {
    this.transitions.addAll(transitions);
    return this;
  }

  public MutableAutomatonBuilder addStates(String... args) {
    for (int i = 0; i <= args.length; i++) {
      this.states.add(new State(args[i]));
    }
    return this;
  }

  public MutableAutomatonBuilder addStates(int n) {
    for (int i = 0; i <= n; i++) {
      this.states.add(new State(String.valueOf(this.states.size())));
    }
    return this;
  }

  public MutableAutomatonBuilder addState(int name, UnaryOperator<StateDescriptor> descriptorFunction) {
    return addState(String.valueOf(name), descriptorFunction);
  }

  public MutableAutomatonBuilder addState(String name, UnaryOperator<StateDescriptor> descriptorFunction) {
    State state = new State(name);
    stateDescriptorFunctionMap.put(new StateDescriptor(state, this), descriptorFunction);
    states.add(state);
    return this;
  }


  public MutableAutomatonBuilder addState(State state) {
    this.states.add(state);
    return this;
  }

  public MutableAutomatonBuilder addInitialState(State state) {
    this.initialState.add(state);
    return this;
  }

  public MutableAutomatonBuilder addFinalState(State state) {
    this.finalStates.add(state);
    return this;
  }

  State getStateById(String id) {
    return this.states.stream().filter(s -> s.getId().equals(id)).findFirst()
        .orElseThrow(() -> new InvalidDataAccessExpectation("No state with id: " + id));
  }

  public MutableAutomaton build() {
    this.stateDescriptorFunctionMap.forEach(
        (stateDescriptor, stateDescriptorUnaryOperator) -> stateDescriptorUnaryOperator.apply(stateDescriptor));
    return new MutableAutomaton(transitions, states, finalStates, initialState);
  }

  public MutableAutomatonBuilder addStates(Collection<State> states) {
    this.states.addAll(states);
    return this;
  }

  public MutableAutomatonBuilder onStates(Consumer<Collection<State>> consumer) {
    consumer.accept(this.states);
    return this;
  }


  public MutableAutomatonBuilder addInitialState(int i) {
    return this.addInitialState(String.valueOf(i));
  }

  public MutableAutomatonBuilder addInitialState(String i) {
    return addInitialState(getStateById(i));
  }

  public MutableAutomatonBuilder addFinalState(int i) {
    return addFinalState(String.valueOf(i));
  }

  public MutableAutomatonBuilder addFinalState(String i) {
    return addFinalState(getStateById(i));
  }
}
