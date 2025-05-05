package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.model.AccessLevel;
import com.ronreynolds.smartsheet.model.AlternateEmail;
import com.ronreynolds.smartsheet.model.Attachment;
import com.ronreynolds.smartsheet.model.AttachmentTypeTrello;
import com.ronreynolds.smartsheet.model.Comment;
import com.ronreynolds.smartsheet.model.Folder;
import com.ronreynolds.smartsheet.model.GetCurrentUser200Response;
import com.ronreynolds.smartsheet.model.GetWorkspaceFolders200ResponseAllOfDataInner;
import com.ronreynolds.smartsheet.model.ListFolders200Response;
import com.ronreynolds.smartsheet.model.Report;
import com.ronreynolds.smartsheet.model.Share;
import com.ronreynolds.smartsheet.model.ShareScope;
import com.ronreynolds.smartsheet.model.ShareType;
import com.ronreynolds.smartsheet.model.Template;
import com.ronreynolds.smartsheet.model.UserProfile;
import com.ronreynolds.smartsheet.model.Workspace;
import com.ronreynolds.util.reflection.Reflection;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

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

            static void assertEquals(@NonNull Attachment attachment) {
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

            static void assertEquals(@NonNull Attachment attachment) {
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

            static void assertEquals(@NonNull Attachment attachment) {
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

        static void assertEquals(@NonNull Comment comment) {
            assertThat(comment.getId()).as("id").isEqualTo(id);
            assertThat(comment.getDiscussionId()).as("discussion id").isEqualTo(discussionId);
            assertThat(comment.getText()).as("text").isEqualTo(text);
            assertThat(comment.getCreatedAt()).as("created-at").isEqualTo(created);

            // assert on the attachment too
            assertThat(comment.getAttachments()).hasSizeGreaterThan(0);
            AttachmentData.CommentAttachment.assertEquals(comment.getAttachments().get(0));
        }
    }

    interface FolderData {
        long id = 1104664725874564L;
        String name = "Test Folder";

        static void assertEquals(@NonNull Folder folder) {
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
        static void assertChildFolders(@NonNull List<? extends Folder> childFolders) {
            assertThat(childFolders)
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

    interface ReportData {
        long id = 5383337628618628L;
        String name = "Test Report";
        static void assertContains(@NonNull List<? extends Report> reports) {
            assertThat(reports).anySatisfy(ReportData::assertEquals);
        }
        static void assertEquals(@NonNull Report report) {
            assertThat(report.getId()).isEqualTo(id);
            assertThat(report.getName()).isEqualTo(name);
            assertThat(report.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            log.info("report:{}", report);
        }
    }

    interface RowData {
        long id = 6139161318133636L;
        long attachmentId = 7117529120280452L;
    }

    interface SheetData {
        long id = 6971132763656068L;
        String name = "Test Sheet 1";
        OffsetDateTime createdDate = OffsetDateTime.of(2025, 4, 2, 14, 27, 31, 0, ZoneOffset.UTC);
        List<String> effectiveAttachmentOptions =
                List.of("FILE", "BOX_COM", "LINK", "EVERNOTE", "ONEDRIVE", "GOOGLE_DRIVE", "DROPBOX", "EGNYTE");
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
            static void assertMatch(@NonNull List<? extends AlternateEmail> emailList) {
                assertThat(emailList).anyMatch(email -> assertThat(email.getId()).isNotNull().actual() == id);
            }
        }

        static void assertEquals(@NonNull GetCurrentUser200Response user) {
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

        static void assertEquals(@NonNull UserProfile user) {
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
    }

    interface WorkflowData {
        String id = "Test Workflow";
    }

    interface WorkspaceData {
        long id = 4931918228678532L;
        String name = "Test Workspace";
        AccessLevel level = AccessLevel.OWNER;

        static void assertEquals(@NonNull Workspace workspace) {
            assertThat(workspace.getId()).isEqualTo(id);
            assertThat(workspace.getName()).isEqualTo(name);
            assertThat(workspace.getAccessLevel()).isSameAs(level);
            assertThat(workspace.getPermalink()).isNotBlank();
        }

        static void assertTestShare(@NonNull Share share) {
            assertThat(share.getId()).isEqualTo("AAAC8sImFOeE");
            assertThat(share.getUserId()).isEqualTo(UserData.id);
            assertThat(share.getName()).isEqualTo(UserData.name);
            assertThat(share.getAccessLevel()).isSameAs(AccessLevel.OWNER);
            assertThat(share.getScope()).isSameAs(ShareScope.WORKSPACE);
            assertThat(share.getType()).isSameAs(ShareType.USER);
        }

        // FIXME - make non-inner type
        static void assertFolderInWorkspace(@NonNull GetWorkspaceFolders200ResponseAllOfDataInner folder) {
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
}
