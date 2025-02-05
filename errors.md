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

## TBD Warnings
```
Failed to get the schema name: null
allOf schema `null` containing multiple types (not model) is not supported at the moment.
allOf schema `null` containing multiple types (not model) is not supported at the moment.
allOf schema `null` containing multiple types (not model) is not supported at the moment.
allOf schema `null` containing multiple types (not model) is not supported at the moment.
allOf schema `null` containing multiple types (not model) is not supported at the moment.
'oneOf' is intended to include only the additional optional OAS extension discriminator object. For more details, see https://json-schema.org/draft/2019-09/json-schema-core.html#rfc.section.9.2.1.3 and the OAS section on 'Composition and Inheritance'.
'oneOf' is intended to include only the additional optional OAS extension discriminator object. For more details, see https://json-schema.org/draft/2019-09/json-schema-core.html#rfc.section.9.2.1.3 and the OAS section on 'Composition and Inheritance'.
'oneOf' is intended to include only the additional optional OAS extension discriminator object. For more details, see https://json-schema.org/draft/2019-09/json-schema-core.html#rfc.section.9.2.1.3 and the OAS section on 'Composition and Inheritance'.
Failed to obtain schema from cell
Failed to obtain schema from cell
Failed to obtain schema from cell
```

## paths known to produce compile-errors
```json
    "/sheets/{sheetId}/proofs/{proofId}/discussions": {
      "parameters": [
        {
          "$ref": "#/components/parameters/sheetIdInPath"
        },
        {
          "$ref": "#/components/parameters/proofIdInPath"
        },
        {
          "$ref": "#/components/parameters/authorizationHeader"
        }
      ],
      "get": {
        "summary": "List Proof Discussions",
        "description": "Gets a list of all discussions that are in the proof.\n",
        "operationId": "proofs-listDiscussions",
        "tags": [
          "proofs"
        ],
        "security": [
          {
            "APIToken": []
          },
          {
            "OAuth2": [
              "READ_SHEETS"
            ]
          }
        ],
        "parameters": [
          {
            "$ref": "#/components/parameters/include_discussion"
          },
          {
            "$ref": "#/components/parameters/pageNumber"
          },
          {
            "$ref": "#/components/parameters/pageSize"
          },
          {
            "$ref": "#/components/parameters/includeAllBoolean"
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "allOf": [
                    {
                      "$ref": "#/components/schemas/PagedResult"
                    },
                    {
                      "type": "object",
                      "properties": {
                        "data": {
                          "description": "list of proof discussions",
                          "allOf": [
                            {
                              "$ref": "#/components/schemas/DiscussionArray"
                            }
                          ]
                        }
                      }
                    }
                  ]
                }
              }
            }
          }
        }
      },
      "post": {
        "summary": "Create Proof Discussion",
        "description": "Creates a discussion on a proof.\n",
        "operationId": "proofs-createDiscussion",
        "tags": [
          "proofs"
        ],
        "security": [
          {
            "APIToken": []
          },
          {
            "OAuth2": [
              "WRITE_SHEETS"
            ]
          }
        ],
        "parameters": [
          {
            "$ref": "#/components/parameters/contentTypeHeader_JSON"
          }
        ],
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
        "responses": {
          "200": {
            "description": "SUCCESS",
            "content": {
              "application/json": {
                "schema": {
                  "allOf": [
                    {
                      "$ref": "#/components/schemas/ResultPrefix"
                    },
                    {
                      "type": "object",
                      "properties": {
                        "result": {
                          "$ref": "#/components/schemas/Discussion"
                        }
                      }
                    }
                  ],
                  "properties": {
                    "version": {
                      "description": "New version of the sheet. Applicable only for operations which update sheet data.",
                      "type": "integer",
                      "nullable": true
                    }
                  }
                }
              }
            }
          }
        }
      }
    },
```
and
```json
    "/sheets/{sheetId}/proofs/{proofId}/versions": {
      "parameters": [
        {
          "$ref": "#/components/parameters/sheetIdInPath"
        },
        {
          "$ref": "#/components/parameters/proofIdInPath"
        },
        {
          "$ref": "#/components/parameters/authorizationHeader"
        }
      ],
      "get": {
        "summary": "List Proof Versions",
        "description": "Gets a list of all versions of the given proofId in order from newest to oldest.\n",
        "operationId": "proofs-getVersions",
        "tags": [
          "proofs"
        ],
        "security": [
          {
            "APIToken": []
          },
          {
            "OAuth2": [
              "READ_SHEETS"
            ]
          }
        ],
        "parameters": [
          {
            "$ref": "#/components/parameters/pageNumber"
          },
          {
            "$ref": "#/components/parameters/pageSize"
          },
          {
            "$ref": "#/components/parameters/includeAllBoolean"
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "allOf": [
                    {
                      "$ref": "#/components/schemas/PagedResult"
                    },
                    {
                      "type": "object",
                      "properties": {
                        "data": {
                          "description": "list of proof versions",
                          "type": "array",
                          "items": {
                            "$ref": "#/components/schemas/Proof"
                          }
                        }
                      }
                    }
                  ]
                }
              }
            }
          }
        }
      },
      "delete": {
        "summary": "Delete Proof Version",
        "description": "Deletes a proof version. Proof Id must be a current version proof Id.\n",
        "operationId": "proofs-deleteVersion",
        "tags": [
          "proofs"
        ],
        "security": [
          {
            "APIToken": []
          },
          {
            "OAuth2": [
              "WRITE_SHEETS"
            ]
          }
        ],
        "responses": {
          "200": {
            "description": "SUCCESS",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/ResultPrefix"
                }
              }
            }
          }
        }
      },
      "post": {
        "summary": "Create Proof Version",
        "description": "Creates a proof version. Proof Id must be for the original proof.\n",
        "operationId": "proofs-createVersion",
        "tags": [
          "proofs"
        ],
        "security": [
          {
            "APIToken": []
          },
          {
            "OAuth2": [
              "WRITE_SHEETS"
            ]
          }
        ],
        "parameters": [
          {
            "$ref": "#/components/parameters/contentTypeHeader_JSON"
          }
        ],
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
                "$ref": "#/components/schemas/URLAttachmentRequest"
              }
            }
          }
        },
        "responses": {
          "200": {
            "description": "SUCCESS",
            "content": {
              "application/json": {
                "schema": {
                  "allOf": [
                    {
                      "$ref": "#/components/schemas/ResultPrefix"
                    },
                    {
                      "type": "object",
                      "properties": {
                        "result": {
                          "$ref": "#/components/schemas/Proof"
                        }
                      }
                    }
                  ],
                  "properties": {
                    "version": {
                      "description": "New version of the sheet. Applicable only for operations which update sheet data.",
                      "type": "integer",
                      "nullable": true
                    }
                  }
                }
              }
            }
          }
        }
      }
    },
```