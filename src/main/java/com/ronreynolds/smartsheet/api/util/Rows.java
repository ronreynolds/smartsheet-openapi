package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.model.Attachment;
import com.ronreynolds.smartsheet.model.Cell;
import com.ronreynolds.smartsheet.model.Column;
import com.ronreynolds.smartsheet.model.Discussion;
import com.ronreynolds.smartsheet.model.Row;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Rows {
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

/* uses a lot of things not available in the openapi spec model (specifically builders)
    public static Row updateRow(Row originalRow, Consumer<Row> rowUpdate,
                                BiConsumer<Row, List<Cell>> cellUpdate) {
        Row.UpdateRowBuilder builder = new Row.UpdateRowBuilder();
        builder.setRowId(originalRow.getId());
        builder.setToTop(originalRow.getToTop());
        builder.setToBottom(originalRow.getToBottom());
        builder.setParentId(originalRow.getParentId());
        builder.setSiblingId(originalRow.getSiblingId());
        builder.setAbove(originalRow.getAbove());
        builder.setIndent(originalRow.getIndent());
        builder.setOutdent(originalRow.getOutdent());
        builder.setFormat(originalRow.getFormat());
        builder.setExpanded(originalRow.isExpanded());
        builder.setLocked(originalRow.isLocked());
        if (rowUpdate != null) {
            rowUpdate.accept(builder);
        }
        if (cellUpdate != null) {
            Cell.UpdateRowCellsBuilder cellsBuilder = new Cell.UpdateRowCellsBuilder();
            cellUpdate.accept(cellsBuilder, originalRow.getCells());
            builder.setCells(cellsBuilder.build());
        }

        return builder.build();
    }
*/

    public static CharSequence toString(@NonNull Row row) {
        StringBuilder buf = new StringBuilder();
        buf.append("{id:").append(row.getId())
                .append(", rowNum:").append(row.getRowNumber())
                .append(", sheetId:").append(row.getSheetId())
//                .append(", parentId:").append(row.getParentId())
//                .append(", parentRowNum:").append(row.getParentRowNumber())
                .append(", siblingId:").append(row.getSiblingId())
                .append(", permalink:").append(row.getPermaLink())
                .append(", version:").append(row.getVersion())
                .append(", created:{by:").append(row.getCreatedBy()).append(", at:").append(row.getCreatedAt()).append('}')
                .append(", modified:{by:").append(row.getModifiedBy()).append(", at:").append(row.getModifiedAt()).append('}')
//                .append(", above:").append(row.getAbove())
//                .append(", toBottom:").append(row.getToBottom()).append(", toTop:").append(row.getToTop())
//                .append(", indent:").append(row.getIndent()).append(", outdent:").append(row.getOutdent())
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

        // none of these are in the openapi spec model?
//        row.setToTop(null);
//        row.setToBottom(null);
//        row.setAbove(null);
//        row.setIndent(null);
//        row.setOutdent(null);
//        row.setParentId(null);
    }
}
