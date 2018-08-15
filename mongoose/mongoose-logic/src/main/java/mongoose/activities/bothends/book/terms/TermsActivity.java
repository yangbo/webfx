package mongoose.activities.bothends.book.terms;

import naga.framework.activity.base.combinations.domainpresentation.impl.DomainPresentationActivityImpl;

/**
 * @author Bruno Salmon
 */
class TermsActivity extends DomainPresentationActivityImpl<TermsPresentationModel> {

    TermsActivity() {
        super(TermsPresentationViewActivity::new, TermsPresentationLogicActivity::new);
    }
}
