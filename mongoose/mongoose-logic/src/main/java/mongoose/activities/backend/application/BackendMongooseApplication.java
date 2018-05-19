package mongoose.activities.backend.application;

import mongoose.activities.backend.book.event.options.EditableOptionsRouting;
import mongoose.activities.backend.container.BackendContainerViewActivity;
import mongoose.activities.backend.event.bookings.BookingsRouting;
import mongoose.activities.backend.event.clone.CloneEventRouting;
import mongoose.activities.backend.event.letters.LettersRouting;
import mongoose.activities.backend.events.EventsRouting;
import mongoose.activities.backend.letter.LetterRouting;
import mongoose.activities.backend.monitor.MonitorRouting;
import mongoose.activities.backend.organizations.OrganizationsRouting;
import mongoose.activities.backend.tester.TesterRouting;
import mongoose.activities.backend.tester.savetest.SaveTestRooting;
import mongoose.activities.shared.application.SharedMongooseApplication;
import mongoose.activities.shared.auth.LoginRouting;
import mongoose.activities.shared.auth.UnauthorizedRouting;
import naga.framework.activity.combinations.viewdomain.impl.ViewDomainActivityContextFinal;
import naga.framework.ui.router.UiRouter;
import naga.platform.activity.Activity;
import naga.util.function.Factory;

/**
 * @author Bruno Salmon
 */
public class BackendMongooseApplication extends SharedMongooseApplication {

    public BackendMongooseApplication() {
        super(OrganizationsRouting.getPath());
    }

    @Override
    protected Factory<Activity<ViewDomainActivityContextFinal>> getContainerActivityFactory() {
        return BackendContainerViewActivity::new;
    }

    @Override
    protected UiRouter setupContainedRouter(UiRouter containedRouter) {
        return super.setupContainedRouter(containedRouter
                .setRedirectAuthHandler(LoginRouting.getPath(), UnauthorizedRouting.getPath())
                .route(LoginRouting.uiRoute())
                .route(UnauthorizedRouting.uiRoute())
                .route(EditableOptionsRouting.uiRoute())
                .route(OrganizationsRouting.uiRoute())
                .route(EventsRouting.uiRoute())
                .route(BookingsRouting.uiRoute())
                .route(LettersRouting.uiRoute())
                .route(CloneEventRouting.uiRoute())
                .route(LetterRouting.uiRoute())
                .route(MonitorRouting.uiRoute())
                .route(TesterRouting.uiRoute())
                .route(SaveTestRooting.uiRoute())
        );
    }

    public static void main(String[] args) {
        launchApplication(new BackendMongooseApplication(), args);
    }

}
