package naga.providers.toolkit.javafx.nodes;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import naga.toolkit.spi.nodes.SelectableDisplayResultSetNode;
import naga.toolkit.properties.markers.SelectionMode;
import naga.toolkit.display.DisplaySelection;

/**
 * @author Bruno Salmon
 */
public abstract class FxSelectableDisplayResultSetNode<N extends Node> extends FxDisplayResultSetNode<N> implements SelectableDisplayResultSetNode {

    public FxSelectableDisplayResultSetNode(N node) {
        super(node);
        syncVisualSelectionMode(getSelectionMode());
        selectionModeProperty.addListener((observable, oldValue, newValue) -> syncVisualSelectionMode(newValue));
    }

    private final Property<SelectionMode> selectionModeProperty = new SimpleObjectProperty<>(SelectionMode.DISABLED);
    @Override
    public Property<SelectionMode> selectionModeProperty() {
        return selectionModeProperty;
    }

    protected abstract void syncVisualSelectionMode(SelectionMode selectionMode);

    private final Property<DisplaySelection> displaySelectionProperty = new SimpleObjectProperty<>();
    @Override
    public Property<DisplaySelection> displaySelectionProperty() {
        return displaySelectionProperty;
    }

}
