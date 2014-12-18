package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.node.Node;

public class GroupingParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        Node node = parser.parseNode();
        parser.consume(TokenType.RPAREN);
        return node;
    }
}
