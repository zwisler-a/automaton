package de.zwisler.automata;

import static de.zwisler.automata.prebuild.PrebuildAutomaton.BINARY_DIVIDABLE_BY_3;
import static de.zwisler.automata.prebuild.PrebuildAutomaton.TWO_CHARACTERS;

import de.zwisler.automata.domain.automaton.Automaton;
import de.zwisler.automata.interaction.TerminalController;
import de.zwisler.automata.mutation.AutomataUtil;
import de.zwisler.automata.mutation.MutableAutomaton;
import de.zwisler.automata.prebuild.PrebuildAutomaton;

public class MainApplication {

  public static void main(String[] args) {

    Automaton concatExample = AutomataUtil.concat(PrebuildAutomaton.BINARY_DIVIDABLE_BY_3, PrebuildAutomaton.TWO_CHARACTERS);

    new TerminalController(concatExample);
  }

}
