package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.model.AlternateEmail;
import com.ronreynolds.smartsheet.model.Attachment;
import com.ronreynolds.smartsheet.model.AttachmentTypeTrello;
import com.ronreynolds.smartsheet.model.Comment;
import com.ronreynolds.smartsheet.model.GetCurrentUser200Response;
import com.ronreynolds.smartsheet.model.User;
import com.ronreynolds.smartsheet.model.UserProfile;
import lombok.NonNull;
import org.assertj.core.data.Offset;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * various bits of test constants
 */
public class TestData {
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
        long id = 0L;
        String name = "Test Folder";
    }

    interface ReportData {
        long id = 0L;
        String name = "Test Report";
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

    interface UserData {
        long id = 829865629902724L;
        String firstName = "Ron";
        String lastName = "Reynolds";
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
    }
}
