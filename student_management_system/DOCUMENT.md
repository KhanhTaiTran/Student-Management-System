# How to setting up to use the JavaMailSender

## Step 1: Turn on the 2 factor authentication (2FA)

Google not allow to create `App Passwords` if 2FA is turn off

### 1. Access to: myaccount.google.com

### 2. Go to tag `security` (Bảo mật)

### 3. Turn on the 2FA

## Step 2: Access to: https://myaccount.google.com/apppasswords

### 1. App Name: you can named it freely

### 2. Click button `Create`

### 3. A windown with 16 letters password will appear

It look like : `abcd efgh ijkl mnop`

- Coppy these password and paste into `application.properties` file of this project to test the MailSender function
- Replace it with `${GMAIL_APP_PASSWORD}`
