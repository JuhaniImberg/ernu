package fi.imberg.juhani.ernu.interpreter.exceptions;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

/**
 * This isn't really an exception, rather it's just used to return a value up.
 */
public class ReturnException extends RuntimeException {
    /**
     * The node that's returned to the function.
     */
    private final Node value;

    /**
     * @param value The node that's returned to the function.
     */
    public ReturnException(Node value) {
        super("this is not an exception");
        this.value = value;
    }

    public Node getValue() {
        return value;
    }
}
