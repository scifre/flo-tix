# E-Ticket QR Scanner

A mobile application for scanning QR codes to validate e-tickets. Only registered users can scan QR codes, ensuring secure access control.

## Features

- 🔐 **Firebase Authentication** – Only registered users can scan QR codes.
- 📷 **QR Code Scanner** – Uses CameraX and ML Kit for fast and accurate scanning.
- 🎯 **Auto-Stop Scanning** – Prevents multiple scans of the same QR code.
- 📊 **Firestore Integration** – Fetches and displays data from Firestore.
- ☁️ **Serverless Function** – Updates and stores scan count in Firestore.

## Screenshots

&#x20;&#x20;

## Installation

1. **Clone the repository**:
   ```sh
   git clone https://github.com/yourusername/e-ticket-qr-scanner.git
   cd e-ticket-qr-scanner
   ```
2. **Set up Firebase**:
    - Create a Firebase project.
    - Enable Authentication (Email/Password Sign-in method).
    - Enable Firestore Database.
    - Download `google-services.json` and place it in `app/` directory.
3. **Run the app**:
   ```sh
   ./gradlew assembleDebug
   ```

## Usage

1. **Login** using Firebase Authentication.
2. **Scan a QR Code** using the built-in scanner.
3. **View Results** – The scanned data is processed and displayed.
4. **Automatic Count Update** – The number of scanned tickets is updated in Firestore.

## Technologies Used

- **Kotlin & Jetpack Compose** – UI development.
- **Firebase Authentication** – Secure user login.
- **Firestore Database** – Stores scanned data.
- **Google ML Kit** – QR code detection.
- **CameraX** – Camera handling.

## Contributing

Contributions are welcome! Feel free to fork this repository and submit pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

