package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.interpreter.builtin.*;

/**
 * Describes an environment with the builtin functions already in.
 */
public class BuiltinEnvironment extends Environment {
    public BuiltinEnvironment() {
        super(null, "builtin");
        addSymbol("print", new PrintFunction());
        addSymbol("range", new RangeFunction());
        addSymbol("len", new LenFunction());
        addSymbol("help", new HelpFunction());
        addSymbol("type", new TypeFunction());
        addSymbol("defined", new DefinedFunction());
        addSymbol("apply", new ApplyFunction());
        addSymbol("str", new StrFunction());
        addSymbol("num", new NumFunction());
    }
}
