package webfx.framework.shared.expression.sqlcompiler.terms;

import webfx.framework.shared.expression.terms.*;
import webfx.fxkit.extra.type.Types;

/**
 * @author Bruno Salmon
 */
public class BinaryExpressionSqlCompiler<E extends BinaryExpression> extends AbstractTermSqlCompiler<E> {

    public BinaryExpressionSqlCompiler() {
        super(Divide.class, Minus.class, Multiply.class, Plus.class);
    }

    protected BinaryExpressionSqlCompiler(Class... acceptableExpressionClasses) {
        super(acceptableExpressionClasses);
    }

    @Override
    public void compileExpressionToSql(BinaryExpression e, Options o) {
        compileChildExpressionToSql(e.getLeft(), o);
        String sqlSeparator = getSqlSeparator(e, o);
        compileChildExpressionToSql(e.getRight(), o.changeSeparator(sqlSeparator));
    }

    protected String getSqlSeparator(BinaryExpression e, Options o) {
        if (e instanceof Plus && Types.isStringType(e.getType()))
            return " || "; // String concatenation for Postgres
        return e.getSeparator();
    }
}
