package naga.core.spi.gui.hasproperties;

import javafx.beans.property.Property;
import naga.core.spi.gui.GuiNode;

/**
 * @author Bruno Salmon
 */
public interface HasNodeProperty<N> {

    Property<GuiNode<N>> nodeProperty();
    default HasNodeProperty setNode(GuiNode node) { nodeProperty().setValue(node); return this; }
    default GuiNode<N> getNode() { return nodeProperty().getValue(); }
}
