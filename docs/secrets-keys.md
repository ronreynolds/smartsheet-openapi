# Secrets.props Keys
in order to assert on sensitive info like emails and such `TestData` gets much of its integration-test data from 
`src/test/resources/secrets.props`.  this file must contain values for the following keys in order to run the full integration
test suite.

## Keys
note, some keys contain digits to indicate groups of keys used to create groups of values.
some of these digit ranges are fixed and some are open-ended; some are 1-based and some 0-based.
likely these will be made more consistent tho certain ranges can't be open-ended if they're used to populate an enum such as 
`ColumnData.ColumnBriefData`.

property names were chosen to match the class and field that uses them (seemed the most intuitive approach).
```
AttachmentData.CommentAttachment.name  
AttachmentData.RowAttachment.name      
AttachmentData.SheetAttachment.name    
AttachmentData.SheetAttachment.id      

ColumnData.ColumnBriefData.0.id        
ColumnData.ColumnBriefData.0.title     
ColumnData.ColumnBriefData.0.type      
ColumnData.ColumnBriefData.1.id        
ColumnData.ColumnBriefData.1.title     
ColumnData.ColumnBriefData.1.type      
ColumnData.ColumnBriefData.2.id        
ColumnData.ColumnBriefData.2.title     
ColumnData.ColumnBriefData.2.type      
ColumnData.ColumnBriefData.3.id        
ColumnData.ColumnBriefData.3.title     
ColumnData.ColumnBriefData.3.type      
ColumnData.ColumnBriefData.4.id        
ColumnData.ColumnBriefData.4.title     
ColumnData.ColumnBriefData.4.type      
ColumnData.ColumnBriefData.5.id        
ColumnData.ColumnBriefData.5.title     
ColumnData.ColumnBriefData.5.type      

CommentData.id                         
CommentData.text                       
CommentData.attachmentId               
CommentData.discussionId               

ContactData.contact.0.id               
#ContactData.contact.0.name - commented out to produce 'null' value rather than empty ("") value
ContactData.contact.0.email            
ContactData.contact.1.id               
ContactData.contact.1.name             
ContactData.contact.1.email            
ContactData.contact.2.id               
ContactData.contact.2.name             
ContactData.contact.2.email            
ContactData.contact.3.id               
ContactData.contact.3.name             
ContactData.contact.3.email            

DashboardData.id                       
DashboardData.name                     

FolderData.id                          
FolderData.name                        
FolderData.childFolder.1.id            
FolderData.childFolder.1.name          
FolderData.childFolder.2.id            
FolderData.childFolder.2.name          

# Image privateIDs (gotten via dev-tools)
ImageData.id.1                         
ImageData.id.2                         

ReportData.id                          
ReportData.name                        
ReportData.virtualColumnId.1           
ReportData.virtualColumnId.2           

RowData.id                             
RowData.attachmentId                   

ShareData.shareId                      

SheetData.id                           
SheetData.name                         
SheetData.sourceId                     

TemplateData.id                        
TemplateData.name                      

UserData.id                            
UserData.email                         
UserData.firstName                     
UserData.lastName                      
UserData.accountName                   
UserData.accountId                     
UserData.AlternateEmailData.id         

WebhookData.id                         

WorkflowData.id                        

WorkspaceData.id                       
WorkspaceData.name                     
WorkspaceData.folderId                 
WorkspaceData.folderName               
```