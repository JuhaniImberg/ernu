package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.Math;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.parser.TokenType;

public class OperatorNode implements Node {
    private final Node left;
    private final Node right;
    private final TokenType operator;
    private String display;

    public OperatorNode(Node left, TokenType operator, Node right) {
        this.right = right;
        this.operator = operator;
        this.left = left;
        display = operator.getMatch();
        if (display.charAt(0) == '\\') {
            display = display.substring(1);
        }
    }

    @Override
    public String toString() {
        return "(" + display + " " + left + " " + right + ")";
    }

    private Node getMathValue(Node left, Node right) throws RuntimeException {
        if (!(left instanceof Math)) {
            throw new RuntimeException("left and right don't know math");
        }
        Math leftMath = (Math) left;
        Math rightMath = (Math) right;
        switch (operator) {
            case ADD:
                return leftMath.add(rightMath);
            case SUB:
                return leftMath.sub(rightMath);
            case DIV:
                return leftMath.div(rightMath);
            case MUL:
                return leftMath.mul(rightMath);
        }
        return new NullNode();
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node left = this.left.getValue(environment);
        Node right = this.right.getValue(environment);
        if (!(left.getClass().equals(right.getClass()))) {
            System.out.println(left.getClass() + " " + right.getClass());
            System.out.println(left + " " + right);
            System.out.println(this.left + " " + this.right);
            System.out.println(left.getClass().equals(right.getClass()));
            throw new RuntimeException("left and right don't share a class");
        }
        if (operator == TokenType.ADD ||
                operator == TokenType.SUB ||
                operator == TokenType.MUL ||
                operator == TokenType.DIV) {
            return getMathValue(left, right);
        }

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
}
