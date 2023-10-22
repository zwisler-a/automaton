package de.zwisler.automata.mutation;

import static de.zwisler.automata.exception.Assertion.isSingleton;

import de.zwisler.automata.domain.automaton.Automaton;
import de.zwisler.automata.domain.State;
import de.zwisler.automata.domain.Transistion;
import java.util.Collection;

public class AutomataUtil {


  public static Automaton initialStateNormalize(Automaton a) {
    MutableAutomaton automaton = new MutableAutomaton(a);

    State initialState = new State();
    for (State oldInitial : a.getInitialState()) {
      Collection<Transistion> transitionsFromOldInitial = automaton.getTransitionsForm(oldInitial);
      for (Transistion t : transitionsFromOldInitial) {
        automaton.addTransition(new Transistion(initialState, t.getCharacter(), t.getTo()));
      }
    }
    automaton.addState(initialState);
    automaton.clearInitialState();
    automaton.addInitialState(initialState);

    boolean acceptsEmpty = a.getFinalStates().stream().anyMatch(s -> a.getInitialState().contains(s));
    if (acceptsEmpty) {
      automaton.addFinalState(initialState);
    }
    return automaton.nda();
  }

  public static Automaton finalStateNormalize(Automaton a) {
    MutableAutomaton automaton = new MutableAutomaton(a);

    State finalState = new State();
    for (State oldFinal : a.getFinalStates()) {
      Collection<Transistion> transitionsToOldFinal = automaton.getTransitionsTo(oldFinal);
      for (Transistion t : transitionsToOldFinal) {
        automaton.addTransition(new Transistion(t.getFrom(), t.getCharacter(), finalState));
      }
    }
    automaton.addState(finalState);
    automaton.clearFinalState();
    automaton.addFinalState(finalState);

    boolean acceptsEmpty = a.getFinalStates().stream().anyMatch(s -> a.getInitialState().contains(s));
    if (acceptsEmpty) {
      automaton.addInitialState(finalState);
    }
    return automaton.nda();
  }


  public static Automaton concat(Automaton left, Automaton right) {
    MutableAutomaton finalStateNormalized = MutableAutomaton.of(finalStateNormalize(left));
    MutableAutomaton initialStateNormalized = MutableAutomaton.of(initialStateNormalize(right));

    finalStateNormalized.prefixStates("left");
    initialStateNormalized.prefixStates("right");

    isSingleton(finalStateNormalized.getFinalStates(),
        "Final state in not unique for assumed finalized state normalized automaton");
    isSingleton(initialStateNormalized.getInitialState(),
        "Inital state in not unique for assumed initial state normalized automaton");
    State fEqualsICandidate = finalStateNormalized.getFinalStates().get(0);
    State fEqualsi = initialStateNormalized.getInitialState().get(0);

    fEqualsICandidate.indentify(fEqualsi);

    MutableAutomaton result = MutableAutomaton.createFrom(finalStateNormalized)
        .addTransitions(initialStateNormalized.getTransitions())
        .addStates(initialStateNormalized.getStates())
        .withInitialState(finalStateNormalized.getInitialState())
        .withFinalStates(initialStateNormalized.getFinalStates())
        .build();

    return result.nda();
  }

}
