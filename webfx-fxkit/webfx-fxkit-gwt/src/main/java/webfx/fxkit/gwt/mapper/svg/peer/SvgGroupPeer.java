package webfx.fxkit.gwt.mapper.svg.peer;

import elemental2.dom.Element;
import webfx.fxkit.gwt.mapper.util.SvgUtil;
import emul.javafx.scene.Group;
import webfx.fxkit.mapper.spi.impl.peer.GroupPeerMixin;
import webfx.fxkit.mapper.spi.impl.peer.GroupPeerBase;

/**
 * @author Bruno Salmon
 */
public final class SvgGroupPeer
        <N extends Group, NB extends GroupPeerBase<N, NB, NM>, NM extends GroupPeerMixin<N, NB, NM>>

        extends SvgNodePeer<N, NB, NM>
        implements GroupPeerMixin<N, NB, NM> {

    public SvgGroupPeer() {
        this((NB) new GroupPeerBase(), SvgUtil.createSvgGroup());
    }

    public SvgGroupPeer(NB base, Element element) {
        super(base, element);
    }
}
