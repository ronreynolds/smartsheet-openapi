package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.api.util.Cells;
import com.ronreynolds.smartsheet.model.AccessLevel;
import com.ronreynolds.smartsheet.model.AlternateEmail;
import com.ronreynolds.smartsheet.model.Attachment;
import com.ronreynolds.smartsheet.model.AttachmentType;
import com.ronreynolds.smartsheet.model.AttachmentTypeTrello;
import com.ronreynolds.smartsheet.model.Cell;
import com.ronreynolds.smartsheet.model.Column;
import com.ronreynolds.smartsheet.model.ColumnType;
import com.ronreynolds.smartsheet.model.Comment;
import com.ronreynolds.smartsheet.model.Folder;
import com.ronreynolds.smartsheet.model.GetCurrentUser200Response;
import com.ronreynolds.smartsheet.model.GetWorkspaceFolders200ResponseAllOfDataInner;
import com.ronreynolds.smartsheet.model.Report;
import com.ronreynolds.smartsheet.model.ReportBrief;
import com.ronreynolds.smartsheet.model.ReportPublish;
import com.ronreynolds.smartsheet.model.Result;
import com.ronreynolds.smartsheet.model.ResultPrefix;
import com.ronreynolds.smartsheet.model.Row;
import com.ronreynolds.smartsheet.model.Share;
import com.ronreynolds.smartsheet.model.ShareScope;
import com.ronreynolds.smartsheet.model.ShareType;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.smartsheet.model.SheetListingDataInner;
import com.ronreynolds.smartsheet.model.SheetPublish;
import com.ronreynolds.smartsheet.model.SourceType;
import com.ronreynolds.smartsheet.model.Template;
import com.ronreynolds.smartsheet.model.UserProfile;
import com.ronreynolds.smartsheet.model.Workspace;
import com.ronreynolds.util.reflection.Reflection;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * various bits of test constants
 */
@Slf4j
public class TestData {
    // lists of IDs of added items to be deleted in subsequent tests
    public static final List<Long> temporaryWorkspaceIds = new ArrayList<>();
    public static final List<Long> temporaryFolderIds = new ArrayList<>();
    public static final List<Long> temporaryUserIds = new ArrayList<>();

    private TestData() {
    }

    interface AttachmentData {
        interface CommentAttachment {
            long id = CommentData.attachmentId;
            long parentId = CommentData.id;
            Attachment.ParentTypeEnum parentType = Attachment.ParentTypeEnum.COMMENT;
            AttachmentTypeTrello attachmentType = AttachmentTypeTrello.FILE;
            String mimeType = "image/gif";
            OffsetDateTime created = OffsetDateTime.of(2025, 4, 23, 13, 44, 32, 0, ZoneOffset.UTC);
            String name = "calvin-head-24-2.GIF";
            int sizeInKb = 10;

            static void assertEquals(Attachment attachment) {
                assertThat(attachment).isNotNull();
                assertThat(attachment.getId()).as("id").isEqualTo(id);
                assertThat(attachment.getName()).as("name").isEqualTo(name);
                assertThat(attachment.getAttachmentType()).as("attachment-type").isSameAs(attachmentType);
                assertThat(attachment.getMimeType()).as("mime-type").isEqualTo(mimeType);
                assertThat(attachment.getCreatedAt()).as("created-at").isEqualTo(created);
                assertThat(attachment.getSizeInKb()).as("size-in-kb").isEqualTo(sizeInKb);

                // light attachment data doesn't include parent info
                if (attachment.getParentType() != null) {
                    assertThat(attachment.getParentId()).as("parent id").isEqualTo(parentId);
                    assertThat(attachment.getParentType()).as("parent type").isSameAs(parentType);
                }
            }
        }

        interface RowAttachment {
            long id = RowData.attachmentId;
            long parentId = RowData.id;
            Attachment.ParentTypeEnum parentType = Attachment.ParentTypeEnum.ROW;
            AttachmentTypeTrello attachmentType = AttachmentTypeTrello.FILE;
            String mimeType = "image/jpeg";
            OffsetDateTime created = OffsetDateTime.of(2025, 4, 23, 13, 11, 19, 0, ZoneOffset.UTC);
            String name = "Test Row Attachment.jpg";
            int sizeInKb = 751;

