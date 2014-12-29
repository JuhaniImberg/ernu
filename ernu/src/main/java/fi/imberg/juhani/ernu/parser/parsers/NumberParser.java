package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.NumberNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

/**
 * Parses an expression for a number. For example 8
 */
public class NumberParser implements PrefixParser {

    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        return new NumberNode(token.getContent());
    }
}
