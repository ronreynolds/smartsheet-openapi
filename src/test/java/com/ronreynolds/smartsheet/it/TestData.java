package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.api.util.Cells;
import com.ronreynolds.smartsheet.api.util.DateTimes;
import com.ronreynolds.smartsheet.model.AccessLevel;
import com.ronreynolds.smartsheet.model.AlternateEmail;
import com.ronreynolds.smartsheet.model.Attachment;
import com.ronreynolds.smartsheet.model.AttachmentType;
import com.ronreynolds.smartsheet.model.AttachmentTypeTrello;
import com.ronreynolds.smartsheet.model.Cell;
import com.ronreynolds.smartsheet.model.CellHistoryGet200ResponseAllOfDataInner;
import com.ronreynolds.smartsheet.model.CellObjectValue;
import com.ronreynolds.smartsheet.model.CellValue;
import com.ronreynolds.smartsheet.model.Column;
import com.ronreynolds.smartsheet.model.ColumnBrief;
import com.ronreynolds.smartsheet.model.ColumnType;
import com.ronreynolds.smartsheet.model.Comment;
import com.ronreynolds.smartsheet.model.Contact;
import com.ronreynolds.smartsheet.model.Folder;
import com.ronreynolds.smartsheet.model.GetCurrentUser200Response;
import com.ronreynolds.smartsheet.model.GetWorkspaceFolders200ResponseAllOfDataInner;
import com.ronreynolds.smartsheet.model.ImageUrl;
import com.ronreynolds.smartsheet.model.ListSights200ResponseAllOfDataInner;
import com.ronreynolds.smartsheet.model.NameAndEmail;
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
import com.ronreynolds.smartsheet.model.Sight;
import com.ronreynolds.smartsheet.model.SourceType;
import com.ronreynolds.smartsheet.model.Template;
import com.ronreynolds.smartsheet.model.UserProfile;
import com.ronreynolds.smartsheet.model.UserProfileAccount;
import com.ronreynolds.smartsheet.model.Workspace;
import com.ronreynolds.util.reflection.Reflection;
import com.ronreynolds.util.streams.ExtCollectors;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
            OffsetDateTime createdDate = getDate("AttachmentData.CommentAttachment.createdDate");
            String name = get("AttachmentData.CommentAttachment.name");
            int sizeInKb = 10;

            static void assertEquals(Attachment attachment) {
                assertThat(attachment).isNotNull();
                assertThat(attachment.getId()).as("id").isEqualTo(id);
                assertThat(attachment.getName()).as("name").isEqualTo(name);
                assertThat(attachment.getAttachmentType()).as("attachment-type").isSameAs(attachmentType);
                assertThat(attachment.getMimeType()).as("mime-type").isEqualTo(mimeType);
                assertThat(attachment.getCreatedAt()).as("created-at").isEqualTo(createdDate);
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
            OffsetDateTime createdDate = getDate("AttachmentData.RowAttachment.createdDate");
            String name = get("AttachmentData.RowAttachment.name");
            int sizeInKb = 751;

            static void assertEquals(Attachment attachment) {
                assertThat(attachment).isNotNull();
                assertThat(attachment.getId()).as("id").isEqualTo(id);
                assertThat(attachment.getParentId()).as("parent id").isEqualTo(parentId);
                assertThat(attachment.getName()).as("name").isEqualTo(name);
                assertThat(attachment.getParentType()).as("parent type").isSameAs(parentType);
                assertThat(attachment.getAttachmentType()).as("attachment-type").isSameAs(attachmentType);
                assertThat(attachment.getMimeType()).as("mime-type").isEqualTo(mimeType);
                assertThat(attachment.getCreatedAt()).as("created-at").isEqualTo(createdDate);
                assertThat(attachment.getSizeInKb()).as("size-in-kb").isEqualTo(sizeInKb);
            }
        }

        interface SheetAttachment {
            long id = getLong("AttachmentData.SheetAttachment.id");
            long parentId = SheetData.id;
            Attachment.ParentTypeEnum parentType = Attachment.ParentTypeEnum.SHEET;
            AttachmentTypeTrello attachmentType = AttachmentTypeTrello.FILE;
            String mimeType = "image/jpeg";
            OffsetDateTime createdDate = getDate("AttachmentData.SheetAttachment.createdDate");
            String name = get("AttachmentData.SheetAttachment.name");
            int sizeInKb = 212;

            static void assertEquals(Attachment attachment) {
                assertThat(attachment).isNotNull();
                assertThat(attachment.getId()).as("id").isEqualTo(id);
                assertThat(attachment.getParentId()).as("parent id").isEqualTo(parentId);
                assertThat(attachment.getName()).as("name").isEqualTo(name);
                assertThat(attachment.getParentType()).as("parent type").isSameAs(parentType);
                assertThat(attachment.getAttachmentType()).as("attachment-type").isSameAs(attachmentType);
                assertThat(attachment.getMimeType()).as("mime-type").isEqualTo(mimeType);
                assertThat(attachment.getCreatedAt()).as("created-at").isEqualTo(createdDate);
                assertThat(attachment.getSizeInKb()).as("size-in-kb").isEqualTo(sizeInKb);
            }
        }
    }

    interface CellData {
        long id = 0L;
        Object value = 42;
    }

    interface ColumnData {
        enum ColumnBriefData {
            // these MUST appear in the same order as they appear in the sheet (since we use the ordinal as the column index)
            PRIMARY,
            IMAGE,
            COLUMN3,
            COLUMN4,
            COLUMN5,
            COLUMN6,
            ;

            final long id;
            final String name;
            final ColumnType type;
            final int index;

            ColumnBriefData() {
                index = ordinal();
                id = getLong("ColumnData.ColumnBriefData." + index + ".id");
                name = get("ColumnData.ColumnBriefData." + index + ".title");
                type = ColumnType.valueOf(get("ColumnData.ColumnBriefData." + index + ".type"));
            }

            void assertMatches(ColumnBrief column) {
                assertThat(column).isNotNull();
                assertThat(column.getIndex()).isEqualTo(ordinal());
                assertThat(column.getId()).isEqualTo(id);
                assertThat(column.getTitle()).isEqualTo(name);
                assertThat(column.getType()).isSameAs(type);
                assertThat(column.getSymbol()).isNull();
                assertThat(column.getVersion()).isZero();
                assertThat(column.getWidth()).isEqualTo(150);

                if (ordinal() == 0) {
                    assertThat(column.getPrimary()).isTrue();
                } else {
                    assertThat(column.getPrimary()).isNull();   // weird; wouldn't false make more sense?
                }

                if (type == ColumnType.DATE) {
                    assertThat(column.getValidation()).isTrue();
                } else {
                    assertThat(column.getValidation()).isFalse();
                }
            }
        }

        ColumnBriefData[] columnBriefData = ColumnBriefData.values();
        OffsetDateTime modifiedDate = getDate("ColumnData.modifiedDate");

        static void assertColumnBriefs(ColumnBrief column) {
            assertThat(column).isNotNull();
            int index = assertThat(column.getIndex()).isNotNull().isBetween(0, columnBriefData.length - 1).actual();
            assertThat(column).satisfies(columnBriefData[index]::assertMatches);
        }

        static void assertColumnHistory(CellHistoryGet200ResponseAllOfDataInner history) {
            assertThat(history).isNotNull();
            assertThat(history.getModifiedAt()).isAfterOrEqualTo(modifiedDate);
            assertThat(history.getModifiedBy()).isEqualTo(UserData.getNameAndEmail());
            assertThat(history.getColumnId()).isEqualTo(ColumnBriefData.PRIMARY.id);
            assertThat(history.getColumnType()).isSameAs(ColumnBriefData.PRIMARY.type);
            assertThat(history.getConditionalFormat()).isNull();
            assertThat(history.getDisplayValue()).isEqualTo("42");
            assertThat(history.getFormat()).isNull();
            assertThat(history.getFormula()).isNull();
            assertThat(history.getHyperlink()).isNull();
            assertThat(history.getImage()).isNull();
            assertThat(history.getLinkInFromCell()).isNull();
            assertThat(history.getLinksOutToCells()).isEmpty();
            assertThat(history.getObjectValue()).isEqualTo(new CellObjectValue(BigDecimal.valueOf(42.0)));
            assertThat(history.getOverrideValidation()).isNull();
            assertThat(history.getStrict()).isNull();
            assertThat(history.getValue()).isEqualTo(new CellValue(BigDecimal.valueOf(42.0)));
            assertThat(history.getVirtualColumnId()).isNull();
        }
    }

    interface CommentData {
        long id = getLong("CommentData.id");
        String text = get("CommentData.text");
        long attachmentId = getLong("CommentData.attachmentId");
        long discussionId = getLong("CommentData.discussionId");
        OffsetDateTime createdDate = getDate("CommentData.createdDate");

        static void assertEquals(Comment comment) {
            assertThat(comment).isNotNull();
            assertThat(comment.getId()).as("id").isEqualTo(id);
            assertThat(comment.getDiscussionId()).as("discussion id").isEqualTo(discussionId);
            assertThat(comment.getText()).as("text").isEqualTo(text);
            assertThat(comment.getCreatedAt()).as("created-at").isEqualTo(createdDate);

            // assert on the attachment too
            assertThat(comment.getAttachments())
                    .isNotEmpty()
                    .first().satisfies(AttachmentData.CommentAttachment::assertEquals);
        }
    }

    interface ContactData {
        Set<Contact> contacts = new HashSet<>(
                getListOfItems("ContactData.contact",
                        props -> Contact.builder()
                                .email(props.get("email"))
                                .id(props.get("id"))
                                .name(props.get("name"))
                                .build()));

        static void assertContains(List<? extends Contact> contactList) {
            assertThat(contactList).isNotEmpty().allMatch(contacts::contains);
        }

        static void assertFirstContact(Contact contact) {
            assertThat(contact).isNotNull().isEqualTo(contacts.iterator().next());
        }
    }

    interface DashboardData {
        long id = getLong("DashboardData.id");
        String name = get("DashboardData.name");
        OffsetDateTime shareDate = getDate("DashboardData.shareDate");
        Map<Long,Sight> dashboardMap = getListOfItems("DashboardData.dashboards",
                fieldMap -> Sight.builder()
                        .accessLevel(AccessLevel.valueOf(fieldMap.get("accessLevel")))
                        .id(Long.parseLong(fieldMap.get("id")))
                        .name(fieldMap.get("name"))
                        .createdAt(DateTimes.parseToOffset(fieldMap.get("createdDate")))
                        .modifiedAt(DateTimes.parseToOffset(fieldMap.get("modifiedDate")))
                        .build()
        ).stream().map(sight -> Map.entry(sight.getId(), sight)).collect(ExtCollectors.entriesToMap());
        Set<String> shareIdsToDelete = getCollectionStartingWith("DashboardData.shareIdsToDelete", HashSet::new, v -> v);
        Set<Long> sightIdsToDelete = getCollectionStartingWith("DashboardData.sightIdsToDelete", HashSet::new, Long::parseLong);

        static void assertShare(Share share) {
            ShareData.assertCommonShare(share, ShareScope.ITEM, shareDate);
            assertThat(share.getCreatedAt()).isEqualTo(shareDate);
            assertThat(share.getModifiedAt()).isAfterOrEqualTo(shareDate);
        }
        static void assertContains(List<? extends ListSights200ResponseAllOfDataInner> dashboardList) {
            log.info("{}", dashboardList);
            assertThat(dashboardList).isNotEmpty();
            assertThat(dashboardList).allSatisfy(val -> {
                Sight dashboard = dashboardMap.get(val.getId());
                assertThat(dashboard).as("has dashboard").isNotNull();
                assertThat(val.getPermalink()).isNotBlank();
                assertThat(val.getName()).isEqualTo(dashboard.getName());
                assertThat(val.getAccessLevel()).isSameAs(dashboard.getAccessLevel());
                assertThat(val.getCreatedAt()).isEqualTo(dashboard.getCreatedAt());
                assertThat(val.getModifiedAt()).isAfterOrEqualTo(dashboard.getModifiedAt());
            });
        }
    }

    interface FolderData {
        long id = getLong("FolderData.id");
        String name = get("FolderData.name");
        long childFolder1Id = getLong("FolderData.childFolder.1.id");
        String childFolder1Name = get("FolderData.childFolder.1.name");
        long childFolder2Id = getLong("FolderData.childFolder.2.id");
        String childFolder2Name = get("FolderData.childFolder.2.name");

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
                        assertThat(folder.getFolders()).isEmpty();
                        assertThat(folder.getReports()).isEmpty();
                        assertThat(folder.getSheets()).isEmpty();
                        assertThat(folder.getSights()).isEmpty();
                        assertThat(folder.getTemplates()).isEmpty();
                        assertThat(folder.getId()).isNotNull();
                        if (folder.getId() == childFolder1Id) {
                            assertThat(folder.getName()).isEqualTo(childFolder1Name);
                        } else if (folder.getId() == childFolder2Id) {
                            assertThat(folder.getName()).isEqualTo(childFolder2Name);
                        }
                    });
