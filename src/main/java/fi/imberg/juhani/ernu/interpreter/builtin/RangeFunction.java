package fi.imberg.juhani.ernu.interpreter.builtin;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.node.*;

import java.util.ArrayList;
import java.util.List;

public class RangeFunction extends FunctionNode {
    public RangeFunction() {
        super(new ArrayList<Node>(),
                "Creates a range.",
                new BlockNode(new ArrayList<Node>()));
    }

    @Override
    public Node call(Environment environment, List<Node> arguments) throws RuntimeException {
        List<Node> values = new ArrayList<>();
        int to = ((IntegerNode) arguments.get(0)).getInteger();
        for (int i = 0; i < to; i++) {
            values.add(new IntegerNode(i));
        }
        return new ArrayNode(values);
    }
}