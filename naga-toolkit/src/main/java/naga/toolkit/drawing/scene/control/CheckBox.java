package naga.toolkit.drawing.scene.control;

import naga.toolkit.drawing.scene.control.impl.CheckBoxImpl;
import naga.toolkit.properties.markers.HasSelectedProperty;

/**
 * @author Bruno Salmon
 */
public interface CheckBox extends ButtonBase,
        HasSelectedProperty {

    static CheckBox create() {
        return new CheckBoxImpl();
    }

}
