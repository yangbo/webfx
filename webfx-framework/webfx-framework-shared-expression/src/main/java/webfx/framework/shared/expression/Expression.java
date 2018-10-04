package webfx.framework.shared.expression;

import webfx.framework.shared.expression.lci.DataReader;
import webfx.framework.shared.expression.lci.DataWriter;
import webfx.fxkit.extra.type.Type;

import java.util.Collection;

/**
 * Base interface for any expression and terms operating on domain objects.
 *
 * @param <T> The expected java class for domain objects.
 *
 * @author Bruno Salmon
 */
public interface Expression<T> {

    /**
     * @return the type of the expression (for example PrimType.BOOLEAN in case of a boolean expression).
     */

    Type getType();

    /**
     * The method that evaluates the expression on a domain object.
     *
     * @param domainObject the domain object on which the evaluation is computed
     * @param dataReader the loose coupling interface for reading data (domain fields and parameters)
     * @return the result of the evaluation. Its java class should be compliant with the type returned by getType().
     */

    Object evaluate(T domainObject, DataReader<T> dataReader);


    /*****************************************************************************
     *  Editing methods (needs to be overridden when the expression is editable) *
     ****************************************************************************/

    /**
     * @return true if the expression is editable
     */

    default boolean isEditable() { return true; }

    /**
     * Change the value of the expression (assuming the expression is editable).
     *
     * @param domainObject the domain object on which the evaluation is computed
     * @param value the new value to set. Its java class should be compliant with the type returned by getType().
     * @param dataWriter the loose coupling interface for writing data (domain fields and parameters)
     */

    default void setValue(T domainObject, Object value, DataWriter<T> dataWriter) {}


    /*********************
     *  Printing methods *
     ********************/

    /**
     * Method called by AbstractExpressions.toString() to optimize the String construct by passing the StringBuilder.
     * @param sb The StringBuilder to be used for the String construct
     * @return The StringBuilder so append() methods can be chained
     */

    StringBuilder toString(StringBuilder sb);

    /**
     * @return The precedence level of the expression, which will determine if parenthesis are needed when printing.
     * For example: x * (y + z) => parenthesis because Plus precedence level (=6) < Multiply precedence level (=7)
     */

    int getPrecedenceLevel();

    /**********************
     *  Collecting method *
     *********************/

    /**
     * This method will collect all persistent terms of the expression and add them into the passed collection. For
     * example, calling this method on 2 * (a + b) will collect a and b.
     *
     * @param persistentTerms the collection where persistent terms will be added.
     */

    void collectPersistentTerms(Collection<Expression<T>> persistentTerms);
}
