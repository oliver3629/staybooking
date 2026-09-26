# Staybooking Backend

Spring Boot backend for user authentication, stay listings, listing search, and bookings. It also supports image uploads and address geocoding.

## Technology

- Java 21, Spring Boot, Spring Web, Spring Security, and Spring Data JPA
- PostgreSQL with PostGIS and Hibernate Spatial
- JWT authentication
- Google Cloud Storage for listing images and Google Maps Geocoding for addresses
- Gradle for builds; Google Cloud Run for deployment

## Project structure

The application code is under `src/main/java/com/laioffer/staybooking`:

| Package | Purpose |
| --- | --- |
| `authentication` | Registration and login |
| `security` | JWT creation, validation, and request authentication |
| `listing` | Listing creation, retrieval, deletion, and search |
| `booking` | Booking creation, retrieval, and deletion |
| `model` | Entities and API request/response models |
| `repository` | Database access for users, listings, and bookings |
| `location` | Address geocoding |
| `storage` | Listing image uploads |

Configuration and the PostGIS initialization script are in `src/main/resources`. The project also includes `compose.yaml` for a local PostGIS database and `project.toml` for the Cloud Run build runtime.

## Run locally

Install Java 21 and Docker with Compose, and make sure Docker is running. Place your Google Cloud service-account `credentials.json` in `src/main/resources`; the application needs it to initialize Cloud Storage. You also need a Cloud Storage bucket and a Google Maps Geocoding API key.

From the project root, start the local PostGIS database:

```sh
docker-compose up -d
```

To check the database connection in IntelliJ IDEA, add a PostgreSQL data source and click **Test Connection** using:

| Setting | Value |
| --- | --- |
| Host | `localhost` |
| Port | `5432` |
| User | `postgres` |
| Password | `secret` |
| Database | `postgres` |

Get the bucket name and Geocoding API key from Google Cloud (see [DEPLOYMENT.md](DEPLOYMENT.md)). Generate a 256-bit JWT secret with this [JWT Secret Generator](https://www.jwtsecretgenerator.com/tools/jwt-secret-generator) and select **base64** (not base64url). Add the values to the project root `.env` file:

```dotenv
GCS_BUCKET=YOUR_BUCKET_NAME
GEOCODING_KEY=YOUR_GEOCODING_KEY
JWT_SECRET_KEY=YOUR_BASE64_ENCODED_JWT_SECRET
```

Load `.env` in the IntelliJ IDEA run configuration, then run `StaybookingApplication`.

## Deployment

See [DEPLOYMENT.md](DEPLOYMENT.md) for the Google Cloud setup and deployment steps.
