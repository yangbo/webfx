package mongoose.activities.shared.book.event.options;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import mongoose.activities.shared.logic.ui.highlevelcomponents.HighLevelComponents;
import mongoose.activities.shared.logic.work.WorkingDocument;
import mongoose.activities.shared.logic.work.transaction.WorkingDocumentTransaction;
import mongoose.entities.Label;
import mongoose.entities.Option;
import mongoose.util.Labels;
import naga.framework.ui.controls.LayoutUtil;
import naga.framework.ui.i18n.I18n;
import naga.fx.properties.Properties;
import naga.fx.util.ImageStore;
import naga.util.collection.Collections;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Salmon
 */
class OptionTreeNode {

    private final Option option;
    private final OptionTree tree;
    private final OptionTreeNode parent;
    private BorderPane buttonNode;
    private Node detailedNode;
    private ChoiceBox<Label> childrenChoiceBox;
    private ToggleGroup childrenToggleGroup;
    private List<OptionTreeNode> childrenOptionTreeNodes;
    private OptionTreeNode lastSelectedChildOptionTreeNode;

    private OptionTreeNode(Option option, OptionTree tree, OptionTreeNode parent) {
        this.option = option;
        this.tree = tree;
        this.parent = parent;
    }

    private OptionTreeNode(Option option, OptionTreeNode parent) {
        this(option, parent.tree, parent);
    }

    OptionTreeNode(Option option, OptionTree tree) {
        this(option, tree, null);
    }

    public Option getOption() {
        return option;
    }

    private ToggleGroup getChildrenToggleGroup() {
        return childrenToggleGroup;
    }

    protected I18n getI18n() {
        return tree.getI18n();
    }

    private WorkingDocumentTransaction getWorkingDocumentTransaction() {
        return tree.getWorkingDocumentTransaction();
    }

    Node createOrUpdateButtonNodeFromModel() {
        if (buttonNode == null) {
            buttonNode = createTopLevelOptionPanel(false);
            buttonNode.setOnMouseClicked(e -> {
                boolean selected = !optionButtonSelectedProperty.getValue();
                optionButtonSelectedProperty.setValue(selected);
                keepButtonSelectedAsItIsATemporaryUiTransitionalState(selected);
            });
            buttonNode.setCursor(Cursor.HAND);
            ImageView checkBoxView = new ImageView();
            checkBoxView.imageProperty().bind(Properties.compute(optionButtonSelectedProperty, selected ->
                    ImageStore.getOrCreateImage(selected ? "images/16/checked.png" : "images/16/unchecked.png", 16, 16)));
            //
            ((HBox) buttonNode.getTop()).getChildren().add(0, checkBoxView);
            LayoutUtil.setMinWidthToPref(buttonNode);
            LayoutUtil.setMaxSizeToInfinite(buttonNode);
        }
        keepButtonSelectedAsItIsATemporaryUiTransitionalState(keepButtonSelectedAsItIsATemporaryUiTransitionalState());
        syncUiFromModel();
        return buttonNode;
    }

    Node createOrUpdateDetailedNodeFromModel() {
        if (detailedNode == null)
            detailedNode = createTopLevelOptionPanel(true);
        syncUiFromModel();
        return detailedNode;
    }

    private BorderPane createTopLevelOptionPanel(boolean detailed) {
        BorderPane sectionPanel = HighLevelComponents.createSectionPanel(null, Collections.toArray(
                createOptionPanelHeaderNodes(Labels.translateLabel(Labels.bestLabelOrName(option), getI18n()))
                , Node[]::new));
        createOptionButtonAndSelectedProperty();
        if (detailed) {
            Pane panelBodyNode = createPanelBodyNode();
            if (panelBodyNode != null) {
                panelBodyNode.setPadding(new Insets(20));
                sectionPanel.setCenter(panelBodyNode);
                if (option.hasNoParent())
                    LayoutUtil.setUnmanagedWhenInvisible(sectionPanel, Properties.compute(optionButtonSelectedProperty,
                            selected -> selected && hasVisibleContent(panelBodyNode)));
            }
        }
        return sectionPanel;
    }

    private static boolean hasVisibleContent(Pane parent) {
        for (Node child : parent.getChildren())
            if (child.isVisible() && (!(child instanceof Pane) || hasVisibleContent((Pane) child)))
                return true;
        return false;
    }

    private List<Node> createOptionPanelHeaderNodes(Property<String> i18nTitle) {
        return tree.getActivity().createOptionPanelHeaderNodes(option, i18nTitle);
    }

    private Node createLabelNode(Label label) {
        return tree.getActivity().createLabelNode(label);
    }

    private ButtonBase optionButton;
    private Property<Boolean> optionButtonSelectedProperty;
    private WorkingDocument keepButtonSelectedAsItIsATemporaryUiTransitionalState; /* Set to true to allow the ui to be temporary
     desynchronized from the model during a transitional state. Ex: The booker ticked the translation checkbox but hasn't
     yet selected the language. This is a transitional state because the translation option can't yet be added to the
     model (as no language selected) but it is necessary to keep the checkbox ticked in the ui to let the booker select
     the language. Once selected, this language selection causes the translation option to be added to the model so this
     flag should be set back to false because the ui and model are synchronized again.*/

