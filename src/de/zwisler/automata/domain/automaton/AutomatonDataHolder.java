package de.zwisler.automata.domain.automaton;

import de.zwisler.automata.domain.State;
import de.zwisler.automata.domain.Transistion;
import java.util.Collection;

public interface AutomatonDataHolder {

  Collection<State> getStates();

  Collection<Transistion> getTransitions();

  Collection<State> getFinalStates();

  Collection<State> getInitialState();
}
