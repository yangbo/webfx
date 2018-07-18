package naga.fx.properties.markers;

import emul.javafx.beans.property.DoubleProperty;

/**
 * @author Bruno Salmon
 */
public interface HasAngleProperty {

    DoubleProperty angleProperty();
    default void setAngle(Number angle) { angleProperty().setValue(angle); }
    default Double getAngle() { return angleProperty().getValue(); }

}
