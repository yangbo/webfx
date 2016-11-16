package naga.providers.toolkit.javafx.nodes.controls;

import javafx.beans.property.Property;
import javafx.scene.control.TextField;
import naga.providers.toolkit.javafx.nodes.FxNode;

/**
 * @author Bruno Salmon
 */
public class FxTextField extends FxNode<TextField> implements naga.toolkit.spi.nodes.controls.TextField {

    public FxTextField() {
        this(createTextField());
    }

    public FxTextField(TextField textField) {
        super(textField);
    }

    private static TextField createTextField() {
        return new TextField();
    }

    @Override
    public Property<String> textProperty() {
        return node.textProperty();
    }

    @Override
    public Property<String> placeholderProperty() {
        return node.promptTextProperty();
    }
}