            static void assertEquals(Attachment attachment) {
                assertThat(attachment).isNotNull();
                assertThat(attachment.getId()).as("id").isEqualTo(id);
                assertThat(attachment.getParentId()).as("parent id").isEqualTo(parentId);
                assertThat(attachment.getName()).as("name").isEqualTo(name);
                assertThat(attachment.getParentType()).as("parent type").isSameAs(parentType);
                assertThat(attachment.getAttachmentType()).as("attachment-type").isSameAs(attachmentType);
                assertThat(attachment.getMimeType()).as("mime-type").isEqualTo(mimeType);
                assertThat(attachment.getCreatedAt()).as("created-at").isEqualTo(created);
                assertThat(attachment.getSizeInKb()).as("size-in-kb").isEqualTo(sizeInKb);
            }
        }

        interface SheetAttachment {
            long id = 350159638925188L;
            long parentId = SheetData.id;
            Attachment.ParentTypeEnum parentType = Attachment.ParentTypeEnum.SHEET;
            AttachmentTypeTrello attachmentType = AttachmentTypeTrello.FILE;
            String mimeType = "image/jpeg";
            OffsetDateTime created = OffsetDateTime.of(2025, 4, 23, 13, 10, 4, 0, ZoneOffset.UTC);
            String name = "Test Attachment.jpeg";
            int sizeInKb = 212;

            static void assertEquals(Attachment attachment) {
                assertThat(attachment).isNotNull();
                assertThat(attachment.getId()).as("id").isEqualTo(id);
                assertThat(attachment.getParentId()).as("parent id").isEqualTo(parentId);
                assertThat(attachment.getName()).as("name").isEqualTo(name);
                assertThat(attachment.getParentType()).as("parent type").isSameAs(parentType);
                assertThat(attachment.getAttachmentType()).as("attachment-type").isSameAs(attachmentType);
                assertThat(attachment.getMimeType()).as("mime-type").isEqualTo(mimeType);
                assertThat(attachment.getCreatedAt()).as("created-at").isEqualTo(created);
                assertThat(attachment.getSizeInKb()).as("size-in-kb").isEqualTo(sizeInKb);
            }
        }
    }

    interface CellData {
        long id = 0L;
        Object value = 42;
    }

    interface ColumnData {
        Set<Long> columnIds = Set.of(PrimaryColumn.id, ImageColumn.id, Column3.id, Column4.id, Column5.id, Column6.id);

        interface PrimaryColumn {
            long id = 6606505816313732L;
            String name = "Primary Column";
            String type = "TEXT_NUMBER";
        }

        interface ImageColumn {
            long id = 4354706002628484L;
            String name = "Images";
            String type = "TEXT_NUMBER";
        }

        // created these to keep track of the IDs which should not change as the column names, types, and so forth do
        interface Column3 {
            long id = 8858305629998980L;
            String name = "Column3";
            String type = "TEXT_NUMBER";
        }

        interface Column4 {
            long id = 273318840323972L;
            String name = "Column4";
            String type = "TEXT_NUMBER";
        }

        interface Column5 {
            long id = 4776918467694468L;
            String name = "Column5";
            String type = "TEXT_NUMBER";
        }

        interface Column6 {
            long id = 2525118654009220L;
            String name = "Column6";
            String type = "TEXT_NUMBER";
        }
    }

    interface CommentData {
        long id = 6185976821223300L;
        String text = "Test Comment";
        long attachmentId = 3555179125575556L;
        long discussionId = 5934265966825348L;
        OffsetDateTime created = OffsetDateTime.of(2025, 4, 23, 13, 42, 0, 0, ZoneOffset.UTC);

        static void assertEquals(Comment comment) {
            assertThat(comment).isNotNull();
            assertThat(comment.getId()).as("id").isEqualTo(id);
            assertThat(comment.getDiscussionId()).as("discussion id").isEqualTo(discussionId);
            assertThat(comment.getText()).as("text").isEqualTo(text);
            assertThat(comment.getCreatedAt()).as("created-at").isEqualTo(created);

            // assert on the attachment too
            assertThat(comment.getAttachments())
                    .isNotEmpty()
                    .first().satisfies(AttachmentData.CommentAttachment::assertEquals);
        }
    }

    interface DashboardData {
        long id = 5226358067488644L;
        String name = "Test Dashboard";
        OffsetDateTime shareDate = OffsetDateTime.of(2025, 5, 7, 14, 9, 7, 0, ZoneOffset.UTC);

