package mongoose.client.activity.table;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.Node;

public class GroupMasterSlaveView extends MasterSlaveView {

    public GroupMasterSlaveView(Orientation orientation) {
        this(orientation, null, null, null);
    }

    public GroupMasterSlaveView(Orientation orientation, Node groupView, Node masterView, Node slaveView) {
        super(orientation, null, null);
        setGroupView(groupView);
        setMasterView(masterView);
        setSlaveView(slaveView);
        setMasterVisible(true);
    }

    private final ObjectProperty<Node> groupViewProperty = new SimpleObjectProperty<Node>() { // GWT doesn't accept <>
        @Override
        protected void invalidated() {
            updateView();
        }
    };

    public ObjectProperty<Node> groupViewProperty() {
        return groupViewProperty;
    }

    public void setGroupView(Node groupView) {
        groupViewProperty().set(groupView);
    }

    public Node getGroupView() {
        return groupViewProperty().get();
    }

    @Override
    void updateView() {
        updateSplitPane(isGroupVisible() ? getGroupView() : null, isMasterVisible() ? getMasterView() : null, isSlaveVisible() ? getSlaveView() : null);
    }

    private final BooleanProperty groupVisibleProperty = new SimpleBooleanProperty() {
        @Override
        protected void invalidated() {
            updateView();
        }
    };

    public BooleanProperty groupVisibleProperty() {
        return groupVisibleProperty;
    }

    public boolean isGroupVisible() {
        return groupVisibleProperty().get();
    }

    public void setGroupVisible(boolean groupVisible) {
        groupVisibleProperty().setValue(groupVisible);
    }

    private final BooleanProperty masterVisibleProperty = new SimpleBooleanProperty() {
        @Override
        protected void invalidated() {
            updateView();
        }
    };

    public BooleanProperty masterVisibleProperty() {
        return masterVisibleProperty;
    }

    public boolean isMasterVisible() {
        return masterVisibleProperty().get();
    }

    public void setMasterVisible(boolean masterVisible) {
        masterVisibleProperty().setValue(masterVisible);
    }

}
