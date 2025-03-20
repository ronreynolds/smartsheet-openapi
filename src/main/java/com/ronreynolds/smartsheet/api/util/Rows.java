package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.model.Attachment;
import com.ronreynolds.smartsheet.model.Cell;
import com.ronreynolds.smartsheet.model.Column;
import com.ronreynolds.smartsheet.model.Discussion;
import com.ronreynolds.smartsheet.model.Row;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Rows {
    private Rows() {
    }

    /**
     * create a Row object, which is composed of a few flags and a list of Cell objects
     */
    public static Row createRow(Consumer<Row>... rowChanges) {
        Row row = new Row();
        row.setCells(new ArrayList<>());
        for (Consumer<Row> change : rowChanges) {
            change.accept(row);
        }
        return row;
    }

    /**
     * update the provided row using the provided callbacks to modify the row and provide the Cells
     *
     * @param originalRow the starting point for the new Row
     * @param rowUpdate   if provided is invoked with the new row before the Cells are added
     * @param cellUpdate  used to update Cells in the new Row
     * @return the newly-built Row
     */
    public static Row updateRow(Row originalRow, Consumer<Row.Builder> rowUpdate, BiConsumer<Row.Builder, List<Cell>> cellUpdate) {
        Row.Builder builder = originalRow.toBuilder();
        if (rowUpdate != null) {
            rowUpdate.accept(builder);
        }
        if (cellUpdate != null) {
            cellUpdate.accept(builder, originalRow.getCells());
        }
        return builder.build();
    }

    public static CharSequence toString(@NonNull Row row) {
        StringBuilder buf = new StringBuilder();
        buf.append("{id:").append(row.getId())
                .append(", rowNum:").append(row.getRowNumber())
                .append(", sheetId:").append(row.getSheetId())
                .append(", parentId:").append(row.getParentId())
//                .append(", parentRowNum:").append(row.getParentRowNumber()) - deprecated (v1.1) and removed in v2
                .append(", siblingId:").append(row.getSiblingId())
                .append(", permalink:").append(row.getPermalink())
                .append(", version:").append(row.getVersion())
                .append(", created:{by:").append(row.getCreatedBy()).append(", at:").append(row.getCreatedAt()).append('}')
                .append(", modified:{by:").append(row.getModifiedBy()).append(", at:").append(row.getModifiedAt()).append('}')
                .append(", above:").append(row.getAbove())
                .append(", toBottom:").append(row.getToBottom()).append(", toTop:").append(row.getToTop())
                .append(", indent:").append(row.getIndent()).append(", outdent:").append(row.getOutdent())
                .append(", accessLevel:").append(row.getAccessLevel())
                .append(", format:").append(row.getFormat()).append(", condFormat:").append(row.getConditionalFormat());

        List<Attachment> attachmentList = row.getAttachments();
        List<Column> columnList = row.getColumns();
        List<Cell> cellList = row.getCells();
        List<Discussion> discussionList = row.getDiscussions();

        buf.append(", columns:").append(columnList);
        buf.append(", cells:").append(cellList);
        buf.append(", attachments:").append(attachmentList);
        buf.append(", discussions:").append(discussionList);

        return buf;
    }

    /**
     * clear out all the location settings on a {@link Row} object
     */
    public static void clearLocations(@NonNull Row row) {
        row.setRowNumber(null);
        row.setSiblingId(null);
        row.setToTop(null);
        row.setToBottom(null);
        row.setAbove(null);
        row.setIndent(null);
        row.setOutdent(null);
        row.setParentId(null);
    }
}
