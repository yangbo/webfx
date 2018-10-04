package mongoose.client.activities.shared;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import webfx.framework.shared.orm.domainmodel.DataSourceModel;
import webfx.framework.client.services.i18n.I18n;
import webfx.framework.client.ui.filter.ReactiveExpressionFilterFactoryMixin;
import webfx.framework.client.ui.util.background.BackgroundUtil;
import webfx.framework.client.ui.controls.button.ButtonFactoryMixin;
import webfx.framework.client.ui.controls.dialog.DialogCallback;
import webfx.framework.client.ui.controls.dialog.DialogUtil;
import webfx.framework.client.ui.layouts.LayoutUtil;
import webfx.fxkit.extra.cell.collator.GridCollator;

import static webfx.framework.client.ui.layouts.LayoutUtil.createHGrowable;

/**
 * @author Bruno Salmon
 */
public final class TermsDialog implements ButtonFactoryMixin, ReactiveExpressionFilterFactoryMixin {

    private final Object eventId;
    private final DataSourceModel dataSourceModel;
    private final Pane parent;
    private Runnable onClose;
    private DialogCallback termsDialogCallback;

    public TermsDialog(Object eventId, DataSourceModel dataSourceModel, Pane parent) {
        this.eventId = eventId;
        this.dataSourceModel = dataSourceModel;
        this.parent = parent;
    }

    public TermsDialog setOnClose(Runnable onClose) {
        this.onClose = onClose;
        return this;
    }

    @Override
    public DataSourceModel getDataSourceModel() {
        return dataSourceModel;
    }

    @Override
    public ObservableValue<Boolean> activeProperty() {
        return null;
    }

    public void show() {
        GridCollator termsLetterCollator = new GridCollator("first", "first");
        termsLetterCollator.setBackground(BackgroundUtil.WHITE_BACKGROUND);
        BorderPane entityDialogPane = new BorderPane(LayoutUtil.setMaxPrefSizeToInfinite(LayoutUtil.createVerticalScrollPaneWithPadding(termsLetterCollator)));
        createReactiveExpressionFilter("{class: 'Letter', where: 'type.terms', limit: '1'}")
                .combine("{where: 'event=" + eventId + "'}")
                .combine(I18n.languageProperty(), lang -> "{columns: '[`html(" + lang + ")`]'}")
                .displayResultInto(termsLetterCollator.displayResultProperty())
                .start();
        HBox hBox = new HBox(20, createHGrowable(), newOkButton(this::closeTermsDialog), createHGrowable());
        hBox.setPadding(new Insets(20, 0, 0, 0));
        entityDialogPane.setBottom(hBox);
        termsDialogCallback = DialogUtil.showModalNodeInGoldLayout(entityDialogPane, parent, 0.9, 0.8);
    }

    private void closeTermsDialog() {
        termsDialogCallback.closeDialog();
        if (onClose != null)
            onClose.run();
    }

}
