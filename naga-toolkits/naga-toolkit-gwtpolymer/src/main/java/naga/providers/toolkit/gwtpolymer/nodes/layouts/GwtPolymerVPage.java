package naga.providers.toolkit.gwtpolymer.nodes.layouts;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import com.vaadin.polymer.Polymer;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import naga.providers.toolkit.gwt.nodes.GwtNode;
import naga.toolkit.spi.nodes.GuiNode;
import naga.toolkit.spi.nodes.layouts.VPage;

/**
 * @author Bruno Salmon
 */
public class GwtPolymerVPage extends GwtNode<Panel> implements VPage {

    public GwtPolymerVPage() {
        this(new LayoutFlowPanel());
    }

    public GwtPolymerVPage(Panel node) {
        super(node);
        node.getElement().addClassName("layout vertical");
        node.getElement().setAttribute("style", "width: 100%");
        Polymer.importHref("iron-flex-layout/iron-flex-layout.html", o -> {
            Document doc = Document.get();
            StyleElement styleElement = doc.createStyleElement();
            styleElement.setAttribute("is", "custom-style");
            styleElement.setAttribute("include", "iron-flex iron-flex-alignment");
            doc.getHead().insertFirst(styleElement);
            return null;
        });
        ChangeListener<GuiNode> onAnyNodePropertyChange = (observable, oldValue, newValue) -> updateNodesOnceAttached();
        headerProperty.addListener(onAnyNodePropertyChange);
        footerProperty.addListener(onAnyNodePropertyChange);
        centerProperty.addListener(onAnyNodePropertyChange);
        updateNodesOnceAttached();
    }

    private HandlerRegistration attachHandlerRegistration;

    private void updateNodesOnceAttached() {
        if (!node.isAttached() && attachHandlerRegistration == null)
            attachHandlerRegistration = node.addAttachHandler(event -> updateNodes());
        else
            updateNodes();
    }

    private void updateNodes() {
        if (attachHandlerRegistration != null) {
            attachHandlerRegistration.removeHandler();
            attachHandlerRegistration = null;
        }
        node.clear();
        GuiNode topPropertyValue = headerProperty.getValue();
        if (topPropertyValue != null)
            node.add(topPropertyValue.unwrapToNativeNode());
        GuiNode centerPropertyValue = centerProperty.getValue();
        if (centerPropertyValue != null) {
            Widget widget = centerPropertyValue.unwrapToNativeNode();
            widget.setWidth("100%");
            widget.setHeight("100%");
            widget.addStyleName("flex");
            node.add(widget);
        }
        GuiNode bottomPropertyValue = footerProperty.getValue();
        if (bottomPropertyValue != null)
            node.add(bottomPropertyValue.unwrapToNativeNode());
    }

    private final Property<GuiNode> headerProperty = new SimpleObjectProperty<>();

    @Override
    public Property<GuiNode> headerProperty() {
        return headerProperty;
    }

    private final Property<GuiNode> centerProperty = new SimpleObjectProperty<>();

    @Override
    public Property<GuiNode> centerProperty() {
        return centerProperty;
    }

    private final Property<GuiNode> footerProperty = new SimpleObjectProperty<>();

    @Override
    public Property<GuiNode> footerProperty() {
        return footerProperty;
    }
}
