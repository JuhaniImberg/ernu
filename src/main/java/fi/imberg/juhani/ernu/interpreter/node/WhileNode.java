package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

public class WhileNode implements Node {
    private final Node conditional;
    private final BlockNode body;

    public WhileNode(Node conditional, BlockNode body) {
        this.conditional = conditional;
        this.body = body;
    }

    @Override
    public String toString() {
        return "(while " + conditional + " " + body + ")";
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node valueNode = new NullNode();
        while(true) {
            Node truth = conditional.getValue(environment);
            if (!(truth instanceof BooleanNode)) {
                throw new RuntimeException("Conditional is not a boolean");
            }
            if(!((BooleanNode) truth).isTrue()) {
                break;
            }
            valueNode = body.getValue(environment);
        }
        return valueNode;
    }
}
