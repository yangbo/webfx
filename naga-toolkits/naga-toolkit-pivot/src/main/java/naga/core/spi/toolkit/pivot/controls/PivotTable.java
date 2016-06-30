package naga.core.spi.toolkit.pivot.controls;

import naga.core.spi.toolkit.controls.Table;
import naga.core.spi.toolkit.hasproperties.SelectionMode;
import naga.core.spi.toolkit.pivot.node.PivotSelectableDisplayResultSetNode;
import naga.core.ui.displayresultset.DisplayResultSet;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TableViewHeader;


/**
 * @author Bruno Salmon
 */
public class PivotTable extends PivotSelectableDisplayResultSetNode<ScrollPane> implements Table<ScrollPane> {

    private TableView tableView;

    public PivotTable() {
        this(createScrollPane());
    }

    public PivotTable(ScrollPane scrollPane) {
        super(scrollPane);
        tableView = (TableView) node.getView();
    }

    @Override
    protected void onNextSelectionMode(SelectionMode selectionMode) {

    }

    private static ScrollPane createScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        TableView tableView = new TableView();
        scrollPane.setView(tableView);
        scrollPane.setColumnHeader(new TableViewHeader(tableView));
        return scrollPane;
    }

    @Override
    protected void onNextDisplayResult(DisplayResultSet displayResultSet) {
        int rowCount = displayResultSet.getRowCount();
        int columnCount = displayResultSet.getColumnCount();
        TableView.ColumnSequence columns = tableView.getColumns();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            if (columnIndex < columns.getLength())
                setUpTableColumn(columns.get(columnIndex), columnIndex, displayResultSet);
            else
                columns.add(setUpTableColumn(new TableView.Column(displayResultSet.getColumns()[columnIndex].getName()), columnIndex, displayResultSet));
        }
        ArrayList tableData = new ArrayList(rowCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            HashMap row = new HashMap();
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++)
                row.put(Integer.toString(columnIndex), displayResultSet.getValue(rowIndex, columnIndex));
            tableData.add(row);
        }
        tableView.setTableData(tableData);
    }

    private TableView.Column setUpTableColumn(TableView.Column tableColumn, int columnIndex, DisplayResultSet displayResultSet) {
        tableColumn.setName(Integer.toString(columnIndex));
        tableColumn.setHeaderData(displayResultSet.getColumns()[columnIndex].getName());
        tableColumn.setWidth(400);
        return tableColumn;
    }

}
