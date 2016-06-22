package naga.core.ui.displayresultset;

import naga.core.format.Formatter;
import naga.core.format.FormatterRegistry;
import naga.core.json.Json;
import naga.core.json.JsonObject;
import naga.core.orm.domainmodel.DomainModel;
import naga.core.orm.domainmodel.Label;
import naga.core.orm.expression.Expression;
import naga.core.type.Type;

/**
 * @author Bruno Salmon
 */
public class ExpressionColumn {

    private final String expressionDefinition;
    private Expression  expression;
    private final Formatter expressionFormatter;
    private Object label;
    private DisplayColumn displayColumn;

    private ExpressionColumn(String expressionDefinition, Expression expression, Object label, Formatter expressionFormatter) {
        this.expressionDefinition = expressionDefinition;
        this.expression = expression;
        this.label = label;
        this.expressionFormatter = expressionFormatter;
    }

    public DisplayColumn getDisplayColumn() {
        if (displayColumn == null) {
            if (label == null)
                label = Label.from(expression);
            Type expectedType = expressionFormatter != null ? expressionFormatter.getExpectedFormattedType() : expression.getType();
            displayColumn = new DisplayColumn(label, expectedType);
        }
        return displayColumn;
    }

    public Expression getExpression() {
        return expression;
    }

    public Formatter getExpressionFormatter() {
        return expressionFormatter;
    }

    public void parseIfNecessary(DomainModel domainModel, Object classId) {
        if (expression == null)
            expression = domainModel.parseExpression(expressionDefinition, classId);
    }

    public static ExpressionColumn create(String jsonOrExpressionDefinition) {
        if (jsonOrExpressionDefinition.startsWith("{"))
            return create(Json.parseObject(jsonOrExpressionDefinition));
        return new ExpressionColumn(jsonOrExpressionDefinition, null, null, null);
    }

    public static ExpressionColumn create(JsonObject json) {
        return create(json.getString("expression"), json);
    }

    public static ExpressionColumn create(String expressionDefinition, String jsonOptions) {
        return create(expressionDefinition, Json.parseObject(jsonOptions));
    }

    public static ExpressionColumn create(String expressionDefinition, JsonObject options) {
        return new ExpressionColumn(expressionDefinition, null, options.get("label"), FormatterRegistry.getFormatter(options.getString("format")));
    }

    public static ExpressionColumn create(String expressionDefinition, Formatter expressionFormatter) {
        return new ExpressionColumn(expressionDefinition, null, null, expressionFormatter);
    }

    public static ExpressionColumn create(Expression expression) {
        return new ExpressionColumn(null, expression, null, null);
    }

    public static ExpressionColumn[] fromExpressions(Expression[] columnExpressions) {
        ExpressionColumn[] expressionColumns = new ExpressionColumn[columnExpressions.length];
        int columnIndex = 0;
        for (Expression columnExpression : columnExpressions)
            expressionColumns[columnIndex++] = ExpressionColumn.create(columnExpression);
        return expressionColumns;
    }

    public static ExpressionColumn[] fromExpressionsDefinition(String columnExpressionsDefinition, DomainModel domainModel, Object classId) {
        return fromExpressions(domainModel.parseExpressionArray(columnExpressionsDefinition, classId).getExpressions());
    }
}
