package fi.imberg.juhani.ernu.interpreter.interfaces;

import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

public interface Sequence {
    public int length();

    public Node get(int index) throws RuntimeException;

    public void set(int index, Node value) throws RuntimeException;

    public Node newEmpty();
}