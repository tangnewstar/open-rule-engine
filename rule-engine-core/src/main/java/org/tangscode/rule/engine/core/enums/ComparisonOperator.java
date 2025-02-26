package org.tangscode.rule.engine.core.enums;

import java.util.function.BiPredicate;

/**
 * @author tangxinxing
 * @version 1.0
 * @description 比较操作符
 * @date 2025/2/17
 */
public enum ComparisonOperator {
    EQ("==", Object::equals),
    NOT_EQUALS("!=", (a, b) -> !a.equals(b)),
    GT(">", (a, b) -> ((Comparable) a).compareTo(b) > 0),
    GTE(">=", (a, b) -> ((Comparable) a).compareTo(b) >= 0),
    LT("<", (a, b) -> ((Comparable) a).compareTo(b) < 0),
    LTE("<=", (a, b) -> ((Comparable) a).compareTo(b) <= 0);

    private final String literal;
    private final BiPredicate<Object, Object> predicate;

    ComparisonOperator(String literal, BiPredicate<Object, Object> predicate) {
        this.literal = literal;
        this.predicate = predicate;
    }

    public String getLiteral() {
        return literal;
    }

    public BiPredicate<Object, Object> getPredicate() {
        return predicate;
    }

    public boolean apply(Object a, Object b) {
        if (!(a instanceof Comparable) || !(b instanceof Comparable)) {
            throw new IllegalArgumentException("Not a comparable");
        }
        return predicate.test(a, b);
    }
}
