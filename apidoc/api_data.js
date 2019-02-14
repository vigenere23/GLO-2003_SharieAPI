define({ "api": [
  {
    "type": "post",
    "url": "/listings",
    "title": "Add a new listing",
    "name": "addListing",
    "group": "Listings",
    "description": "<p>No body is returned, only the header <code>Location</code>.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>Listing's title.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "description",
            "description": "<p>Listing's description.</p>"
          },
          {
            "group": "Parameter",
            "type": "Object",
            "optional": false,
            "field": "owner",
            "description": "<p>Listing's owner</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "owner.name",
            "description": "<p>Owner's name.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "owner.phoneNumber",
            "description": "<p>Owner's phone number.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "owner.email",
            "description": "<p>Owner's email.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Params example:",
          "content": "{\n  title: \"Fresh new Honda Civic ONLY 3000$\",\n  description: \"Just buy it, i'll be worth it!\",\n  owner: {\n    name: \"Frank Desjose\",\n    phoneNumber: \"123 456-7890\",\n    email: \"frankito.deslato@myman.com\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Created 201": [
          {
            "group": "Created 201",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>ID of the newly created listing</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success Example:",
          "content": "HTTP 201 CREATED\nLocation: /listings/:id",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/glo2003/controllers/ListingsController.java",
    "groupTitle": "Listings"
  },
  {
    "type": "get",
    "url": "/listings/:id",
    "title": "Get single listing",
    "name": "getListing",
    "group": "Listings",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Listing's ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/glo2003/controllers/ListingsController.java",
    "groupTitle": "Listings"
  }
] });
