package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Appendable;
import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.Sequence;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;

import java.util.ArrayList;
import java.util.List;

public class ArrayNode implements Node, Sequence, Appendable {
    private final List<Node> values;

    public ArrayNode() {
        this.values = new ArrayList<>();
    }

    public ArrayNode(List<Node> values) {
        this.values = values;
    }

    @Override
    public void set(int index, Node value) {
        while (values.size() <= index) {
            values.add(new NullNode());
        }
        values.set(index, value);
    }

    @Override
    public Node newEmpty() {
        return new ArrayNode();
    }

    @Override
    public Node get(int index) throws RuntimeException {
        return values.get(index);
    }

    @Override
    public String toString() {
        return values.toString();
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return this;
    }

    @Override
    public int length() {
        return values.size();
    }

    public List<Node> getValues() {
        return values;
    }

    @Override
    public Node append(Node node) {
        ArrayNode newArray = new ArrayNode(new ArrayList<>(values));
        if (node instanceof ArrayNode) {
            ArrayNode other = (ArrayNode) node;
            for (Node value : other.getValues()) {
                newArray.appendNode(value);
            }
        } else {
            newArray.appendNode(node);
        }

        return newArray;
    }

    public void appendNode(Node node) {
        set(length(), node);
    }

    public void prependNode(Node node) {
        values.add(0, node);
    }

    @Override
    public Node prepend(Node node) {
        ArrayNode newArray = new ArrayNode(new ArrayList<>(values));
        if (node instanceof ArrayNode) {
            ArrayNode other = (ArrayNode) node;
            for (int i = other.length() - 1; i >= 0; i--) {
                Node value = other.getValues().get(i);
                newArray.prependNode(value);
            }
        } else {
            newArray.prependNode(node);
        }

        return newArray;
    }
}
