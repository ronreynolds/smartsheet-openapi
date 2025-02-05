# current Errors (all compile-time)
* C:\dev\git\smartsheet-openapi\build\generated\api\src\main\java\com\ronreynolds\smartsheet\api\AttachmentsApi.java:22: error: cannot find symbol
import com.ronreynolds.smartsheet.model.AttachmentsAttachToSheetRequest;
has multiple content-types for the request body; not sure how the client is supposed to resolve that
```json
        "operationId": "attachments-attachToSheet",
...
        "requestBody": {
          "content": {
            "application/octet-stream": {
              "schema": {
                "type": "string",
                "format": "binary"
              }
            },
            "multipart/form-data": {
              "schema": {
                "type": "object",
                "properties": {
                  "name": {
                    "type": "string"
                  },
                  "filename": {
                    "type": "string",
                    "format": "binary"
                  }
                }
              }
            },
            "application/json": {
              "schema": {
                "type": "object",
                "properties": {
                  "attachmentSubType": {
                    "description": "Attachment sub type. Note--Folder type is for EGNYTE values and the rest are GOOGLE_DRIVE values.",
                    "type": "string",
                    "enum": [
                      "DOCUMENT",
                      "DRAWING",
                      "FOLDER",
                      "PDF",
                      "PRESENTATION",
                      "SPREADSHEET"
                    ]
                  },
                  "attachmentType": {
                    "description": "Attachment type. Note--Dropbox, Egnyte, and Evernote are not supported for Smartsheet.gov accounts.",
                    "allOf": [
                      {
                        "$ref": "#/components/schemas/AttachmentType_trello"
                      }
                    ]
                  },
                  "description": {
                    "type": "string"
                  },
                  "name": {
                    "type": "string"
                  },
                  "url": {
                    "type": "string"
                  }
                }
              }
            }
          }
        },
```
* C:\dev\git\smartsheet-openapi\build\generated\api\src\main\java\com\ronreynolds\smartsheet\api\CommentsApi.java:25: error: cannot find symbol
import com.ronreynolds.smartsheet.model.CommentsCreateRequest;
multiple content-types in request body
```json
        "operationId": "comments-create",
...
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/CommentText"
              }
            },
            "multipart/form-data": {
              "schema": {
                "type": "object",
                "properties": {
                  "comment": {
                    "description": "Comment.",
                    "type": "object",
                    "properties": {
                      "text": {
                        "description": "Comment text.",
                        "type": "string"
                      }
                    }
                  },
                  "file": {
                    "description": "File to attach to the new comment. Only used with an attachment.",
                    "type": "string",
                    "format": "binary"
                  }
                }
              },
              "encoding": {
                "comment": {
                  "contentType": "application/json"
                },
                "file": {
                  "contentType": "application/octet-stream"
                }
              }
            }
          }
        },
```
* C:\dev\git\smartsheet-openapi\build\generated\api\src\main\java\com\ronreynolds\smartsheet\api\DiscussionsApi.java:22: error: cannot find symbol
import com.ronreynolds.smartsheet.model.DiscussionAndFile;
possible same issue:
```json
        "operationId": "discussions-create",
...
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/CommentLite"
              }
            },
            "multipart/form-data": {
              "schema": {
                "$ref": "#/components/schemas/DiscussionAndFile"
              },
              "encoding": {
                "discussion": {
                  "contentType": "application/json"
                },
                "file": {
                  "contentType": "application/octet-stream"
                }
              }
            }
          }
        },

```
* C:\dev\git\smartsheet-openapi\build\generated\api\src\main\java\com\ronreynolds\smartsheet\api\ProofsApi.java:21: error: cannot find symbol
import com.ronreynolds.smartsheet.model.AttachmentsAttachToSheetRequest;
```json

```
* C:\dev\git\smartsheet-openapi\build\generated\api\src\main\java\com\ronreynolds\smartsheet\api\ProofsApi.java:23: error: cannot find symbol
import com.ronreynolds.smartsheet.model.DiscussionAndFile;
```json

```
* C:\dev\git\smartsheet-openapi\build\generated\api\src\main\java\com\ronreynolds\smartsheet\api\UsersApi.java:21: error: cannot find symbol
import com.ronreynolds.smartsheet.model.AttachmentsAttachToSheetRequest;
```json

```