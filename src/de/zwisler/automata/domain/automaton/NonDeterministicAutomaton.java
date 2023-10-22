package de.zwisler.automata.domain.automaton;

import de.zwisler.automata.domain.State;
import de.zwisler.automata.domain.StateTransitionKey;
import de.zwisler.automata.domain.Transistion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class NonDeterministicAutomaton extends Automaton {


  public NonDeterministicAutomaton(Collection<State> states, Collection<Transistion> transitions,
      Collection<State> finalStates, Collection<State> initialState) {
    super(states, transitions, finalStates, initialState);
  }

  private Collection<State> getNextStatesFor(State q, String a) {
    return transitions.getOrDefault(new StateTransitionKey(q, a), new ArrayList<>()).stream().map(Transistion::getTo)
        .toList();
  }


  public Collection<Run> run(String word, boolean log) {
    List<Run> runs = initialState.stream().map(i -> new Run(word, i, List.of(), this::getNextStatesFor)).toList();
    for (String a : word.split("")) {
      runs = runs.stream().flatMap(r -> r.next().stream()).toList();
      if (log) {
        System.out.printf("Executing %s. Run count after %d%n", a, runs.size());
      }
    }
    if (log) {
      System.out.println("Successful runs:");
      runs.forEach(r -> System.out.printf(" %s", r.toString()));
      System.out.println();
    }
    return runs.stream().filter(r -> finalStates.contains(r.currentState)).toList();
  }


}
