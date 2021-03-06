package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BlockNode;
import fi.imberg.juhani.ernu.interpreter.nodes.IfNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses an expression for an if statement. For example if i == 0 do print(i) end
 */
public class IfParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        Node condition = parser.parseNode();
        List<Node> branch1 = new ArrayList<>();
        List<Node> branch2 = new ArrayList<>();

        parser.consume(TokenType.DO);
        parser.match(TokenType.EOL);

        if (!parser.match(TokenType.END)) {
            do {
                if (parser.isNext(TokenType.ELSE)) {
                    break;
                }
                branch1.add(parser.parseNode());
                parser.match(TokenType.EOL);
            } while (!parser.match(TokenType.END));
        }

        if (parser.match(TokenType.ELSE)) {
            parser.match(TokenType.EOL);
            if (!parser.match(TokenType.END)) {
                do {
                    branch2.add(parser.parseNode());
                    parser.match(TokenType.EOL);
                } while (!parser.match(TokenType.END));
            }
        }

        return new IfNode(condition, new BlockNode(branch1), new BlockNode(branch2));
    }
}
