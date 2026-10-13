# Architecture

VBook is split into an Android client and a PHP/MySQL backend.

## Android client

The Gradle project in `mobile` contains activities for authentication, customer reservations, vendor hotel management, and inventory browsing. The `dataLayer` package owns HTTP communication and translates API responses into the application's domain objects.

## PHP API

The endpoints in `api` expose the original database operations for customers, vendors, hotels, rooms, and reservations. They connect to the `hotel_reservation_application` MySQL database.

```text
Android activities → data layer → PHP endpoints → MySQL
```

The implementation reflects its original development environment. Before deployment, database credentials and the API base URL should be supplied through environment-specific configuration, and the API should add authentication, authorization, prepared statements, and HTTPS.
