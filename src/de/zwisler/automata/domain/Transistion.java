package de.zwisler.automata.domain;

public class Transistion {

  private State from;
  private String character;
  private State to;

  public Transistion(State from, String character, State to) {
    this.from = from;
    this.character = character;
    this.to = to;
  }

  public State getFrom() {
    return from;
  }

  public void setFrom(State from) {
    this.from = from;
  }

  public String getCharacter() {
    return character;
  }

  public void setCharacter(String character) {
    this.character = character;
  }

  public State getTo() {
    return to;
  }

  public void setTo(State to) {
    this.to = to;
  }
}
