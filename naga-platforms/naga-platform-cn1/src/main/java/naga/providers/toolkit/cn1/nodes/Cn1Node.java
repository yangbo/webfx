package naga.providers.toolkit.cn1.nodes;

import com.codename1.ui.Component;
import naga.toolkit.spi.nodes.GuiNode;


/**
 * @author Bruno Salmon
 */
public class Cn1Node<N extends Component> implements GuiNode {

    protected final N node;

    public Cn1Node(N node) {
        this.node = node;
    }

    @Override
    public N unwrapToNativeNode() {
        return node;
    }

    @Override
    public void requestFocus() {
        node.requestFocus();
    }

}
