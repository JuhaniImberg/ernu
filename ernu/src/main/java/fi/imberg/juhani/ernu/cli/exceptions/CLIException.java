package fi.imberg.juhani.ernu.cli.exceptions;

import fi.imberg.juhani.ernu.ErnuException;

/**
 * Generic CLI exception class.
 */
public class CLIException extends ErnuException {
    public CLIException(String string) {
        super("\033[31m" + string + "\033[0m");
    }
}
