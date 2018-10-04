package webfx.framework.shared.expression.terms.function;

import webfx.fxkit.extra.type.Type;

/**
 * @author Bruno Salmon
 */
public final class DomainClassType implements Type {

    private final Object domainClass;

    public DomainClassType(Object domainClass) {
        this.domainClass = domainClass;
    }

    public Object getDomainClass() {
        return domainClass;
    }
}
