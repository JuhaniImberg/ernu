package fi.imberg.juhani.ernu.interpreter;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.interpreter.node.NullNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Tokenizer;

import java.util.Scanner;

public class REPL {
    private final Scanner scanner;
    private final Tokenizer tokenizer;
    private final Parser parser;
    private final Environment environment;

    public REPL() {
        this.scanner = new Scanner(System.in);
        this.tokenizer = new Tokenizer();
        this.parser = new Parser(tokenizer);
        this.environment = new Environment("repl");
    }

    private String read() {
        System.out.print("<(^_^)> ");
        return scanner.nextLine() + "\n\n";
    }

    private Node eval(String source) throws ErnuException {
        tokenizer.tokenize(source);
        Node node = new NullNode();
        while (!tokenizer.isEmpty()) {
            Node current = parser.parseNode();
            if (current != null) {
                node.getValue(environment);
                node = current;
            }
        }
        return node;
    }

    private void print(Node node) throws RuntimeException {
        System.out.println(node.getValue(this.environment).toString());
    }

    public void loop() {
        while (true) {
            try {
                print(eval(read()));
            } catch (ErnuException e) {
                e.printStackTrace();
                while (!tokenizer.isEmpty()) {
                    tokenizer.nextToken();
                }
            }
        }
    }
}
