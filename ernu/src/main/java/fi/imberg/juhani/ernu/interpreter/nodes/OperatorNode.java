package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Appendable;
import fi.imberg.juhani.ernu.interpreter.interfaces.Mathable;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.TokenType;

/**
 * Represents an operation on two things, such as 3 + 2.
 */
public class OperatorNode implements Node {
    /**
     * The left node in the operation
     */
    private final Node left;
    /**
     * The right node in the operation
     */
    private final Node right;
    /**
     * The type of operation that this operation does
     */
    private final TokenType operator;
    /**
     * How to display the operator when printing
     */
    private String display;

    /**
     * @param left     The left node in the operation
     * @param operator The type of operation that this operation does
     * @param right    The right node in the operation
     */
    public OperatorNode(Node left, TokenType operator, Node right) {
        this.right = right;
        this.operator = operator;
        this.left = left;
        display = operator.getMatch();
        if (display.contains("|")) {
            display = display.split("\\|", 2)[1];
        }
        display = display.replaceAll("\\\\", "");
    }

    @Override
    public String toString() {
        return "(" + display + " " + left + " " + right + ")";
    }

    private Node getMathValue(Node left, Node right) throws RuntimeException {
        if (!(left instanceof Mathable)) {
            throw new RuntimeException("left and right don't know math");
        }
        Mathable leftMathable = (Mathable) left;
        Mathable rightMathable = (Mathable) right;
        switch (operator) {
            case ADD:
            case ADDSET:
                return leftMathable.add(rightMathable);
            case SUB:
            case SUBSET:
                return leftMathable.sub(rightMathable);
            case DIV:
            case DIVSET:
                return leftMathable.div(rightMathable);
            case MUL:
            case MULSET:
                return leftMathable.mul(rightMathable);
            case MOD:
            case MODSET:
                return leftMathable.mod(rightMathable);
        }
        return new NullNode();
    }

    private Node getCompareValue(Node left, Node right) throws RuntimeException {
        if (!(left instanceof Comparable)) {
            throw new RuntimeException("left and right can't be compared");
        }

        int cmp = ((Comparable) left).compareTo(right);
        boolean truth = false;

        switch (operator) {
            case EQ:
                truth = (cmp == 0);
                break;
            case NOTEQ:
                truth = (cmp != 0);
                break;
            case LT:
                truth = (cmp < 0);
                break;
            case GT:
                truth = (cmp > 0);
                break;
            case LTOE:
                truth = (cmp <= 0);
                break;
            case GTOE:
                truth = (cmp >= 0);
                break;
        }

        return new BooleanNode(truth);
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node left = this.left.getValue(environment).getValue(environment);
        Node right = this.right.getValue(environment).getValue(environment);
        if (operator == TokenType.ADD || operator == TokenType.ADDSET) {
            if (left instanceof Appendable || right instanceof Appendable) {
                return getAppendValue(left, right);
            }
        }
        if (!(left.getClass().equals(right.getClass()))) {
            throw new RuntimeException("left and right don't share a class, got: " + left + " and " + right);
        }
        if (operator == TokenType.ADD ||
                operator == TokenType.SUB ||
                operator == TokenType.MUL ||
                operator == TokenType.DIV ||
                operator == TokenType.MOD ||
                operator == TokenType.ADDSET ||
                operator == TokenType.SUBSET ||
                operator == TokenType.DIVSET ||
                operator == TokenType.MULSET ||
                operator == TokenType.MODSET) {
            return getMathValue(left, right);
        } else if (operator == TokenType.EQ ||
                operator == TokenType.NOTEQ ||
                operator == TokenType.GT ||
                operator == TokenType.GTOE ||
                operator == TokenType.LT ||
                operator == TokenType.LTOE) {
            return getCompareValue(left, right);
        }
        if (left instanceof BooleanNode) {
            BooleanNode leftBoolean = (BooleanNode) left;
            BooleanNode rightBoolean = (BooleanNode) right;
            if (operator == TokenType.AND) {
                return new BooleanNode(leftBoolean.isTrue() && rightBoolean.isTrue());
            } else if (operator == TokenType.OR) {
                return new BooleanNode(leftBoolean.isTrue() || rightBoolean.isTrue());
            }
        }
        return new NullNode();
    }

    private Node getAppendValue(Node left, Node right) {
        if (left instanceof Appendable) {
            return ((Appendable) left).append(right);
        } else {
            return ((Appendable) right).prepend(left);
        }
    }
}
