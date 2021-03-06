package fi.imberg.juhani.ernu.cli.command;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.repl.REPL;
import fi.imberg.juhani.ernu.repl.terminal.TerminalREPL;
import fi.imberg.juhani.ernu.util.Range;

/**
 * This command starts a REPL.
 */
public class REPLCommand implements Command {
    @Override
    public void call(String[] args) throws ErnuException {
        (new REPL(new TerminalREPL())).start();
    }

    @Override
    public Range getRange() {
        return new Range(0, 0);
    }

    @Override
    public String getCommand() {
        return "repl";
    }

    @Override
    public String getDescription() {
        return "Start a REPL.";
    }
}
