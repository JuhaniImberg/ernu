package fi.imberg.juhani.ernu.lang;

public class Token {
    private int line;
    private int column;
    private int length;
    private String content;
    private TokenType type;

    public Token(int line, int column, String content) {
        this.line = line;
        this.column = column;
        this.length = content.length();
        this.content = content;
        this.type = TokenType.NONE;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getLength() {
        return length;
    }

    public String getContent() {
        return content;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public void add(char c) {
        length++;
        content += c;
    }

    public void add(String chars) {
        for (char c : chars.toCharArray()) {
            add(c);
        }
    }

    public void moveColumn() {
        column++;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public String toString() {
        return String.format("%3d:%-3d%20s    %s",
                this.line,
                this.column,
                this.type.toString(),
                (this.content.equals("\n") ? "<EOL>" : this.content)
        );
    }
}
