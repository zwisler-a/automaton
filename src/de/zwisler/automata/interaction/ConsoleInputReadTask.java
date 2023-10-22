package de.zwisler.automata.interaction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ConsoleInputReadTask implements Callable<String> {

  private boolean aborted = false;

  public String call() {
    BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in));
    String input;
    do {
      try {
        // wait until we have data to complete a readLine()
        while (!br.ready() && !aborted) {
          Thread.sleep(200);
        }
        input = br.readLine();
      } catch (Exception e) {
        return null;
      }
    } while ("".equals(input));
    return input;
  }

  public void abort() {
    this.aborted = true;
  }
}
