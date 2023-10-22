package de.zwisler.automata.prebuild;

import de.zwisler.automata.domain.automaton.Automaton;
import de.zwisler.automata.mutation.MutableAutomaton;

public class PrebuildAutomaton {

  public static final Automaton BINARY_DIVIDABLE_BY_3 = MutableAutomaton.create()
      .addState("0", q -> q
          .addTransition("0", 0)
          .addTransition("1", 1)
      )
      .addState("1", q -> q
          .addTransition("0", 2)
          .addTransition("1", 0)
      )
      .addState("2", q -> q
          .addTransition("0", 1)
          .addTransition("1", 2)
      )
      .addInitialState(0)
      .addFinalState(0)
      .build().nda();

  public static final Automaton TWO_CHARACTERS = MutableAutomaton.create()
      .addState(0, q -> q
          .setInitial()
          .setFinal()
          .addTransition("a", 1)
          .addTransition("b", 1)
      )
      .addState(1, q -> q
          .addTransition("a", 0)
          .addTransition("b", 0)
      ).build().nda();

}
