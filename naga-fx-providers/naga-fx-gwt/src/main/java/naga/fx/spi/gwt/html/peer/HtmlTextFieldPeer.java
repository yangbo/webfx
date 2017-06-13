package naga.fx.spi.gwt.html.peer;

import elemental2.dom.HTMLElement;
import emul.javafx.geometry.Pos;
import emul.javafx.scene.control.TextField;
import naga.fx.spi.gwt.util.HtmlUtil;
import naga.fx.spi.peer.base.TextFieldPeerBase;
import naga.fx.spi.peer.base.TextFieldPeerMixin;

/**
 * @author Bruno Salmon
 */
public class HtmlTextFieldPeer
        <N extends TextField, NB extends TextFieldPeerBase<N, NB, NM>, NM extends TextFieldPeerMixin<N, NB, NM>>

        extends HtmlTextInputControlPeer<N, NB, NM>
        implements TextFieldPeerMixin<N, NB, NM>, HtmlLayoutMeasurable {

    public HtmlTextFieldPeer() {
        this((NB) new TextFieldPeerBase(), HtmlUtil.createTextInput());
    }

    public HtmlTextFieldPeer(NB base, HTMLElement element) {
        super(base, element);
    }

    @Override
    public void updateAlignment(Pos alignment) {
        String textAlign = null;
        if (alignment != null)
            switch (alignment.getHpos()) {
                case LEFT: textAlign = "left"; break;
                case CENTER: textAlign = "center"; break;
                case RIGHT: textAlign = "right"; break;
            }
        setElementStyleAttribute("text-align", textAlign);
    }
}