//            log.info("child-folders:{}", childFolders);
        }
    }

    interface ProofData {
        long id = 0L;   // FIXME
    }

    interface ImageData {
        String id1 = get("ImageData.id.1");
        String id2 = get("ImageData.id.2");
        Set<String> idSet = Set.of(id1, id2);

        static void assertImageUrl(ImageUrl url) {
            assertThat(url).isNotNull();
            assertThat(url.getError()).isNull();
            assertThat(url.getHeight()).isNull();
            assertThat(url.getWidth()).isNull();
            assertThat(url.getUrl()).isNotBlank();
            assertThat(url.getImageId()).matches(idSet::contains);
        }
    }

    interface ReportData {
        long id = getLong("ReportData.id");
        String name = get("ReportData.name");
        Set<Long> virtualColumnIds = getCollectionStartingWith("ReportData.virtualColumnId", HashSet::new, Long::parseLong);
        OffsetDateTime shareDate = getDate("ReportData.shareDate");
        OffsetDateTime createdDate = getDate("ReportData.createdDate");
        OffsetDateTime modifiedDate = getDate("ReportData.modifiedDate");
        OffsetDateTime rowCreateDate = getDate("ReportData.rowCreateDate");

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
            assertThat(report.getCreatedAt()).isEqualTo(createdDate);
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
            assertThat(report.getModifiedAt()).isAfterOrEqualTo(modifiedDate);
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
            assertThat(reportColumn.getVirtualId()).matches(virtualColumnIds::contains);
            assertThat(reportColumn.getSheetNameColumn()).matches(ReportData::isNullOrTrue);
        }

        static boolean isNullOrTrue(Boolean val) {
            return val == null || val;
        }

        static void assertReportRow(Row reportRow) {
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
            assertThat(reportRow.getCells()).isNotEmpty().allSatisfy(ReportData::assertReportCell);
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
            assertThat(reportCell.getVirtualColumnId()).matches(virtualColumnIds::contains);
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
        long id = getLong("RowData.id");
        long attachmentId = getLong("RowData.attachmentId");
    }

    interface ShareData {
        String shareId = get("ShareData.shareId");
        String shareToEmail = get("ShareData.shareToEmail");

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
        long id = getLong("SheetData.id");
        String name = get("SheetData.name");
        long sourceId = getLong("SheetData.sourceId");

        OffsetDateTime createdDate = getDate("SheetData.createdDate");
        OffsetDateTime shareDate = getDate("SheetData.shareDate");
        Set<AttachmentType> effectiveAttachmentOptions = Set.of(
                AttachmentType.BOX_COM, AttachmentType.DROPBOX, AttachmentType.EGNYTE, AttachmentType.EVERNOTE,
                AttachmentType.FILE, AttachmentType.GOOGLE_DRIVE, AttachmentType.LINK, AttachmentType.ONEDRIVE);


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
                assertThat(src.getId()).isEqualTo(sourceId);
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
        long id = getLong("TemplateData.id");
        String name = get("TemplateData.name");

        Template template = Template.builder()
                .id(id)
                .accessLevel(AccessLevel.OWNER)
                .categories(List.of())
                .name(name)
                .tags(List.of())
                .build();
    }

    interface UserData {
        long id = getLong("UserData.id");
        String email = get("UserData.email");
        String firstName = get("UserData.firstName");
        String lastName = get("UserData.lastName");
        String name = firstName + " " + lastName;
        String locale = "en_US";
        String timezone = "US/Pacific";
        String accountName = get("UserData.accountName");
        long accountId = getLong("UserData.accountId");
        boolean isAdmin = true;
        boolean isLicensedSheetCreator = true;
        boolean isGroupAdmin = true;

        interface AlternateEmailData {
            long id = getLong("UserData.AlternateEmailData.id");
            static void assertMatch(List<? extends AlternateEmail> emailList) {
                assertThat(emailList).isNotEmpty().anySatisfy(email -> assertThat(email.getId()).isEqualTo(id));
            }
        }

        static NameAndEmail getNameAndEmail() {
            return NameAndEmail.builder()
                    .email(email)
                    .name(name)
                    .build();
        }
        static void assertEquals(GetCurrentUser200Response user) {
            assertThat(user).isNotNull();
            UserProfileAccount account = assertThat(user.getAccount()).as("account").isNotNull().actual();
            assertThat(account.getId()).as("account-id").isEqualTo(accountId);
            assertThat(account.getName()).as("account-name").isEqualTo(accountName);
            assertThat(user.getEmail()).as("email").isEqualTo(email);
            assertThat(user.getAdmin()).as("admin-flag").isEqualTo(isAdmin);
            assertThat(user.getFirstName()).as("first-name").isEqualTo(firstName);
            assertThat(user.getGroupAdmin()).as("group-admin-flag").isEqualTo(isGroupAdmin);
            assertThat(user.getLastName()).as("last-name").isEqualTo(lastName);
            assertThat(user.getLicensedSheetCreator()).as("sheet-creator-flag").isEqualTo(isLicensedSheetCreator);
            assertThat(user.getLocale()).as("locale").isEqualTo(locale);
            assertThat(user.getTimeZone()).as("time-zone").isEqualTo(timezone);
            assertThat(user.getAlternateEmails()).satisfies(AlternateEmailData::assertMatch);
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
        long id = getLong("WebhookData.id");
    }

    interface WorkflowData {
        String id = get("WorkflowData.id");
    }

    interface WorkspaceData {
        long id = getLong("WorkspaceData.id");
        String name = get("WorkspaceData.name");
        long folderId = getLong("WorkspaceData.folderId");
        String folderName = get("WorkspaceData.folderName");
        AccessLevel level = AccessLevel.OWNER;
        OffsetDateTime shareDate = getDate("WorkspaceData.shareDate");

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
            assertThat(folder.getId()).isEqualTo(folderId);
            assertThat(folder.getName()).isEqualTo(folderName);
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
        assertThat(result.getFailedItems()).isNullOrEmpty();
        assertThat(result.getResultCode()).isSameAs(Result.ResultCodeEnum.NUMBER_0);
        assertThat(result.getMessage()).isSameAs(Result.MessageEnum.SUCCESS);
        // what's result.version about?
    }

    static void successfulResult(ResultPrefix result) {
        assertThat(result).isNotNull();
        assertThat(result.getResultCode()).isSameAs(ResultPrefix.ResultCodeEnum.NUMBER_0);
        assertThat(result.getMessage()).isSameAs(ResultPrefix.MessageEnum.SUCCESS);
    }

    /**
     * because OpenAPI-codegen uses individual inner-classes we need the specific success code and message values against which to
     * assert
     */
    static void successfulResult(Object result, Object successCode, Object successMessage) {
        assertThat(result).as("result not null").isNotNull();
        if (Reflection.hasMethod(result.getClass(), "getFailedItems")) {
            assertThat(Reflection.invoke(result, "getFailedItems", List.class)).as("no failed items").isNullOrEmpty();
        }
        assertThat(Reflection.invoke(result, "getResultCode", successCode.getClass()))
                .as("success result code").isSameAs(successCode);
        assertThat(Reflection.invoke(result, "getMessage", successMessage.getClass()))
                .as("success message").isSameAs(successMessage);
    }

    // all "secret" values are moved to a properties file that is not checked in
    private static final Properties secrets = new Properties();
    static {
        try {
            secrets.load(new FileReader("src/test/resources/secrets.props"));
        } catch (IOException iox) {
            throw new RuntimeException(iox);
        }
    }

    private static String get(String name) {
        return assertThat(secrets.getProperty(name)).as("get(" + name + ")").isNotBlank().actual();
    }

    private static OffsetDateTime getDate(String name) {
        return DateTimes.parseToOffset(get(name));
    }

    private static long getLong(String name) {
        return Long.parseLong(get(name));
    }

    private static <T, CollectionT extends Collection<T>> CollectionT getCollectionStartingWith(
            String prefix, Supplier<CollectionT> collectionFactory, Function<String, T> valueConverter) {
        assertThat(valueConverter).as("check converter").isNotNull();
        assertThat(collectionFactory).as("check collection factory").isNotNull();
        return getPropertiesStartingWith(prefix).values().stream()
                .map(valueConverter)
                .collect(Collectors.toCollection(collectionFactory));
    }

    private static Map<String, String> getPropertiesStartingWith(String namePrefix) {
        // matches all names that start with the prefix
        return secrets.stringPropertyNames().stream()
                .filter(key -> key.startsWith(namePrefix))
                .map(key -> Map.entry(key, secrets.getProperty(key)))
                .collect(ExtCollectors.entriesToMap());
    }

    /**
     * generate a list of T given their property name prefix and a factory method to convert the Map of key-value pairs to T.
     *
     * @param namePrefix  key prefix by which to find all properties to use; property keys must be of the form "PREFIX.DIGITS
     *                    .FIELD"
     * @param itemFactory a method-ref to invoke to convert {@code Map<String,String>} of key-value pairs into type T
     * @param <T>         the type of the object created from the property groups
     * @return a {@code List<T>} containing all T created from all properties with {@code namePrefix} grouped by DIGIT(S)
     */
    private static <T> List<T> getListOfItems(String namePrefix, Function<Map<String, String>, T> itemFactory) {
        assertThat(itemFactory).isNotNull();

        Map<String, String> properties = getPropertiesStartingWith(namePrefix + ".");
        Pattern keyPattern = Pattern.compile(Pattern.quote(namePrefix) + "\\.(\\d+)\\.(.+)");

        // gather up all the key-value pairs by digit suffix
        Map<Integer, Map<String, String>> mapsByDigit = new HashMap<>();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            Matcher mat = keyPattern.matcher(entry.getKey());
            if (mat.matches()) {
                Integer digits = Integer.parseInt(mat.group(1));
                String field = mat.group(2);
                Map<String, String> keyValuePairs = mapsByDigit.computeIfAbsent(digits, ignore -> new HashMap<>());
                keyValuePairs.put(field, entry.getValue());
            } else {
                log.warn("key '{}' didn't match expect key-pattern '{}'", entry.getKey(), keyPattern.pattern());
            }
        }

        List<T> newList = new ArrayList<>();
        for (Map<String, String> valueMap : mapsByDigit.values()) {
            newList.add(itemFactory.apply(valueMap));
        }
        return newList;
    }
}