        static void assertShare(Share share) {
            ShareData.assertCommonShare(share, ShareScope.ITEM, shareDate);
            assertThat(share.getCreatedAt()).isEqualTo(shareDate);
            assertThat(share.getModifiedAt()).isAfterOrEqualTo(shareDate);
        }
    }

    interface FolderData {
        long id = 1104664725874564L;
        String name = "Test Folder";

        static void assertEquals(Folder folder) {
            assertThat(folder).isNotNull();
            assertThat(folder.getId()).isEqualTo(id);
            assertThat(folder.getName()).isEqualTo(name);
            assertThat(folder.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            log.info("folder:{}", folder);
            assertThat(folder.getFolders()).satisfies(FolderData::assertChildFolders);
            assertThat(folder.getReports()).satisfies(ReportData::assertContains);
            assertThat(folder.getSheets()).isEmpty();
            assertThat(folder.getSights()).isEmpty();
            assertThat(folder.getTemplates()).isEmpty();
        }
        static void assertChildFolders(List<? extends Folder> childFolders) {
            assertThat(childFolders)
                    .isNotEmpty()
                    .anySatisfy(folder -> {
                        assertThat(folder.getId()).isEqualTo(2081162651821956L);
                        assertThat(folder.getName()).isEqualTo("Test Folder Child 1");
                        assertThat(folder.getFolders()).isEmpty();
                        assertThat(folder.getReports()).isEmpty();
                        assertThat(folder.getSheets()).isEmpty();
                        assertThat(folder.getSights()).isEmpty();
                        assertThat(folder.getTemplates()).isEmpty();
                    })
                    .anySatisfy(folder -> {
                        assertThat(folder.getId()).isEqualTo(1658950186755972L);
                        assertThat(folder.getName()).isEqualTo("Test Folder Child 2");
                        assertThat(folder.getFolders()).isEmpty();
                        assertThat(folder.getReports()).isEmpty();
                        assertThat(folder.getSheets()).isEmpty();
                        assertThat(folder.getSights()).isEmpty();
                        assertThat(folder.getTemplates()).isEmpty();
                    });
            log.info("child-folders:{}", childFolders);
        }
    }

    interface ProofData {
        long id = 0L;   // FIXME
    }

    interface ReportData {
        long id = 5383337628618628L;
        String name = "Test Report";
        OffsetDateTime shareDate = OffsetDateTime.of(2025, 4, 20, 13, 59, 22, 0, ZoneOffset.UTC);

        static void assertContains(List<? extends Report> reports) {
            assertThat(reports).isNotEmpty().anySatisfy(ReportData::assertEquals);
        }

        static void assertEquals(Report report) {
            assertThat(report).isNotNull();
            assertThat(report.getId()).isEqualTo(id);
            assertThat(report.getName()).isEqualTo(name);
            assertThat(report.getAccessLevel()).isSameAs(AccessLevel.OWNER);
        }

