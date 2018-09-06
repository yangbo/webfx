package webfx.platforms.core.services.bus.spi.impl;

import webfx.platforms.core.services.bus.Bus;
import webfx.platforms.core.services.bus.BusOptions;
import webfx.platforms.core.services.bus.call.ThreadLocalBusContext;
import webfx.platforms.core.services.bus.spi.BusServiceProvider;

/**
 * @author Bruno Salmon
 */
public abstract class BusServiceProviderBase implements BusServiceProvider {

    private static Bus BUS;
    private static BusOptions busOptions;

    public Bus bus() {
        Bus bus = ThreadLocalBusContext.getThreadLocalBus();
        if (bus != null)
            return bus;
        if (BUS == null)
            BUS = createBus();
        return BUS;
    }

    public BusOptions getBusOptions() {
        return busOptions;
    }

    public void setBusOptions(BusOptions busOptions) {
        BusServiceProviderBase.busOptions = busOptions;
    }

    public Bus createBus() {
        if (busOptions == null)
            busOptions = createBusOptions();
        return createBus(busOptions);
    }

    public Bus createBus(BusOptions options) {
        setPlatformBusOptions(options);
        Bus bus = busFactory().createBus(options);
        if (BUS == null)
            BUS = bus;
        return bus;
    }

}
