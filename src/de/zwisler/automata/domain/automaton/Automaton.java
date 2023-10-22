package de.zwisler.automata.domain.automaton;

import static java.util.stream.Collectors.groupingBy;

import de.zwisler.automata.domain.State;
import de.zwisler.automata.domain.StateTransitionKey;
import de.zwisler.automata.domain.Transistion;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Automaton implements AutomatonDataHolder{

  protected Collection<State> states;
  protected Map<StateTransitionKey, List<Transistion>> transitions;
  protected Collection<State> finalStates;
  protected Collection<State> initialState;

  protected Automaton(Collection<State> states, Collection<Transistion> transitions, Collection<State> finalStates,
      Collection<State> initialState) {
    this.states = Collections.unmodifiableCollection(states);
    this.transitions = transitions.stream().collect(groupingBy(o -> new StateTransitionKey(o.getFrom(), o.getCharacter())));
    this.finalStates = Collections.unmodifiableCollection(finalStates);
    this.initialState = Collections.unmodifiableCollection(initialState);
  }

  public Collection<Run> run(String word) {
    return run(word, false);
  }


  public abstract Collection<Run> run(String word, boolean log);


  public Collection<Transistion> getTransitionsFor(State state, String word) {
    return transitions.get(new StateTransitionKey(state, word));
  }

  public Collection<State> getStates() {
    return states;
  }

  public Collection<Transistion> getTransitions() {
    return transitions.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
  }

  public Collection<State> getFinalStates() {
    return finalStates;
  }

  public Collection<State> getInitialState() {
    return initialState;
  }
}
