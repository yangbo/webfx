package naga.framework.router.auth.authz;

import naga.framework.services.authz.spi.impl.inmemory.AuthorizationRuleType;
import naga.framework.services.authz.spi.impl.inmemory.SimpleInMemoryAuthorizationRuleBase;

/**
 * @author Bruno Salmon
 */
public class RoutingAuthorizationRule extends SimpleInMemoryAuthorizationRuleBase<RouteRequest> {

    private final String route;
    private final boolean includeSubRoutes;

    public RoutingAuthorizationRule(AuthorizationRuleType type, String route, boolean includeSubRoutes) {
        super(type, RouteRequest.class);
        this.route = route;
        this.includeSubRoutes = includeSubRoutes;
    }

    @Override
    protected boolean matchRule(RouteRequest operationRequest) {
        String requestedRoute = operationRequest.getRoutePath();
        return requestedRoute.equals(route) || includeSubRoutes && requestedRoute.startsWith(route);
    }
}
