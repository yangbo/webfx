package naga.core.orm.expression.term;

import naga.core.orm.expression.Expression;
import naga.core.orm.expression.lci.DataWriter;
import naga.core.type.PrimType;
import naga.core.util.Booleans;

/**
 * @author Bruno Salmon
 */
public class Equals<T> extends BooleanExpression<T> {

    public Equals(Expression<T> left, Expression<T> right) {
        super(left, "=", right, 5);
    }

    public boolean evaluateCondition(Object a, Object b) {
        return PrimType.areEquivalent(a, b);
    }

    @Override
    public void setValue(T domainObject, Object value, DataWriter<T> dataWriter) {
        if (Booleans.isTrue(value))
            left.setValue(domainObject, right.evaluate(domainObject, dataWriter), dataWriter);
    }

}
