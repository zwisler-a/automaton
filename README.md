# Automaton

Little project to understand automatons a bit better :)

```java
Automaton concatExample=AutomataUtil.concat(
    PrebuildAutomaton.BINARY_DIVIDABLE_BY_3,
    PrebuildAutomaton.TWO_CHARACTERS
);

new TerminalController(concatExample);
```

```java
Automaton automaton = MutableAutomaton.create()
    .addState("0",q->q
        .addTransition("0",0)
        .addTransition("1",1)
        .setFinal()
        .setInital()
    )
    .addState("1",q->q
        .addTransition("0",2)
        .addTransition("1",0)
    )
    .addState("2",q->q
        .addTransition("0",1)
        .addTransition("1",2)
    )
    .build().nda();

automaton.run("110");
```