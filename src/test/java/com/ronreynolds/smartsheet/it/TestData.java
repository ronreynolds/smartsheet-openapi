package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.api.util.Cells;
import com.ronreynolds.smartsheet.api.util.DateTimes;
import com.ronreynolds.smartsheet.model.AccessLevel;
import com.ronreynolds.smartsheet.model.Account;
import com.ronreynolds.smartsheet.model.AlternateEmail;
import com.ronreynolds.smartsheet.model.Attachment;
import com.ronreynolds.smartsheet.model.AttachmentParentType;
import com.ronreynolds.smartsheet.model.AttachmentSubType;
import com.ronreynolds.smartsheet.model.AttachmentTypeWithSmartsheet;
import com.ronreynolds.smartsheet.model.AttachmentTypeWithTrello;
import com.ronreynolds.smartsheet.model.Cell;
import com.ronreynolds.smartsheet.model.CellHistory;
import com.ronreynolds.smartsheet.model.CellValue;
import com.ronreynolds.smartsheet.model.Column;
import com.ronreynolds.smartsheet.model.ColumnType;
import com.ronreynolds.smartsheet.model.ColumnVersion;
import com.ronreynolds.smartsheet.model.Comment;
import com.ronreynolds.smartsheet.model.Contact;
import com.ronreynolds.smartsheet.model.Discussion;
import com.ronreynolds.smartsheet.model.DiscussionParentType;
import com.ronreynolds.smartsheet.model.Favorite;
import com.ronreynolds.smartsheet.model.FavoriteType;
import com.ronreynolds.smartsheet.model.Folder;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.GetColumn;
import com.ronreynolds.smartsheet.model.GetCurrentUser200Response;
import com.ronreynolds.smartsheet.model.GetWorkspaceFolders200ResponseAllOfDataInner;
import com.ronreynolds.smartsheet.model.GridListing;
import com.ronreynolds.smartsheet.model.ImageUrl;
import com.ronreynolds.smartsheet.model.MiniReport;
import com.ronreynolds.smartsheet.model.MiniSheet;
import com.ronreynolds.smartsheet.model.MiniUser;
import com.ronreynolds.smartsheet.model.ObjectValue;
import com.ronreynolds.smartsheet.model.Report;
import com.ronreynolds.smartsheet.model.ReportPublish;
import com.ronreynolds.smartsheet.model.Result;
import com.ronreynolds.smartsheet.model.ResultCode;
import com.ronreynolds.smartsheet.model.ResultMessage;
import com.ronreynolds.smartsheet.model.Row;
import com.ronreynolds.smartsheet.model.Share;
import com.ronreynolds.smartsheet.model.ShareScope;
import com.ronreynolds.smartsheet.model.ShareType;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.smartsheet.model.SheetPublish;
import com.ronreynolds.smartsheet.model.Sight;
import com.ronreynolds.smartsheet.model.SightListItem;
import com.ronreynolds.smartsheet.model.SourceType;
import com.ronreynolds.smartsheet.model.Template;
import com.ronreynolds.smartsheet.model.UserProfile;
import com.ronreynolds.smartsheet.model.Workspace;
import com.ronreynolds.smartsheet.model.WorkspaceListing;
import com.ronreynolds.util.properties.ExtProperties;
import com.ronreynolds.util.reflection.Reflection;
import com.ronreynolds.util.streams.ExtCollectors;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * various bits of test constants
 */
