package mongoose.activities.bothends.book.start;

import mongoose.activities.bothends.generic.routing.MongooseRoutingUtil;
import naga.framework.activity.base.combinations.viewdomain.impl.ViewDomainActivityContextFinal;
import naga.framework.ui.uirouter.UiRoute;

/**
 * @author Bruno Salmon
 */
public class StartBookingRouting {

    private final static String PATH = "/book/event/:eventId/start";

    public static UiRoute<?> uiRoute() {
        return UiRoute.create(PATH
                , false
                , StartBookingActivity::new
                , ViewDomainActivityContextFinal::new
        );
    }

    public static String getStartBookingPath(Object eventId) {
        return MongooseRoutingUtil.interpolateEventIdInPath(eventId, PATH);
    }
}
