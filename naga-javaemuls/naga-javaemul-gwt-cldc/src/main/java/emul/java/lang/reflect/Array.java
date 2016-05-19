package emul.java.lang.reflect;

import naga.core.queryservice.QueryResultSet;
import naga.core.spi.platform.Platform;

public final class Array {

    public static Object newInstance(Class<?> componentType, int length) throws NegativeArraySizeException {
        if (componentType.equals(QueryResultSet.class)) return new QueryResultSet[length];
        Platform.log("GWT super source Array.newInstance() has no case for type " + componentType + ", so new Object[] is returned but this may cause a ClassCastException.");
        return new Object[length];
    }

}