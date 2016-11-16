package naga.toolkit.spi.nodes;

import naga.toolkit.properties.markers.HasDisplaySelectionProperty;
import naga.toolkit.properties.markers.HasSelectionModeProperty;

/**
 * @author Bruno Salmon
 */
public interface SelectableDisplayResultSetNode extends DisplayResultSetNode,
        HasDisplaySelectionProperty,
        HasSelectionModeProperty {
}
