package fi.imberg.juhani.ernu.cli.command;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.ErnuParser;
import fi.imberg.juhani.ernu.parser.Tokenizer;
import fi.imberg.juhani.ernu.util.Range;

/**
 * This command shows the parsed nodes from a file.
 */
public class ParseCommand extends FileCommand {
    @Override
    public void call(String[] args) throws ErnuException {
        Tokenizer tokenizer = new Tokenizer();
        ErnuParser parser = new ErnuParser(tokenizer);
        for (String fileName : args) {
            handleFile(parser, tokenizer, fileName);
        }
    }

    private void handleFile(ErnuParser parser, Tokenizer tokenizer, String fileName) throws ErnuException {
        tokenizer.tokenize(fileToString(fileName) + "\n\n");
        while (!tokenizer.isEmpty()) {
            Node node = parser.parseNode();
            if (node != null) {
                System.out.println(node);
            }
        }
    }


    @Override
    public Range getRange() {
        return new Range(1);
    }

    @Override
    public String getCommand() {
        return "parse";
    }

    @Override
    public String getDescription() {
        return "Prints a tree structure representation of the source.";
    }
}
