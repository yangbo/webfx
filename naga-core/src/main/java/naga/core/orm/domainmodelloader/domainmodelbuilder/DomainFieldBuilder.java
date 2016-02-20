package naga.core.orm.domainmodelloader.domainmodelbuilder;

import naga.core.orm.domainmodel.DomainClass;
import naga.core.orm.domainmodel.DomainField;
import naga.core.orm.domainmodel.Label;
import naga.core.orm.expression.Expression;
import naga.core.orm.expressionparser.expressionbuilder.term.ExpressionBuilder;
import naga.core.orm.expressionsqlcompiler.ExpressionSqlCompiler;
import naga.core.type.Type;

/**
 * @author Bruno Salmon
 */
public class DomainFieldBuilder {
    public DomainClassBuilder classBuilder;
    public DomainClass domainClass;
    public Object modelId;
    public Object id;
    public String name;
    public String expressionDefinition;
    public ExpressionBuilder expressionBuilder;
    public Expression expression;
    public String applicableConditionDefinition;
    public Type type;
    public boolean persistent = true;
    public String sqlColumnName;
    public Label label;
    public int prefWidth;
    public DomainClass foreignClass;
    public String foreignAlias;
    public String foreignCondition;
    public String foreignOrderBy;
    public String foreignComboFields;
    public String foreignTableFields;

    private DomainField field;  // Field or Alias or Argument

    public DomainFieldBuilder(String name) {
        this.name = name;
    }

    public DomainField build() {
        if (field == null) {
            if (id == null)
                id = name;
            if (modelId == null)
                modelId = id;
            if (domainClass != null)
                field = domainClass.getField(id);
            if (expression == null && expressionBuilder != null) {
                expressionBuilder.buildingClass = domainClass;
                expression = expressionBuilder.build();
            }
            if (label == null)
                label = new Label(name);
            if (sqlColumnName == null && persistent && expression == null && expressionDefinition == null)
                sqlColumnName = ExpressionSqlCompiler.toSqlString(foreignClass == null ? name : name + "Id");
            field = new DomainField(domainClass, modelId, id, name, label, persistent, type, sqlColumnName, expression, expressionDefinition, applicableConditionDefinition, prefWidth, foreignClass, foreignAlias, foreignCondition, null, foreignOrderBy, foreignComboFields, foreignTableFields);
        }
        return field;
    }

}
