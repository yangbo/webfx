package naga.platform.activity;

/**
 * @author Bruno Salmon
 */
public interface ActivityContext
        <THIS extends ActivityContext<THIS>>

        extends HasActivityContextFactory<THIS> {

    ActivityContext getParentContext();

    ActivityManager<THIS> getActivityManager();

    @Override
    ActivityContextFactory<THIS> getActivityContextFactory();

    static ActivityContextFinal create(ActivityContext parent) {
        return new ActivityContextFinal(parent, ActivityContext::create);
    }
}
