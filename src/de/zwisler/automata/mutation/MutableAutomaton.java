package de.zwisler.automata.mutation;

import de.zwisler.automata.domain.State;
import de.zwisler.automata.domain.Transistion;
import de.zwisler.automata.domain.automaton.Automaton;
import de.zwisler.automata.domain.automaton.AutomatonDataHolder;
import de.zwisler.automata.domain.automaton.NonDeterministicAutomaton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MutableAutomaton implements AutomatonDataHolder {

  public static MutableAutomatonBuilder create() {
    return new MutableAutomatonBuilder();
  }

  public static MutableAutomatonBuilder createFrom(AutomatonDataHolder automaton) {
    return new MutableAutomatonBuilder()
        .withStates(automaton.getStates())
        .withTransitions(automaton.getTransitions())
        .withFinalStates(automaton.getFinalStates())
        .withInitialState(automaton.getInitialState());
  }

  public static MutableAutomaton of(AutomatonDataHolder automaton) {
    return new MutableAutomaton(
        automaton.getTransitions(),
        automaton.getStates(),
        automaton.getFinalStates(),
        automaton.getInitialState()
    );
  }


  protected List<Transistion> transitions = new ArrayList<>();
  protected List<State> states = new ArrayList<>();
  protected List<State> finalStates = new ArrayList<>();
  protected List<State> initialState = new ArrayList<>();

  public MutableAutomaton() {
  }

  public MutableAutomaton(
      Collection<Transistion> transitions,
      Collection<State> states,
      Collection<State> finalStates,
      Collection<State> initialState
  ) {
    this.transitions = new ArrayList<>(transitions);
    this.states = new ArrayList<>(states);
    this.finalStates = new ArrayList<>(finalStates);
    this.initialState = new ArrayList<>(initialState);
  }

  protected MutableAutomaton(Automaton automaton) {
    // Create deep copies of states using copy constructors
    states = automaton.getStates().stream()
        .map(State::new) // Assuming a copy constructor in the State class
        .collect(Collectors.toCollection(ArrayList::new));

    // Create deep copies of transitions
    transitions = new ArrayList<>();
    for (Transistion transition : automaton.getTransitions()) {
      State from = states.stream()
          .filter(s -> s.equals(transition.getFrom()))
          .findFirst().orElse(null);
      State to = states.stream()
          .filter(s -> s.equals(transition.getTo()))
          .findFirst().orElse(null);
      if (from != null && to != null) {
        Transistion transitionCopy = new Transistion(from, transition.getCharacter(), to);
        transitions.add(transitionCopy);
      }
    }

    // Create deep copies of finalStates using copy constructors
    finalStates = automaton.getFinalStates().stream()
        .map(State::new) // Assuming a copy constructor in the State class
        .collect(Collectors.toCollection(ArrayList::new));

    // Create deep copies of initialState using copy constructors
    initialState = automaton.getInitialState().stream()
        .map(State::new) // Assuming a copy constructor in the State class
        .collect(Collectors.toCollection(ArrayList::new));
  }


  public void replaceState(State currentState, State newState) {
    int index = states.indexOf(currentState);
    if (index != -1) {
      // Replace the state at the specified index with the new state
      states.set(index, newState);

      // Update transitions that refer to the current state to refer to the new state
      for (Transistion transition : transitions) {
        if (transition.getFrom().equals(currentState)) {
          transition.setFrom(newState);
        }
        if (transition.getTo().equals(currentState)) {
          transition.setTo(newState);
        }
      }

      // Update initial state if it matches the current state
      if (initialState.contains(currentState)) {
        initialState.remove(currentState);
        initialState.add(newState);
      }

      // Update final states if they match the current state
      if (finalStates.contains(currentState)) {
        finalStates.remove(currentState);
        finalStates.add(newState);
      }
    }
  }


  public Collection<Transistion> getTransitionsForm(State state) {
    return transitions.stream().filter(t -> t.getFrom().equals(state))
        .collect(Collectors.toSet());
  }

  public Collection<Transistion> getTransitionsTo(State state) {
    return transitions.stream().filter(t -> t.getTo().equals(state))
        .collect(Collectors.toSet());
  }

  public void prefixStates(String prefix) {
    getStates().forEach(s -> replaceState(s, new State(prefix + s.getId())));
  }

  public void addTransition(Transistion transistion) {
    this.transitions.add(transistion);
  }

  public void addState(State state) {
    this.states.add(state);
  }

  public void addInitialState(State state) {
    this.initialState.add(state);
  }

  public void addFinalState(State state) {
    this.finalStates.add(state);
  }

  public void clearFinalState() {
    this.finalStates.clear();
  }

  public void clearInitialState() {
    this.initialState.clear();
  }

  public void clearState() {
    this.states.clear();
  }

  public void clearTransitions() {
    this.transitions.clear();
  }

  public Automaton nda() {
    return new NonDeterministicAutomaton(
        states, transitions, finalStates, initialState
    );
  }


  public void removeTransition(Transistion transition) {
    this.transitions.remove(transition);
  }


  public void removeState(State state) {
    this.states.remove(state);

    transitions.removeIf(t -> t.getFrom().equals(state) || t.getTo().equals(state));

    initialState.remove(state);
    finalStates.remove(state);
  }


  public void removeInitialState(State state) {
    this.initialState.remove(state);
  }


  public void removeFinalState(State state) {
    this.finalStates.remove(state);
  }


  public List<Transistion> getTransitions() {
    return transitions;
  }

  public void setTransitions(List<Transistion> transitions) {
    this.transitions = transitions;
  }

  public List<State> getStates() {
    return states;
  }

  public void setStates(List<State> states) {
    this.states = states;
  }

  public List<State> getFinalStates() {
    return finalStates;
  }

  public void setFinalStates(List<State> finalStates) {
    this.finalStates = finalStates;
  }

  public List<State> getInitialState() {
    return initialState;
  }

  public void setInitialState(List<State> initialState) {
    this.initialState = initialState;
  }
}
