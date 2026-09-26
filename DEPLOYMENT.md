# Staybooking Deployment

## 1. Sign up for Google Cloud

Open the [Google Cloud Console](https://console.cloud.google.com/) and sign in with your Gmail account. Click **START FREE** to sign up, then provide the requested billing and credit card information.

## 2. Create a project

In the console, select **New Project**, enter a project name, leave **Location** as **No organization**, and click **Create**. Record the **Project ID** shown on the project dashboard; you will need it later.

## 3. Open Cloud Shell

Click the **Cloud Shell** icon in the upper-right corner of the console. Run the following commands in Cloud Shell.

## 4. Create a Cloud Storage bucket

Replace `staybooking-bucket` in the command with your own bucket name. The name must be globally unique.

```bash
gcloud storage buckets create gs://staybooking-bucket --location=us-west1 --default-storage-class=STANDARD --no-uniform-bucket-level-access
```

Click **AUTHORIZE** if prompted. If the bucket name is unavailable (HTTP 409), choose another name and run the command again. Verify the bucket in [Cloud Storage](https://console.cloud.google.com/storage).

## 5. Create a service account and credentials

Create a service account:

```bash
gcloud iam service-accounts create my-service-account
```

Replace both occurrences of `YOUR_PROJECT_ID` below with the Project ID from step 2, then bind the service account to the project:

```bash
gcloud projects add-iam-policy-binding YOUR_PROJECT_ID \
  --member=serviceAccount:my-service-account@YOUR_PROJECT_ID.iam.gserviceaccount.com \
  --role=roles/editor
```

Replace both occurrences of `YOUR_PROJECT_ID` in the next command to generate `credentials.json`:

```bash
gcloud iam service-accounts keys create credentials.json \
  --iam-account=my-service-account@YOUR_PROJECT_ID.iam.gserviceaccount.com \
  --project=YOUR_PROJECT_ID
```

Use the **Download** option in Cloud Shell to download `credentials.json` to your computer, then move it into the Staybooking project's `src/main/resources` directory.

## 6. Configure and verify image uploads

Set the `GCS_BUCKET` environment variable to the bucket name from step 4. The project's `application.yaml` already uses this variable for `staybooking.gcs.bucket`; the Storage bean in `AppConfig` and the upload logic in `ImageStorageService` are also implemented.

Create a listing to test image uploads. After the request succeeds, open the bucket in [Cloud Storage](https://console.cloud.google.com/storage) and verify that the image file was uploaded.

## 7. Enable Google Maps Geocoding

Enable the Geocoding API in Cloud Shell:

```bash
gcloud services enable geocoding-backend.googleapis.com
```

Create an API key:

```bash
gcloud alpha services api-keys create --display-name google_maps_key
```

Copy the `keyString` from the response and set it as `GEOCODING_KEY`. The project's `application.yaml` already uses this variable; the `GeoApiContext` bean, geocoding exceptions, and `GeocodingService` are also implemented.

Create a listing with an address, then retrieve the listing and verify that its coordinates are correct.

## 8. Create a Cloud SQL PostgreSQL instance

In Cloud Shell, run the following command after replacing `YOUR_DB_PASSWORD` with your password:

```bash
gcloud sql instances create staybooking \
  --database-version=POSTGRES_15 \
  --tier=db-f1-micro \
  --storage-type=SSD \
  --storage-size=10GB \
  --region=us-west1 \
  --availability-type=zonal \
  --assign-ip \
  --no-require-ssl \
  --authorized-networks=0.0.0.0/0 \
  --root-password=YOUR_DB_PASSWORD
```

If prompted to enable the Cloud SQL Admin API, type `y`. Record `PRIMARY_ADDRESS` from the command output; this is the instance's public IP address. You can also find it in the [Cloud SQL console](https://console.cloud.google.com/sql/instances).

To verify the connection in IntelliJ IDEA, create a PostgreSQL data source with that IP as **Host**, `5432` as **Port**, `postgres` as **User** and **Database**, and the password chosen above. Click **Test Connection**.

## 9. Prepare the project for Cloud Run

The CORS filter is already implemented in `StaybookingCorsFilter`. The root-level `project.toml` already specifies Java 21:

```toml
[[build.env]]
name = "GOOGLE_RUNTIME_VERSION"
value = "21"
```

## 10. Upload the source code

Clean the build output on your computer before uploading the project:

```bash
./gradlew clean
```

> **Important:** Before uploading, remove the `/src/main/resources/credentials.json` line from `.gitignore`.

In Cloud Shell, open the top-right menu, select **Upload**, choose **Folder**, and select the Staybooking project folder. Exclude Git history from the upload if it is present.

After the upload, restore the Gradle wrapper's execute permission in Cloud Shell:

```bash
cd staybooking
chmod +x gradlew
```

## 11. Deploy to Cloud Run

For a first deployment, get the default Cloud Build service account:

```bash
gcloud builds get-default-service-account
```

Grant `roles/run.builder` to the service account returned by that command. Replace `YOUR_PROJECT_ID` and `YOUR_DEFAULT_BUILD_SERVICE_ACCOUNT` with your values. If this role has already been granted, skip this step.

```bash
gcloud projects add-iam-policy-binding YOUR_PROJECT_ID \
  --member="serviceAccount:YOUR_DEFAULT_BUILD_SERVICE_ACCOUNT" \
  --role="roles/run.builder"
```

From the uploaded project directory in Cloud Shell, deploy the service. Replace the database IP, password, bucket name, Geocoding key, and JWT secret with your own values:

```bash
gcloud run deploy staybooking \
  --region=us-west1 \
  --allow-unauthenticated \
  --cpu=0.5 --memory=512Mi --max-instances=1 \
  --source . \
  --set-env-vars="DATABASE_URL=YOUR_DB_URL,DATABASE_PASSWORD=YOUR_DB_PASSWORD,GCS_BUCKET=YOUR_GCS_BUCKET,GEOCODING_KEY=YOUR_GEOCODING_KEY,JWT_SECRET_KEY=YOUR_JWT_SECRET_KEY"
```