    private void keepButtonSelectedAsItIsATemporaryUiTransitionalState(boolean value) {
        keepButtonSelectedAsItIsATemporaryUiTransitionalState = value ? tree.getWorkingDocument() : null;
    }

    private boolean keepButtonSelectedAsItIsATemporaryUiTransitionalState() {
        return keepButtonSelectedAsItIsATemporaryUiTransitionalState == tree.getWorkingDocument();
    }

    private void createOptionButtonAndSelectedProperty() {
        if (optionButtonSelectedProperty != null)
            return;
        if (option.isObligatory() || option.hasNoParent())
            optionButtonSelectedProperty = new SimpleBooleanProperty(option.isObligatory()); // new ReadOnlyBooleanWrapper(true); // not emulated
        else {
            Label promptLabel = option.getPromptLabel();
            Label buttonLabel = promptLabel != null ? promptLabel : Labels.bestLabelOrName(option);
            ToggleGroup toggleGroup = parent == null ? null : parent.getChildrenToggleGroup();
            ChoiceBox<Label> choiceBox = parent == null ? null : parent.childrenChoiceBox;
            if (toggleGroup != null) {
                RadioButton radioButton = new RadioButton();
                radioButton.setToggleGroup(toggleGroup);
                optionButtonSelectedProperty = radioButton.selectedProperty();
                optionButton = radioButton;
            } else if (choiceBox != null) {
                choiceBox.getItems().add(buttonLabel);
                optionButtonSelectedProperty = new SimpleObjectProperty<Boolean>(false) {
                    @Override
                    protected void invalidated() {
                        if (getValue())
                            choiceBox.getSelectionModel().select(buttonLabel);
                    }
                };
                Properties.runOnPropertiesChange(p -> optionButtonSelectedProperty.setValue(p.getValue() == buttonLabel), choiceBox.getSelectionModel().selectedItemProperty());
                optionButton = null;
            } else {
                CheckBox checkBox = new CheckBox();
                optionButtonSelectedProperty = checkBox.selectedProperty();
                optionButton = checkBox;
            }
            if (optionButton != null)
                Labels.translateLabel(optionButton, buttonLabel, getI18n());
        }
        Properties.runOnPropertiesChange(p -> onUiOptionButtonChanged(), optionButtonSelectedProperty);
    }

    private Pane createPanelBodyNode() {
        createOptionButtonAndSelectedProperty();
        if (optionButton == null && option.isNotObligatory() && option.hasParent())
            return null;
        VBox vBox = new VBox();
        if (parent != null && parent.parent != null && parent.parent.childrenToggleGroup != null) // If under a radio button (ex: Ecommoy shuttle)
            vBox.setPadding(new Insets(0, 0, 0, 20)); // Adding a left padding
        mongoose.entities.Label topLabel = option.getTopLabel();
        ObservableList<Node> vBoxChildren = vBox.getChildren();
        if (topLabel != null)
            vBoxChildren.add(createLabelNode(topLabel));
        if (optionButton != null)
            vBoxChildren.add(optionButton);
        List<Option> childrenOptions = getChildrenOptions(option);
        if (!Collections.isEmpty(childrenOptions)) {
            if ("select".equals(option.getLayout())) {
                childrenChoiceBox = new ChoiceBox<>();
                childrenChoiceBox.setConverter(new StringConverter<Label>() {
                    @Override
                    public String toString(Label label) {
                        return Labels.instantTranslateLabel(label, getI18n());
                    }

                    @Override
                    public Label fromString(String string) {
                        return null;
                    }
                });
                Label childrenPromptLabel = option.getChildrenPromptLabel();
                Node selectNode = childrenChoiceBox;
                if (childrenPromptLabel != null)
                    selectNode =  new HBox(10, createLabelNode(childrenPromptLabel), selectNode);
                vBoxChildren.add(selectNode);
                if (optionButtonSelectedProperty != null)
                    LayoutUtil.setUnmanagedWhenInvisible(selectNode, optionButtonSelectedProperty);
                Properties.runOnPropertiesChange(p -> refreshChildrenChoiceBoxOnLanguageChange(), getI18n().languageProperty());
            } else if (option.isChildrenRadio())
                childrenToggleGroup = new ToggleGroup();
            //Doesn't work on Android: childrenOptionTreeNodes = childrenOptions.stream().map(o -> new OptionTreeNode(o, this)).collect(Collectors.toList());
            childrenOptionTreeNodes = Collections.map(childrenOptions, o -> new OptionTreeNode(o, this));
            //Doesn't work on Android: vBoxChildren.addAll(childrenOptionTreeNodes.stream().map(OptionTreeNode::createPanelBodyNode).filter(Objects::nonNull).collect(Collectors.toList()));
            vBoxChildren.addAll(Collections.mapFilter(childrenOptionTreeNodes, OptionTreeNode::createPanelBodyNode, naga.util.Objects::nonNull));
        }
        if (parent != null && parent.optionButtonSelectedProperty != null)
            LayoutUtil.setUnmanagedWhenInvisible(vBox, parent.optionButtonSelectedProperty);
        return vBox;
    }

