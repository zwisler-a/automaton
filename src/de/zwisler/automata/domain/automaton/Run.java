package de.zwisler.automata.domain.automaton;

import de.zwisler.automata.domain.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class Run {

  String nextCharacters;
  State currentState;
  List<State> q = new ArrayList<>();
  BiFunction<State, String, Collection<State>> nextStateResolver;

  public Run(String nextCharacters, State currentState, List<State> q,
      BiFunction<State, String, Collection<State>> nextStateResolver) {
    this.nextCharacters = nextCharacters;
    this.currentState = currentState;
    this.q = q;
    this.nextStateResolver = nextStateResolver;
  }

  Collection<Run> next() {
    String a = String.valueOf(nextCharacters.charAt(0));
    Collection<State> nextPossibleStates = nextStateResolver.apply(currentState, a);
    return nextPossibleStates.stream().map(nextPossibleState -> new Run(
        nextCharacters.substring(1),
        nextPossibleState,
        Stream.concat(q.stream(), Stream.of(currentState)).toList(),
        nextStateResolver
    )).toList();
  }

  @Override
  public String toString() {
    return "Run{" +
        "q=" + q +
        '}';
  }
}
