package de.zwisler.automata.domain.automaton;

import de.zwisler.automata.domain.State;
import de.zwisler.automata.domain.StateTransitionKey;
import de.zwisler.automata.domain.Transistion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DeterministicAutomaton extends Automaton {


  public DeterministicAutomaton(Collection<State> states, Collection<Transistion> transistions,
      Collection<State> finalStates, Collection<State> initialState) {
    super(states, transistions, finalStates, initialState);
    if (initialState.size() != 1) {
      throw new IllegalArgumentException("There needs to be one initial state");
    }
    if (transitions.values().stream().anyMatch(t -> t.size() > 1)) {
      throw new IllegalArgumentException("Multiple transitions from one state with the same letter");
    }
  }

  private Collection<State> getNextStatesFor(State q, String a) {
    return transitions.getOrDefault(new StateTransitionKey(q, a), new ArrayList<>()).stream().map(Transistion::getTo)
        .toList();
  }

  @Override
  public Collection<Run> run(String word, boolean log) {
    Run run = new Run(word, initialState.stream().findFirst().orElseThrow(), List.of(), this::getNextStatesFor);
    for (String a : word.split("")) {
      Optional<Run> runOptional = run.next().stream().findFirst();
      if (runOptional.isEmpty()) {
        return List.of();
      } else {
        run = runOptional.get();
      }

    }
    return List.of(run);
  }
}