@Slf4j
public class TestData {
    // all "secret" values are moved to a properties file that is not checked in
    private static final ExtProperties secrets = ExtProperties.load("src/test/resources/secrets.props");

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
            AttachmentParentType parentType = AttachmentParentType.COMMENT;
            AttachmentTypeWithTrello attachmentType = AttachmentTypeWithTrello.FILE;
            String mimeType = "image/gif";
            OffsetDateTime createdDate = secrets.getDate("AttachmentData.CommentAttachment.createdDate");
            String name = secrets.get("AttachmentData.CommentAttachment.name");
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
            AttachmentParentType parentType = AttachmentParentType.ROW;
            AttachmentTypeWithTrello attachmentType = AttachmentTypeWithTrello.FILE;
            String mimeType = "image/jpeg";
            OffsetDateTime createdDate = secrets.getDate("AttachmentData.RowAttachment.createdDate");
            String name = secrets.get("AttachmentData.RowAttachment.name");
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
            long id = secrets.getLong("AttachmentData.SheetAttachment.id");
            long parentId = SheetData.id;
            AttachmentParentType parentType = AttachmentParentType.SHEET;
            AttachmentTypeWithTrello attachmentType = AttachmentTypeWithTrello.FILE;
            String mimeType = "image/jpeg";
            OffsetDateTime createdDate = secrets.getDate("AttachmentData.SheetAttachment.createdDate");
            String name = secrets.get("AttachmentData.SheetAttachment.name");
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
                id = secrets.getLong("ColumnData.ColumnBriefData." + index + ".id");
                name = secrets.get("ColumnData.ColumnBriefData." + index + ".title");
                type = ColumnType.valueOf(secrets.get("ColumnData.ColumnBriefData." + index + ".type"));
            }

            void assertMatches(GetColumn column) {
                assertThat(column).isNotNull();
                assertThat(column.getIndex()).isEqualTo(ordinal());
                assertThat(column.getId()).isEqualTo(id);
                assertThat(column.getTitle()).isEqualTo(name);
                assertThat(column.getType()).isSameAs(type);
                assertThat(column.getSymbol()).isNull();
//FIXME                assertThat(column.getVersion()).isZero();
//FIXME                assertThat(column.getWidth()).isEqualTo(150);

                if (ordinal() == 0) {
//FIXME                    assertThat(column.getPrimary()).isTrue();
                } else {
//FIXME                    assertThat(column.getPrimary()).isNull();   // weird; wouldn't false make more sense?
                }

                if (type == ColumnType.DATE) {
                    assertThat(column.getValidation()).isTrue();
                } else {
                    assertThat(column.getValidation()).isFalse();
                }
            }
        }

        ColumnBriefData[] columnBriefData = ColumnBriefData.values();
        OffsetDateTime modifiedDate = secrets.getDate("ColumnData.modifiedDate");

        static void assertColumnBriefs(GetColumn column) {
            assertThat(column).isNotNull();
            int index = assertThat(column.getIndex()).isNotNull().isBetween(0, columnBriefData.length - 1).actual();
            assertThat(column).satisfies(columnBriefData[index]::assertMatches);
        }

        static void assertColumnHistory(CellHistory history) {
            assertThat(history).isNotNull();
            assertThat(history.getModifiedAt()).isAfterOrEqualTo(modifiedDate);
            assertThat(history.getModifiedBy()).isEqualTo(UserData.nameAndEmail);
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
            assertThat(history.getOverrideValidation()).isNull();
            assertThat(history.getStrict()).isNull();
            assertThat(history.getValue()).isEqualTo(new CellValue(BigDecimal.valueOf(42.0)));
//FIXME            assertThat(history.getVirtualColumnId()).isNull();
        }
    }

    interface CommentData {
        long id = secrets.getLong("CommentData.id");
        String text = secrets.get("CommentData.text");
        long attachmentId = secrets.getLong("CommentData.attachmentId");
        long discussionId = secrets.getLong("CommentData.discussionId");
        OffsetDateTime createdDate = secrets.getDate("CommentData.createdDate");

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
        Set<Contact> contacts = secrets.getObjectsCollection("ContactData.contact", HashSet::new,
                props -> Contact.builder()
                        .email(props.get("email"))
                        .id(props.get("id"))
                        .name(props.get("name"))
                        .build());

        static void assertContains(List<? extends Contact> contactList) {
            assertThat(contactList).isNotEmpty().allMatch(contacts::contains);
        }

        static void assertFirstContact(Contact contact) {
            assertThat(contact).isNotNull().isEqualTo(contacts.iterator().next());
        }
    }

    interface DiscussionData {
        long id = secrets.getLong("DiscussionData.id");
        AccessLevel accessLevel = secrets.getUC("DiscussionData.accessLevel", AccessLevel::valueOf);
        List<Comment> comments = null; // FIXME
        List<Attachment> commentAttachments = List.of(); // FIXME
        int commentCount = secrets.getInt("DiscussionData.commentCount");
        MiniUser createdBy = secrets.getObject("DiscussionData.createdBy", TestData::mapToNameAndEmail);
        OffsetDateTime lastCommentedAt = secrets.getDate("DiscussionData.lastCommentedAt");
        MiniUser lastCommentedUser = secrets.getObject("DiscussionData.lastCommentedUser", TestData::mapToNameAndEmail);
        Long parentId = secrets.getLong("DiscussionData.parentId");
        DiscussionParentType parentType = secrets.getUC("DiscussionData.parentType", DiscussionParentType::valueOf);
        Boolean readOnly = secrets.getBool("DiscussionData.readOnly", null);
        String title = secrets.get("DiscussionData.title");

        static void assertEquals(Discussion discussion) {
            log.info("discussion:{}", discussion);

            assertThat(discussion).isNotNull();
            assertThat(discussion.getId()).isEqualTo(id);
            assertThat(discussion.getAccessLevel()).isSameAs(accessLevel);
            if (discussion.getComments() != null) {
                // FIXME
            }
            if (discussion.getCommentAttachments() != null) {
                // FIXME
            }
            assertThat(discussion.getCommentCount()).isEqualTo(commentCount);
            assertThat(discussion.getCreatedBy()).isEqualTo(createdBy);
            assertThat(discussion.getLastCommentedAt()).isAfterOrEqualTo(lastCommentedAt);
            assertThat(discussion.getLastCommentedUser()).isEqualTo(lastCommentedUser);
            assertThat(discussion.getTitle()).isEqualTo(title);

            // certain API endpoints don't populate these fields
            if (discussion.getParentId() != null) {
                assertThat(discussion.getParentId()).isEqualTo(parentId);
            }
            if (discussion.getParentType() != null) {
                assertThat(discussion.getParentType()).isEqualTo(parentType);
            }
            if (discussion.getReadOnly() != null) {
                assertThat(discussion.getReadOnly()).isEqualTo(readOnly);
            }
        }

        static void assertEqualsAttachment(Attachment attachment) {
            assertThat(attachment).isNotNull();
            final String PREFIX = "DiscussionData.attachment.";
            assertThat(attachment.getId()).isEqualTo(secrets.getLong(PREFIX + "id"));
            assertThat(attachment.getParentId()).isEqualTo(secrets.getLong(PREFIX + "parentId"));
            assertThat(attachment.getAttachmentType())
                    .isEqualTo(secrets.getUC(PREFIX + "attachmentType", AttachmentTypeWithTrello::valueOf));
            assertThat(attachment.getAttachmentSubType())
                    .isEqualTo(secrets.getUC(PREFIX + "attachmentSubType", AttachmentSubType::valueOf));
            assertThat(attachment.getMimeType()).isEqualTo(secrets.get(PREFIX + "mimeType"));
            assertThat(attachment.getParentType())
                    .isEqualTo(secrets.getUC(PREFIX + "parentType", AttachmentParentType::valueOf));
            assertThat(attachment.getCreatedAt()).isEqualTo(secrets.getDate(PREFIX + "createdAt"));
            assertThat(attachment.getCreatedBy()).isEqualTo(secrets.getObject(PREFIX + "createdBy", TestData::mapToNameAndEmail));
            assertThat(attachment.getName()).isEqualTo(secrets.get(PREFIX + "name"));
            assertThat(attachment.getSizeInKb()).isEqualTo(secrets.getInt(PREFIX + "sizeInKb"));
            assertThat(attachment.getUrl()).isEqualTo(secrets.get(PREFIX + "url"));
            assertThat(attachment.getUrlExpiresInMillis()).isEqualTo(secrets.getInt(PREFIX + "urlExpiresInMillis"));
        }
        static void assertContains(List<? extends Discussion> discussionList) {
            assertThat(discussionList).isNotEmpty().anySatisfy(DiscussionData::assertEquals);
        }

        static void assertContainsAttachment(List<? extends Attachment> attachments) {
            assertThat(attachments).isNotEmpty().anySatisfy(DiscussionData::assertEqualsAttachment);
        }
    }

    interface FavoriteData {
        Set<Favorite> favorites = secrets.getObjectsCollection("FavoriteData.favorites", HashSet::new,
                map -> Favorite.builder()
                        .objectId(Long.parseLong(map.get("id")))
                        .type(FavoriteType.valueOf(map.get("type").toUpperCase()))
//FIXME                        .name(map.get("name"))
//FIXME                        .directId(map.get("directId"))
                        .build()
        );
        static void assertContains(List<? extends Favorite> favoriteList) {
            assertThat(favoriteList).isNotEmpty();
            // assert that all the favorites in our favorites list are in the favoriteList
            for (Favorite favorite : favorites) {
                assertThat(favoriteList).anySatisfy(fav -> {
                    assertThat(fav.getObjectId()).isEqualTo(favorite.getObjectId());
                    assertThat(fav.getType()).isSameAs(favorite.getType());
                    // not all of Favorite lists have all 4 fields populated :-/
//FIXME                    if (fav.getName() != null) {
//FIXME                        assertThat(fav.getName()).isEqualTo(favorite.getName());
//FIXME                    }
//FIXME                    if (fav.getDirectId() != null) {
//FIXME                        assertThat(fav.getDirectId()).isEqualTo(favorite.getDirectId());
//FIXME                    }
                });
            }
        }
    }

    interface DashboardData {
        long id = secrets.getLong("DashboardData.id");
        String name = secrets.get("DashboardData.name");
        OffsetDateTime shareDate = secrets.getDate("DashboardData.shareDate");
        Map<Long, Sight> dashboardMap = secrets.getObjectsCollection("DashboardData.dashboards", ArrayList::new,
                        fieldMap -> Sight.builder()
                                .accessLevel(AccessLevel.valueOf(fieldMap.get("accessLevel").toUpperCase()))
                                .id(Long.parseLong(fieldMap.get("id")))
                                .name(fieldMap.get("name"))
                                .createdAt(DateTimes.parseToOffset(fieldMap.get("createdDate")))
                                .modifiedAt(DateTimes.parseToOffset(fieldMap.get("modifiedDate")))
                                .build())
                .stream()
                .map(sight -> Map.entry(sight.getId(), sight))
                .collect(ExtCollectors.entriesToMap());
        Set<String> shareIdsToDelete = secrets.getValuesCollection("DashboardData.shareIdsToDelete", HashSet::new, v -> v);
        Set<Long> sightIdsToDelete = secrets.getValuesCollection("DashboardData.sightIdsToDelete", HashSet::new, Long::parseLong);

        static void assertShare(Share share) {
            ShareData.assertCommonShare(share, ShareScope.ITEM, shareDate);
            assertThat(share.getCreatedAt()).isEqualTo(shareDate);
            assertThat(share.getModifiedAt()).isAfterOrEqualTo(shareDate);
        }
        static void assertContains(List<? extends SightListItem> dashboardList) {
            log.info("{}", dashboardList);
            assertThat(dashboardList).isNotEmpty()
                    .allSatisfy(val -> {
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
        long id = secrets.getLong("FolderData.id");
        String name = secrets.get("FolderData.name");
        long childFolder1Id = secrets.getLong("FolderData.childFolder.1.id");
        String childFolder1Name = secrets.get("FolderData.childFolder.1.name");
        long childFolder2Id = secrets.getLong("FolderData.childFolder.2.id");
        String childFolder2Name = secrets.get("FolderData.childFolder.2.name");

        static void assertEquals(Folder folder) {
            assertThat(folder).isNotNull();
            assertThat(folder.getId()).isEqualTo(id);
            assertThat(folder.getName()).isEqualTo(name);
//FIXME            assertThat(folder.getAccessLevel()).isSameAs(AccessLevel.OWNER);
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
        Set<String> idSet = secrets.getValuesCollection("ImageData.id", HashSet::new, Function.identity());
        String imagePath = secrets.get("ImageData.image.path");
        String imageMimeType = secrets.get("ImageData.image.mime");
        File imageFile = new File(imagePath);
        long imageFileSize = imageFile.length();

//        AtomicReference<byte[]> imageBytes = new AtomicReference<>();
//
//        static byte[] getImageBytes() {
//            byte[] bytes = imageBytes.get();
//            if (bytes == null) {
//                bytes = imageBytes.updateAndGet(ImageData::readImageBytes);
//            }
//            return bytes;
//        }

        static void assertImageUrl(ImageUrl url) {
            assertThat(url).isNotNull();
            assertThat(url.getError()).isNull();
            assertThat(url.getHeight()).isNull();
            assertThat(url.getWidth()).isNull();
            assertThat(url.getUrl()).isNotBlank();
            assertThat(url.getImageId()).matches(idSet::contains);
        }

        private static byte[] readImageBytes(byte[] bytes) {
            // bytes is non-null if a value has already been read
            if (bytes != null) {
                return bytes;
            }
            try {
                return Files.readAllBytes(Paths.get(imagePath));
            } catch (IOException iox) {
                Assertions.fail(iox);
                return null;    // we will never make it this far
            }
        }
    }

    interface ReportData {
        long id = secrets.getLong("ReportData.id");
        String name = secrets.get("ReportData.name");
        Set<Long> virtualColumnIds = secrets.getValuesCollection("ReportData.virtualColumnId", HashSet::new, Long::parseLong);
        OffsetDateTime shareDate = secrets.getDate("ReportData.shareDate");
        OffsetDateTime createdDate = secrets.getDate("ReportData.createdDate");
        OffsetDateTime modifiedDate = secrets.getDate("ReportData.modifiedDate");
        OffsetDateTime rowCreateDate = secrets.getDate("ReportData.rowCreateDate");

        static void assertContains(List<? extends GridListing> reports) {
            assertThat(reports).isNotEmpty().anySatisfy(ReportData::assertEquals);
        }

        static void assertEquals(GridListing report) {
            assertThat(report).isNotNull();
            assertThat(report.getId()).isEqualTo(id);
            assertThat(report.getName()).isEqualTo(name);
//FIXME            assertThat(report.getAccessLevel()).isSameAs(AccessLevel.OWNER);
        }

        static void assertDeepEquals(Report report) {
            assertThat(report).isNotNull();
            assertThat(report.getId()).isEqualTo(id);
            assertThat(report.getName()).isEqualTo(name);
            assertThat(report.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            assertThat(report.getScope()).isNull();
            assertThat(report.getSourceSheets()).isNullOrEmpty();
            assertThat(report.getIsSummaryReport()).isFalse();
            assertThat(report.getSource()).isNull();
            assertThat(report.getFromId()).isNull();
            assertThat(report.getAttachments()).isNullOrEmpty();
            assertThat(report.getCellImageUploadEnabled()).isTrue();
            assertThat(report.getCreatedAt()).isEqualTo(createdDate);
            assertThat(report.getCrossSheetReferences()).isNullOrEmpty();
            assertThat(report.getDependenciesEnabled()).isNull();
            assertThat(report.getDiscussions()).isNullOrEmpty();
            assertThat(report.getEffectiveAttachmentOptions()).allMatch(SheetData.effectiveAttachmentOptions::contains);
            assertThat(report.getFavorite()).isNull();
//FIXME            assertThat(report.getFilters()).isEmpty();
//FIXME            assertThat(report.getGanttConfig()).isNull();
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
            assertThat(reportColumn.getContactOptions()).isNullOrEmpty();
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
            assertThat(reportColumn.getVersion()).isSameAs(ColumnVersion.NUMBER_0);
            assertThat(reportColumn.getWidth()).isEqualTo(150);
            assertThat(reportColumn.getVirtualId()).matches(virtualColumnIds::contains);
            assertThat(reportColumn.getSheetNameColumn()).matches(ReportData::isNullOrTrue);
        }

        static boolean isNullOrTrue(Boolean val) {
            return val == null || val;
        }

        static void assertReportRow(Row reportRow) {
            assertThat(reportRow).isNotNull();
            assertThat(reportRow.getColumns()).isNullOrEmpty();
            assertThat(reportRow.getConditionalFormat()).isNull();
            assertThat(reportRow.getCreatedAt()).isEqualTo(rowCreateDate);
            assertThat(reportRow.getCreatedBy()).isNull();
            assertThat(reportRow.getDiscussions()).isNullOrEmpty();
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
            assertThat(reportRow.getAttachments()).isNullOrEmpty();
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
                        // clumsy but not sure how else to handle cell-values that can have many value types
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

        static void assertReportWorkspace(WorkspaceListing reportWorkspace) {
            assertThat(reportWorkspace).isNotNull();
            assertThat(reportWorkspace.getId()).isEqualTo(WorkspaceData.id);
            assertThat(reportWorkspace.getName()).isEqualTo(WorkspaceData.name);
            assertThat(reportWorkspace.getAccessLevel()).isNull();
            assertThat(reportWorkspace.getPermalink()).isNull();
//FIXME            assertThat(reportWorkspace.getFolders()).isEmpty();
//FIXME            assertThat(reportWorkspace.getReports()).isEmpty();
//FIXME            assertThat(reportWorkspace.getSheets()).isEmpty();
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

        static void assertBrief(MiniReport brief) {
            assertThat(brief).isNotNull();
            assertThat(brief.getId()).isEqualTo(id);
            assertThat(brief.getName()).isEqualTo(name);
            assertThat(brief.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            assertThat(brief.getPermalink()).isNotBlank();
            assertThat(brief.getIsSummaryReport()).isFalse();
        }
    }

    interface RowData {
        long id = secrets.getLong("RowData.id");
        long attachmentId = secrets.getLong("RowData.attachmentId");
    }

    interface ShareData {
        String shareId = secrets.get("ShareData.shareId");
        String shareToEmail = secrets.get("ShareData.shareToEmail");

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
        long id = secrets.getLong("SheetData.id");
        String name = secrets.get("SheetData.name");
        long sourceId = secrets.getLong("SheetData.sourceId");

        OffsetDateTime createdDate = secrets.getDate("SheetData.createdDate");
        OffsetDateTime shareDate = secrets.getDate("SheetData.shareDate");
        Set<AttachmentTypeWithSmartsheet> effectiveAttachmentOptions = Set.of(
                AttachmentTypeWithSmartsheet.BOX_COM, AttachmentTypeWithSmartsheet.DROPBOX, AttachmentTypeWithSmartsheet.EGNYTE,
                AttachmentTypeWithSmartsheet.EVERNOTE, AttachmentTypeWithSmartsheet.FILE,
                AttachmentTypeWithSmartsheet.GOOGLE_DRIVE,
                AttachmentTypeWithSmartsheet.LINK, AttachmentTypeWithSmartsheet.ONEDRIVE);


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

        static void assertListingEquals(MiniSheet listing) {
            assertThat(listing).isNotNull();
            assertThat(listing.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            assertThat(listing.getCreatedAt()).isEqualTo(createdDate);
            assertThat(listing.getId()).isEqualTo(id);
            assertThat(listing.getName()).isEqualTo(name);
            assertThat(listing.getPermalink()).isNotBlank();
            assertThat(listing.getModifiedAt()).isAfterOrEqualTo(createdDate);
            // as of 2025-05-17 the sheet src is now sometimes null :-?
            if (listing.getSource() != null) {
                assertThat(listing.getSource()).satisfies(src -> {
                    assertThat(src.getId()).isEqualTo(sourceId);
                    assertThat(src.getType()).isSameAs(SourceType.SHEET);
                });
            }
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
        long id = secrets.getLong("TemplateData.id");
        String name = secrets.get("TemplateData.name");

        Template template = Template.builder()
                .id(id)
                .accessLevel(AccessLevel.OWNER)
                .categories(List.of())
                .name(name)
                .tags(List.of())
                .build();
    }

    interface UserData {
        long id = secrets.getLong("UserData.id");
        String email = secrets.get("UserData.email");
        String firstName = secrets.get("UserData.firstName");
        String lastName = secrets.get("UserData.lastName");
        String name = firstName + " " + lastName;
        String locale = "en_US";
        String timezone = "US/Pacific";
        String accountName = secrets.get("UserData.accountName");
        long accountId = secrets.getLong("UserData.accountId");
        boolean isAdmin = true;
        boolean isLicensedSheetCreator = true;
        boolean isGroupAdmin = true;
        MiniUser nameAndEmail = MiniUser.builder().email(email).name(name).build();

        interface AlternateEmailData {
            long id = secrets.getLong("UserData.AlternateEmailData.id");
            static void assertMatch(List<? extends AlternateEmail> emailList) {
                assertThat(emailList).isNotEmpty().anySatisfy(email -> assertThat(email.getId()).isEqualTo(id));
            }
        }

        static void assertEquals(GetCurrentUser200Response user) {
            assertThat(user).isNotNull();
            Account account = assertThat(user.getAccount()).as("account").isNotNull().actual();
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
        long id = secrets.getLong("WebhookData.id");
    }

    interface WorkflowData {
        String id = secrets.get("WorkflowData.id");
    }

    interface WorkspaceData {
        long id = secrets.getLong("WorkspaceData.id");
        String name = secrets.get("WorkspaceData.name");
        long folderId = secrets.getLong("WorkspaceData.folderId");
        String folderName = secrets.get("WorkspaceData.folderName");
        AccessLevel level = AccessLevel.OWNER;
        OffsetDateTime shareDate = secrets.getDate("WorkspaceData.shareDate");

        static void assertEquals(Workspace workspace) {
            assertThat(workspace).isNotNull();
            assertThat(workspace.getId()).isEqualTo(id);
            assertThat(workspace.getName()).isEqualTo(name);
            assertThat(workspace.getAccessLevel()).isSameAs(level);
            assertThat(workspace.getPermalink()).isNotBlank();
        }

        static void assertEquals(WorkspaceListing workspace) {
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
        assertThat(result.getResultCode()).isSameAs(ResultCode.NUMBER_0);
        assertThat(result.getMessage()).isSameAs(ResultMessage.SUCCESS);
        // what's result.version about?
    }

    static void successfulResult(GenericResult result) {
        assertThat(result).isNotNull();
        assertThat(result.getResultCode()).isSameAs(ResultCode.NUMBER_0);
        assertThat(result.getMessage()).isSameAs(ResultMessage.SUCCESS);
    }

    /**
     * most general-purpose version to handle all types similar in structure to Result (because allOf generates aggregation not
     * extension)
     */
    static void successfulResult(Object result) {
        assertThat(result).as("result not null").isNotNull();
        if (Reflection.hasMethod(result.getClass(), "getFailedItems")) {
            assertThat(Reflection.invoke(result, "getFailedItems", List.class)).as("no failed items").isNullOrEmpty();
        }
        assertThat(Reflection.invoke(result, "getResultCode", ResultCode.class))
                .as("success result code").isSameAs(ResultCode.NUMBER_0);
        assertThat(Reflection.invoke(result, "getMessage", ResultMessage.class))
                .as("success message").isSameAs(ResultMessage.SUCCESS);
    }

    static MiniUser mapToNameAndEmail(Map<String, String> map) {
        return MiniUser.builder()
                .email(map.get("email"))
                .name(map.get("name"))
//                .systemUserType(map.get("systemUserType"))
                .build();
    }
}