    private void refreshChildrenChoiceBoxOnLanguageChange() {
        Label selectedItem = childrenChoiceBox.getSelectionModel().getSelectedItem();
        // Resetting the items with an identical duplicated list (to force the ui update)
        childrenChoiceBox.getItems().setAll(new ArrayList<>(childrenChoiceBox.getItems()));
        // The later operation removed the selected item so we restore it
        childrenChoiceBox.getSelectionModel().select(selectedItem);
    }

    private void onUiOptionButtonChanged() {
        if (!syncingUiFromModel)
            syncModelFromUi(false);
    }

    private void syncModelFromUi(boolean unselectedParent) {
        boolean uiSelected = !unselectedParent && (optionButtonSelectedProperty == null /* obligatory */ || optionButtonSelectedProperty.getValue());
        //Logger.log("Syncing model from TreeNode uiSelected = " + uiSelected + (option.getItem() != null ? ", item = " + option.getItem() : ", option = " + option));
        if (uiSelected)
            addOptionToModelIfNotAlreadyPresent();
        else
            removeOptionFromModel();
        if (childrenOptionTreeNodes != null)
            for (OptionTreeNode childOptionTreeNode : childrenOptionTreeNodes)
                childOptionTreeNode.syncModelFromUi(!uiSelected);
        tree.deferTransactionCommitAndUiSync();
    }

    private void addOptionToModelIfNotAlreadyPresent() {
        if (!isModelOptionSelected())
            addOptionToModel();
    }

    private void addOptionToModel() {
        if (option.isConcrete()) {
            getWorkingDocumentTransaction().addOption(option);
            syncUiOptionButtonSelected(true);
        }
        if (childrenOptionTreeNodes != null) {
            boolean childAdded = false;
            for (OptionTreeNode childTreeNode: childrenOptionTreeNodes)
                if (childTreeNode.option.isObligatory()) {
                    childTreeNode.addOptionToModel();
                    childAdded = true;
                }
            if (!childAdded) {
                if (lastSelectedChildOptionTreeNode != null)
                    lastSelectedChildOptionTreeNode.addOptionToModel();
                else if (optionButtonSelectedProperty != null && optionButtonSelectedProperty.getValue())
                    keepButtonSelectedAsItIsATemporaryUiTransitionalState(true);
            }
        }
    }


    private void removeOptionFromModel() {
        getWorkingDocumentTransaction().removeOption(option);
        if (option.hasNoParent())
            keepButtonSelectedAsItIsATemporaryUiTransitionalState(false);
        if (childrenOptionTreeNodes != null)
            for (OptionTreeNode childTreeNode : childrenOptionTreeNodes)
                childTreeNode.removeOptionFromModel();
    }

    private List<Option> getChildrenOptions(Option parent) {
        return tree.getActivity().getChildrenOptions(parent);
    }

    private boolean syncingUiFromModel;

    private void syncUiFromModel() {
        boolean modelSelected = isModelOptionSelected();
        syncUiOptionButtonSelected(modelSelected);
        if (childrenOptionTreeNodes != null && modelSelected)
            for (OptionTreeNode childOptionTreeNode : childrenOptionTreeNodes)
                childOptionTreeNode.syncUiFromModel();
    }

    private void syncUiOptionButtonSelected(boolean modelSelected) {
        if (optionButtonSelectedProperty != null && option.isNotObligatory()) { // obligatory options should not be ui updated (ex: Airport transfer section)
            syncingUiFromModel = true;
            boolean uiSelected = modelSelected || keepButtonSelectedAsItIsATemporaryUiTransitionalState();
            //Logger.log("Syncing ui from model uiSelected = " + uiSelected + (option.getItem() != null ? ", item = " + option.getItem().getName() : ", option = " + option.getName()));
            optionButtonSelectedProperty.setValue(uiSelected);
            syncingUiFromModel = false;
        }
        if (parent != null && modelSelected) {
            parent.lastSelectedChildOptionTreeNode = this;
            if (parent.option.hasParent())
                parent.keepButtonSelectedAsItIsATemporaryUiTransitionalState(false);
        }
    }

    private boolean isModelOptionSelected() {
        if (getWorkingDocumentTransaction().isOptionBooked(option))
            return true;
        if (childrenOptionTreeNodes != null) {
            for (OptionTreeNode childTreeNode: childrenOptionTreeNodes) {
                if (childTreeNode.isModelOptionSelected())
                    return true;
            }
        }
        return false;
    }
}