        static void assertDeepEquals(Report report) {
            assertThat(report).isNotNull();
            assertThat(report.getId()).isEqualTo(id);
            assertThat(report.getName()).isEqualTo(name);
            assertThat(report.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            assertThat(report.getScope()).isNull();
            assertThat(report.getSourceSheets()).isNull();
            assertThat(report.getIsSummaryReport()).isFalse();
            assertThat(report.getSource()).isNull();
            assertThat(report.getFromId()).isNull();
            assertThat(report.getAttachments()).isNull();
            assertThat(report.getCellImageUploadEnabled()).isTrue();
            assertThat(report.getCreatedAt()).isEqualTo(OffsetDateTime.of(2025, 4, 20, 13, 59, 22, 0, ZoneOffset.UTC));
            assertThat(report.getCrossSheetReferences()).isNull();
            assertThat(report.getDependenciesEnabled()).isNull();
            assertThat(report.getDiscussions()).isNull();
            // TODO - change to enum
            assertThat(report.getEffectiveAttachmentOptions()).allMatch(SheetData.effectiveAttachmentOptions::contains);
            assertThat(report.getFavorite()).isNull();
            assertThat(report.getFilters()).isEmpty();
            assertThat(report.getGanttConfig()).isNull();
            assertThat(report.getGanttEnabled()).isFalse();
            assertThat(report.getHasSummaryFields()).isNull();
            assertThat(report.getIsMultiPicklistEnabled()).isNull();
            assertThat(report.getModifiedAt()).isAfterOrEqualTo(OffsetDateTime.of(2025, 4, 20, 13, 59, 58, 0, ZoneOffset.UTC));
            assertThat(report.getOwner()).isNull();
            assertThat(report.getOwnerId()).isNull();
            assertThat(report.getPermalink()).isNotBlank();
            assertThat(report.getProjectSettings()).isNull();
            assertThat(report.getReadOnly()).isNull();
            assertThat(report.getResourceManagementEnabled()).isNull();
            assertThat(report.getResourceManagementType()).isNull();
            assertThat(report.getShowParentRowsForFilters()).isNull();
            assertThat(report.getSummary()).isNull();
            assertThat(report.getTotalRowCount()).isOne();
            assertThat(report.getUserPermissions()).isNull();
            assertThat(report.getUserSettings()).isNull();
            assertThat(report.getVersion()).isNull();

            assertThat(report.getColumns()).isNotEmpty().anySatisfy(ReportData::assertReportColumn);
            assertThat(report.getRows()).isNotEmpty().anySatisfy(ReportData::assertReportRow);
            assertThat(report.getWorkspace()).satisfies(ReportData::assertReportWorkspace);
        }

        static void assertReportColumn(Column reportColumn) {
            assertThat(reportColumn).isNotNull();
            assertThat(reportColumn.getAutoNumberFormat()).isNull();
            assertThat(reportColumn.getContactOptions()).isNull();
            assertThat(reportColumn.getDescription()).isNull();
            assertThat(reportColumn.getFormat()).isNull();
            assertThat(reportColumn.getFormula()).isNull();
            assertThat(reportColumn.getHidden()).isNull();
            assertThat(reportColumn.getId()).isNull();
            assertThat(reportColumn.getIndex()).isNotNegative();    // 0, 1, ...
            assertThat(reportColumn.getLocked()).isNull();
            assertThat(reportColumn.getLockedForUser()).isNull();
            assertThat(reportColumn.getOptions()).isEmpty();
            assertThat(reportColumn.getPrimary()).matches(ReportData::isNullOrTrue);
            assertThat(reportColumn.getSymbol()).isNull();
            assertThat(reportColumn.getSystemColumnType()).isNull();
            assertThat(reportColumn.getTags()).isEmpty();
            assertThat(reportColumn.getTitle()).matches(Set.of("Sheet Name", "Primary")::contains);
            assertThat(reportColumn.getType()).isSameAs(ColumnType.TEXT_NUMBER);
            assertThat(reportColumn.getValidation()).isFalse();
            assertThat(reportColumn.getVersion()).isSameAs(Column.VersionEnum.NUMBER_0);
            assertThat(reportColumn.getWidth()).isEqualTo(150);
            assertThat(reportColumn.getVirtualId()).matches(Set.of(6693795649507204L, 4441995835821956L)::contains);
            assertThat(reportColumn.getSheetNameColumn()).matches(ReportData::isNullOrTrue);
        }

        static boolean isNullOrTrue(Boolean val) {
            return val == null || val;
        }

        static void assertReportRow(Row reportRow) {
            OffsetDateTime rowCreateDate = OffsetDateTime.of(2025, 4, 20, 13, 52, 46, 0, ZoneOffset.UTC);
            assertThat(reportRow).isNotNull();
            assertThat(reportRow.getColumns()).isNull();
            assertThat(reportRow.getConditionalFormat()).isNull();
            assertThat(reportRow.getCreatedAt()).isEqualTo(rowCreateDate);
            assertThat(reportRow.getCreatedBy()).isNull();
            assertThat(reportRow.getDiscussions()).isNull();
            assertThat(reportRow.getProof()).isNull();
            assertThat(reportRow.getExpanded()).matches(ReportData::isNullOrTrue);
            assertThat(reportRow.getFilteredOut()).isNull();
            assertThat(reportRow.getFormat()).isNull();
            assertThat(reportRow.getInCriticalPath()).isNull();
            assertThat(reportRow.getLocked()).isNull();
            assertThat(reportRow.getLockedForUser()).isNull();
            assertThat(reportRow.getModifiedAt()).isAfterOrEqualTo(rowCreateDate);
            assertThat(reportRow.getModifiedBy()).isNull();
            assertThat(reportRow.getPermalink()).isNull();
            assertThat(reportRow.getRowNumber()).isOne();
            assertThat(reportRow.getVersion()).isNull();
            assertThat(reportRow.getParentId()).isNull();
            assertThat(reportRow.getToTop()).isNull();
            assertThat(reportRow.getToBottom()).isNull();
            assertThat(reportRow.getAbove()).isNull();
            assertThat(reportRow.getIndent()).isNull();
            assertThat(reportRow.getOutdent()).isNull();
            assertThat(reportRow.getDataModifiedAt()).isAfterOrEqualTo(rowCreateDate);
            assertThat(reportRow.getId()).isEqualTo(RowData.id);
            assertThat(reportRow.getSheetId()).isEqualTo(SheetData.id);
            assertThat(reportRow.getSiblingId()).isNull();
            assertThat(reportRow.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            assertThat(reportRow.getAttachments()).isNull();
            assertThat(reportRow.getCells()).isNotEmpty()
                    .allSatisfy(ReportData::assertReportCell);
        }

        static void assertReportCell(Cell reportCell) {
            assertThat(reportCell).isNotNull();

            assertThat(reportCell.getColumnType()).isNull();
            assertThat(reportCell.getConditionalFormat()).isNull();
            assertThat(reportCell.getFormat()).isNull();
            assertThat(reportCell.getFormula()).isNull();
            assertThat(reportCell.getHyperlink()).isNull();
            assertThat(reportCell.getImage()).isNull();
            assertThat(reportCell.getLinkInFromCell()).isNull();
            assertThat(reportCell.getLinksOutToCells()).isEmpty();
            assertThat(reportCell.getObjectValue()).isNull();
            assertThat(reportCell.getOverrideValidation()).isNull();
            assertThat(reportCell.getStrict()).isNull();
            assertThat(reportCell.getValue()).isNotNull()
                    .satisfies(val -> {
                        // clumbsy but not sure how else to handle cell-values that can have many value types
                        switch (Cells.ValueType.getValueType(val)) {
                            case NULL:
                                assertThat(val).withFailMessage("this should not happen").isNull();
                                break;
                            case BOOLEAN:
                                assertThat(val).withFailMessage("this is unexpected").isNull();
                                break;
                            case NUMBER:
                                assertThat(val.getBigDecimal()).isEqualTo(BigDecimal.valueOf(42.0));
                                break;
                            case STRING:
                                assertThat(val.getString()).isEqualTo("Test Sheet 1");
                                break;
                        }
                    });
            assertThat(reportCell.getVirtualColumnId()).matches(Set.of(6693795649507204L, 4441995835821956L)::contains);
        }

        static void assertReportWorkspace(Workspace reportWorkspace) {
            assertThat(reportWorkspace).isNotNull();
            assertThat(reportWorkspace.getId()).isEqualTo(WorkspaceData.id);
            assertThat(reportWorkspace.getName()).isEqualTo(WorkspaceData.name);
            assertThat(reportWorkspace.getAccessLevel()).isNull();
            assertThat(reportWorkspace.getPermalink()).isNull();
            assertThat(reportWorkspace.getFolders()).isEmpty();
            assertThat(reportWorkspace.getReports()).isEmpty();
            assertThat(reportWorkspace.getSheets()).isEmpty();
        }

        static void assertShare(Share share) {
            ShareData.assertCommonShare(share, ShareScope.ITEM, shareDate);
        }

        static void assertPublish(ReportPublish publish) {
            assertThat(publish).isNotNull();
            assertThat(publish.getReadOnlyFullAccessibleBy()).isNull();
            assertThat(publish.getReadOnlyFullDefaultView()).isNull();
            assertThat(publish.getReadOnlyFullEnabled()).isFalse();
            assertThat(publish.getReadOnlyFullShowToolbar()).isNull();
            assertThat(publish.getReadOnlyFullUrl()).isNull();
        }

        static void assertBrief(ReportBrief brief) {
            assertThat(brief).isNotNull();
            assertThat(brief.getId()).isEqualTo(id);
            assertThat(brief.getName()).isEqualTo(name);
            assertThat(brief.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            assertThat(brief.getPermalink()).isNotBlank();
            assertThat(brief.getIsSummaryReport()).isFalse();
        }
    }

    interface RowData {
        long id = 6139161318133636L;
        long attachmentId = 7117529120280452L;
    }

    interface ShareData {
        String shareId = "AAAC8sImFOeE";
        static void assertCommonShare(Share share, ShareScope scope, OffsetDateTime shareDate) {
            assertThat(share).isNotNull();
            assertThat(share.getId()).isEqualTo(ShareData.shareId);
            assertThat(share.getGroupId()).isNull();
            assertThat(share.getUserId()).isEqualTo(UserData.id);
            assertThat(share.getType()).isSameAs(ShareType.USER);
            assertThat(share.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            assertThat(share.getCcMe()).isNull();   // not false?
            assertThat(share.getEmail()).isNotBlank();
            assertThat(share.getMessage()).isNull();
            assertThat(share.getName()).isEqualTo(UserData.name);
            assertThat(share.getScope()).isSameAs(scope);
            assertThat(share.getSubject()).isNull();
            assertThat(share.getCreatedAt()).isEqualTo(shareDate);
            assertThat(share.getModifiedAt()).isAfterOrEqualTo(shareDate);
        }
    }

    interface SheetData {
        long id = 6971132763656068L;
        String name = "Test Sheet 1";
        OffsetDateTime createdDate = OffsetDateTime.of(2025, 4, 2, 14, 27, 31, 0, ZoneOffset.UTC);
        Set<AttachmentType> effectiveAttachmentOptions = Set.of(
                AttachmentType.BOX_COM, AttachmentType.DROPBOX, AttachmentType.EGNYTE, AttachmentType.EVERNOTE,
                AttachmentType.FILE, AttachmentType.GOOGLE_DRIVE, AttachmentType.LINK, AttachmentType.ONEDRIVE);

        OffsetDateTime shareDate = OffsetDateTime.of(2025, 4, 2, 14, 27, 31, 0, ZoneOffset.UTC);

        static void assertEquals(Sheet sheet) {
            assertThat(sheet).isNotNull();
            assertThat(sheet.getName()).isEqualTo(name);
            assertThat(sheet.getId()).isEqualTo(id);
            assertThat(sheet.getCreatedAt()).isEqualTo(createdDate);
            assertThat(sheet.getEffectiveAttachmentOptions()).allMatch(effectiveAttachmentOptions::contains);
        }

        static void assertNotPublished(SheetPublish publish) {
            assertThat(publish).isNotNull();
            assertThat(publish.getIcalEnabled()).isFalse();
            assertThat(publish.getIcalUrl()).isNull();
            assertThat(publish.getReadOnlyFullAccessibleBy()).isNull();
            assertThat(publish.getReadOnlyFullDefaultView()).isNull();
            assertThat(publish.getReadOnlyFullEnabled()).isFalse();
            assertThat(publish.getReadOnlyFullShowToolbar()).isNull();
            assertThat(publish.getReadOnlyFullUrl()).isNull();
            assertThat(publish.getReadOnlyLiteEnabled()).isFalse();
            assertThat(publish.getReadOnlyLiteSslUrl()).isNull();
            assertThat(publish.getReadOnlyLiteUrl()).isNull();
            assertThat(publish.getReadWriteAccessibleBy()).isNull();
            assertThat(publish.getReadWriteDefaultView()).isNull();
            assertThat(publish.getReadWriteEnabled()).isFalse();
            assertThat(publish.getReadWriteShowToolbar()).isNull();
            assertThat(publish.getReadWriteUrl()).isNull();
        }

        static void assertShare(Share share) {
            ShareData.assertCommonShare(share, ShareScope.ITEM, shareDate);
        }

        static void assertListingEquals(SheetListingDataInner listing) {
            assertThat(listing).isNotNull();
            assertThat(listing.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            assertThat(listing.getCreatedAt()).isEqualTo(createdDate);
            assertThat(listing.getId()).isEqualTo(id);
            assertThat(listing.getName()).isEqualTo(name);
            assertThat(listing.getPermalink()).isNotBlank();
            assertThat(listing.getModifiedAt()).isAfterOrEqualTo(createdDate);
            assertThat(listing.getSource()).satisfies(src -> {
                assertThat(src.getId()).isEqualTo(4503604829677444L);
                assertThat(src.getType()).isSameAs(SourceType.SHEET);
            });
            assertThat(listing.getVersion()).isGreaterThanOrEqualTo(9); // as of 2025-05-06
        }

        @NonNull
        static Sheet createTestSheet() {
            return Sheet.builder()
//                    .accessLevel()
//                    .attachments()
//                    .cellImageUploadEnabled()
//                    .columns()
//                    .createdAt()
//                    .crossSheetReferences()
//                    .dependenciesEnabled()
//                    .discussions()
//                    .effectiveAttachmentOptions()
//                    .favorite()
//                    .filters()
                    .fromId(id) // copy
//                    .ganttConfig()
//                    .hasSummaryFields()
//                    .id()
//                    .isMultiPicklistEnabled()
//                    .modifiedAt()
//                    .name()
//                    .owner()
//                    .ownerId()
//                    .permalink()
//                    .projectSettings()
//                    .readOnly()
//                    .resourceManagementEnabled()
//                    .resourceManagementType()
//                    .rows()
//                    .showParentRowsForFilters()
//                    .source()
//                    .summary()
//                    .totalRowCount()
//                    .userPermissions()
//                    .userSettings()
//                    .version()
//                    .workspace()
                    .build();
        }
        @NonNull
        static Sheet createTestSheetCopy() {
            return Sheet.builder()
                    .fromId(id) // copy of the test sheet
                    .build();
        }
    }

    interface TemplateData {
        Template template = Template.builder()
                .id(6674172927233924L)
                .accessLevel(AccessLevel.OWNER)
                .categories(List.of())
                .name("Template of Waiting List")
                .tags(List.of())
                .build();
    }

    interface UserData {
        long userIdToDeactivate = 0L;
        long id = 829865629902724L;
        String firstName = "Ron";
        String lastName = "Reynolds";
        String name = firstName + " " + lastName;
        String locale = "en_US";
        String timezone = "US/Pacific";
        String accountName = "Flock of Singletons";
        long accountId = 429119679817604L;
        boolean isAdmin = true;
        boolean isLicensedSheetCreator = true;
        boolean isGroupAdmin = true;
        List<String> alternateEmails = List.of();

        interface AlternateEmailData {
            long id = 3036181538596740L;
            static void assertMatch(List<? extends AlternateEmail> emailList) {
                assertThat(emailList).isNotEmpty().anySatisfy(email -> assertThat(email.getId()).isEqualTo(id));
            }
        }

        static void assertEquals(GetCurrentUser200Response user) {
            assertThat(user).isNotNull();
            assertThat(user.getAccount()).as("missing account").isNotNull();
            assertThat(user.getAccount().getId()).as("account-id").isEqualTo(accountId);
            assertThat(user.getAccount().getName()).as("account-name").isEqualTo(TestData.UserData.accountName);
            assertThat(user.getAdmin()).as("admin-flag").isEqualTo(TestData.UserData.isAdmin);
            assertThat(user.getFirstName()).as("first-name").isEqualTo(TestData.UserData.firstName);
            assertThat(user.getGroupAdmin()).as("group-admin-flag").isEqualTo(TestData.UserData.isGroupAdmin);
            assertThat(user.getLastName()).as("last-name").isEqualTo(TestData.UserData.lastName);
            assertThat(user.getLicensedSheetCreator()).as("sheet-creator-flag").isEqualTo(TestData.UserData.isLicensedSheetCreator);
            assertThat(user.getLocale()).as("locale").isEqualTo(TestData.UserData.locale);
            assertThat(user.getTimeZone()).as("time-zone").isEqualTo(TestData.UserData.timezone);
            AlternateEmailData.assertMatch(assertThat(user.getAlternateEmails()).isNotNull().actual());
        }

        static void assertEquals(UserProfile user) {
            assertThat(user).isNotNull();
            assertThat(user.getAccount()).as("account").isNotNull();
            assertThat(user.getAccount().getId()).as("account-id").isEqualTo(accountId);
            assertThat(user.getAccount().getName()).as("account-name").isEqualTo(TestData.UserData.accountName);
            assertThat(user.getAdmin()).as("admin-flag").isEqualTo(TestData.UserData.isAdmin);
            assertThat(user.getFirstName()).as("first-name").isEqualTo(TestData.UserData.firstName);
            assertThat(user.getGroupAdmin()).as("group-admin-flag").isEqualTo(TestData.UserData.isGroupAdmin);
            assertThat(user.getLastName()).as("last-name").isEqualTo(TestData.UserData.lastName);
            assertThat(user.getLicensedSheetCreator()).as("sheet-creator-flag").isEqualTo(TestData.UserData.isLicensedSheetCreator);
            assertThat(user.getLocale()).as("locale").isEqualTo(TestData.UserData.locale);
            assertThat(user.getTimeZone()).as("time-zone").isEqualTo(TestData.UserData.timezone);
            AlternateEmailData.assertMatch(assertThat(user.getAlternateEmails()).isNotNull().actual());
        }
    }

    interface WebhookData {
        long id = 0L;   // FIXME
    }

    interface WorkflowData {
        String id = "Test Workflow";
    }

    interface WorkspaceData {
        long id = 4931918228678532L;
        String name = "Test Workspace";
        AccessLevel level = AccessLevel.OWNER;
        OffsetDateTime shareDate = OffsetDateTime.of(2025, 4, 2, 14, 27, 3, 0, ZoneOffset.UTC);

        static void assertEquals(Workspace workspace) {
            assertThat(workspace).isNotNull();
            assertThat(workspace.getId()).isEqualTo(id);
            assertThat(workspace.getName()).isEqualTo(name);
            assertThat(workspace.getAccessLevel()).isSameAs(level);
            assertThat(workspace.getPermalink()).isNotBlank();
        }

        static void assertShare(Share share) {
            ShareData.assertCommonShare(share, ShareScope.WORKSPACE, shareDate);
        }

        // FIXME - make non-inner type
        static void assertFolderInWorkspace(GetWorkspaceFolders200ResponseAllOfDataInner folder) {
            assertThat(folder).isNotNull();
            assertThat(folder.getId()).isEqualTo(1104664725874564L);
            assertThat(folder.getName()).isEqualTo("Test Folder");
        }
    }

    /**
     * these assertions are so common i just couldn't not create a single method to handle them; unfortunately it needs to use
     * reflection because code-gen uses aggregation instead of inheritance (for other good reasons); no choices without tradeoffs
     */
    static void pagedResultHasData(Object pagedResult) {
        // any response object that aggregates the paged-result will have these methods; unfortunately openapi-codegen uses
        // aggregation rather than extension so we can't depend on a base-type :shrug:
        assertThat(pagedResult).isNotNull();
        assertThat(Reflection.invoke(pagedResult, "getPageNumber", Integer.class)).isOne();
        assertThat(Reflection.invoke(pagedResult, "getPageSize", Integer.class)).isPositive();
        assertThat(Reflection.invoke(pagedResult, "getTotalPages", Integer.class)).isPositive();
        assertThat(Reflection.invoke(pagedResult, "getTotalCount", Integer.class)).isPositive();
    }

    /**
     * some APIs respond with a null pageSize if the request had a null pageSize; it's inconsistent across APIs unfortunately
     */
    static void pagedResultHasDataNullPageSize(Object pagedResult) {
        // any response object that aggregates the paged-result will have these methods; unfortunately openapi-codegen uses
        // aggregation rather than extension so we can't depend on a base-type :shrug:
        assertThat(pagedResult).isNotNull();
        assertThat(Reflection.invoke(pagedResult, "getPageNumber", Integer.class)).isOne();
        assertThat(Reflection.invoke(pagedResult, "getPageSize", Integer.class)).isNull();
        assertThat(Reflection.invoke(pagedResult, "getTotalPages", Integer.class)).isPositive();
        assertThat(Reflection.invoke(pagedResult, "getTotalCount", Integer.class)).isPositive();
    }


    static void successfulResult(Result result) {
        assertThat(result).isNotNull();
        assertThat(result.getFailedItems()).isEmpty();
        assertThat(result.getResultCode()).isSameAs(Result.ResultCodeEnum.NUMBER_0);
        assertThat(result.getMessage()).isSameAs(Result.MessageEnum.SUCCESS);
        // what's result.version about?
    }

    static void successfulResult(ResultPrefix result) {
        assertThat(result).isNotNull();
        assertThat(result.getResultCode()).isSameAs(Result.ResultCodeEnum.NUMBER_0);
        assertThat(result.getMessage()).isSameAs(Result.MessageEnum.SUCCESS);
    }
}
