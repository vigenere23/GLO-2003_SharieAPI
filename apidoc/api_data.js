define({ "api": [
  {
    "type": "post",
    "url": "/listings",
    "title": "Add a new listing",
    "name": "addListing",
    "group": "Listings",
    "description": "<p>No body is returned, only the header <code>Location</code>.</p>",
    "parameter": {
      "examples": [
        {
          "title": "Request",
          "content": "POST /listings\n{\n  \"title\": \"\"::string,\n  \"description\": \"\"::string,\n  \"owner\": {\n    \"name\": \"\"::string,\n    \"phoneNumber\": \"\"::string,\n    \"email\": \"\"::string,\n  }\n}",
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
          "title": "Response",
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
    "type": "post",
    "url": "/listings/:id/book",
    "title": "Book a listing",
    "name": "bookListing",
    "group": "Listings",
    "description": "<p>No body is returned.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Listing's ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "bookings",
            "description": "<p>List of date in format <a href=\"https://en.wikipedia.org/wiki/ISO_8601\">ISO-8601</a>. The time is not important, you should only consider the date.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request",
          "content": "POST /listings/:id/book\n{\n  \"bookings\": [ \"2019-02-12T00:00:00Z\"::string, ... ]\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "204 HTTP No Content",
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
    "url": "/listings",
    "title": "Get all listings",
    "name": "getAllListings",
    "group": "Listings",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String[]",
            "optional": false,
            "field": "availabilities",
            "description": "<p>List of date in format <a href=\"https://en.wikipedia.org/wiki/ISO_8601\">ISO-8601</a>. By default, it should contain 7 dates, starting from today. The time is not important as it is ignored by the frontend.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Response",
          "content": "###\n\n```\n200 HTTP Ok\n{\n  \"title\": \"\"::string,\n  \"description\": \"\"::string,\n  \"availabilities\": [ \"2019-02-12T00:00:00Z\"::string, ... ]\n  \"owner\": {\n    \"name\": \"\"::string,\n    \"phoneNumber\": \"\"::string,\n    \"email\": \"\"::string,\n  }\n}\n```",
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
    "title": "Get a single listing",
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
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String[]",
            "optional": false,
            "field": "availabilities",
            "description": "<p>List of date in format <a href=\"https://en.wikipedia.org/wiki/ISO_8601\">ISO-8601</a>. By default, it should contain 7 dates, starting from today. The time is not important as it is ignored by the frontend.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Response",
          "content": "200 HTTP Ok\n{\n  \"listings\": [{\n    \"title\": \"\"::string,\n    \"description\": \"\"::string,\n    \"availabilities\": [ \"2019-02-12T00:00:00Z\"::string, ... ]\n    \"owner\": {\n      \"name\": \"\"::string,\n      \"phoneNumber\": \"\"::string,\n      \"email\": \"\"::string,\n    }\n  },\n  ...\n  ]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/glo2003/controllers/ListingsController.java",
    "groupTitle": "Listings"
  }
] });
