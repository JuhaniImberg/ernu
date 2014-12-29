package fi.imberg.juhani.ernu.parser.exceptions;


import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;

/**
 * Exception for when we expect some other token type than the one that was met.
 */
public class UnexpectedTokenException extends LangException {
    public UnexpectedTokenException(TokenType expected, Token got) {
        super(String.format("Unexpected token met while parsing. Expected %s but got %s instead. Near %d:%d",
                expected, got.getType(), got.getLine() + 1, got.getColumn() + 1));
    }
}
