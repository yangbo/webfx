package webfx.fxkit.gwt.mapper.html.peer;

import com.googlecode.gwt.charts.client.corechart.CoreChartWidget;
import webfx.fxkit.extra.chart.ScatterChart;
import webfx.fxkit.extra.mapper.spi.peer.impl.ScatterChartPeerBase;
import webfx.fxkit.extra.mapper.spi.peer.impl.ScatterChartPeerMixin;

/**
 * @author Bruno Salmon
 */
public final class HtmlScatterChartPeer
        <C, N extends ScatterChart, NB extends ScatterChartPeerBase<C, N, NB, NM>, NM extends ScatterChartPeerMixin<C, N, NB, NM>>
        extends HtmlChartPeer<C, N, NB, NM>
        implements ScatterChartPeerMixin<C, N, NB, NM> {

    public HtmlScatterChartPeer() {
        this((NB) new ScatterChartPeerBase());
    }

    public HtmlScatterChartPeer(NB base) {
        super(base);
    }

    @Override
    protected CoreChartWidget createChartWidget() {
        return new com.googlecode.gwt.charts.client.corechart.ScatterChart();
    }
}
