package de.zwisler.automata.interaction;

import de.zwisler.automata.domain.automaton.Automaton;
import java.util.Scanner;
import sun.misc.Signal;

public class TerminalController {

  boolean running = true;
  ConsoleInputReadTask consoleInputReadTask = new ConsoleInputReadTask();

  public TerminalController(Automaton automaton) {

    Signal.handle(new Signal("INT"),  // SIGINT
        signal -> {
          System.out.println("Interrupted by Ctrl+C");
          running = false;
          consoleInputReadTask.abort();
        });

    while (running) {
      System.out.print("Enter a word: ");
      String word = consoleInputReadTask.call();
      long startTime = System.currentTimeMillis();
      if (!automaton.run(word, false).isEmpty()) {
        System.out.println("Word recognized!");
      } else {
        System.out.println("Word not recognized!");
      }
      long endTime = System.currentTimeMillis();
      System.out.println("(That took " + (endTime - startTime) + " milliseconds)");
    }
  }

}
