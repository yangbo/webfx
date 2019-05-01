package webfx.fxkit.gwt.mapper.html.peer.javafxgraphics;

import elemental2.dom.HTMLCanvasElement;
import elemental2.dom.HTMLElement;
import javafx.scene.canvas.Canvas;
import webfx.fxkit.gwt.mapper.util.HtmlUtil;
import webfx.fxkit.mapper.spi.impl.peer.javafxgraphics.CanvasPeerBase;
import webfx.fxkit.mapper.spi.impl.peer.javafxgraphics.CanvasPeerMixin;

/**
 * @author Bruno Salmon
 */
public final class HtmlCanvasPeer
        <N extends Canvas, NB extends CanvasPeerBase<N, NB, NM>, NM extends CanvasPeerMixin<N, NB, NM>>

        extends HtmlNodePeer<N, NB, NM>
        implements CanvasPeerMixin<N, NB, NM> {

    public HtmlCanvasPeer() {
        this((NB) new CanvasPeerBase(), HtmlUtil.createElement("canvas"));
    }

    public HtmlCanvasPeer(NB base, HTMLElement element) {
        super(base, element);
    }

    @Override
    public void updateWidth(Number width) {
        ((HTMLCanvasElement) getElement()).width = width.intValue();
    }

    @Override
    public void updateHeight(Number height) {
        ((HTMLCanvasElement) getElement()).height = height.intValue();
    }

}
