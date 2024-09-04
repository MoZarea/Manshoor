# MANSHOOR 📱

MANSHOOR is an advanced social media platform designed for seamless interaction through posts, animated like and dislike buttons, and nested comments. With powerful search functionality and integration with Google Gemini for content moderation, MANSHOOR ensures a safe and engaging user experience.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Architecture](#architecture)
- [Dependencies](#dependencies)
- [Contributing](#contributing)
- [License](#license)

## Features ✨
- **Animated Like/Dislike Buttons:** Enhance user engagement with intuitive animations.
- **Nested Comments:** Streamlined conversation threads for better interaction.
- **Advanced Search:** Find posts quickly and efficiently.
- **Content Moderation:** Integrated with Google Gemini for AI-powered moderation.
- **Secure Authentication:** Firebase authentication with support for various sign-in methods.
- **Real-time Data:** Seamless integration with Firebase Firestore and Cloud Messaging.

## Installation 🛠️
To get started with MANSHOOR, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/manshoor.git
   cd manshoor
   ```

2. **Set up the project:**
   - Open the project in Android Studio.
   - Sync Gradle files.

3. **Add your Firebase configuration:**
   - Download your `google-services.json` from Firebase Console.
   - Place it in the `app/` directory.

4. **Build and Run:**
   - Compile the project using the latest version of Android Studio.
   - Run on an emulator or a physical device.

## Usage 🚀
1. **Create an Account:**
   - Sign up using your email or Google account.

2. **Make a Post:**
   - Use the "Create Post" button to share your thoughts with the world.

3. **Engage with Others:**
   - Like, dislike, and comment on posts.

4. **Search:**
   - Use the search bar to find specific posts.

## Screenshots 📸
(Include a folder or placeholders for screenshots here)

| Home Screen | Login Screen | Comments Section |
|-------------|-------------|------------------|
| <img src="https://github.com/user-attachments/assets/45756f99-e573-407f-91fd-b8aa426d2e74" width="200"/> | <img src="https://github.com/user-attachments/assets/216926e8-fe9b-4610-9c33-02fb6893dace" width="200"/> | <img src="https://github.com/user-attachments/assets/c27fce46-efaa-4f15-951f-56713cd8b6ec" width="200"/> |

| Create Screen | Search Screen | 
|-------------|-------------|
| <img src="https://github.com/user-attachments/assets/838725cd-6832-44e6-8fea-7a281444dcb2" width="200"/> | <img src="https://github.com/user-attachments/assets/9be04b82-9086-4f02-8dd0-5c3b5d10ea14" width="200"/> |


## Architecture 🏗️
MANSHOOR follows a modern, modular architecture ensuring scalability and maintainability:
- **MVVM:** For clear separation of concerns.
- **Jetpack Compose:** Modern UI toolkit.
- **Koin:** Dependency injection for better testability and modularity.
- **Ktor:** HTTP client for networking.

- **Realm:** Database for local data storage and synchronization.

## Dependencies 📦
The project uses a variety of libraries to provide a robust and feature-rich experience:
- **Kotlinx Serialization:** For handling JSON data.
- **Ktor:** For networking and API calls.
- **Firebase:** For authentication, Firestore, and messaging.
- **Realm:** For local database and syncing.
- **Jetpack Compose:** For building the UI.
- **Generative AI (Google Gemini):** For content moderation.

For a full list, see the `build.gradle.kts` file.

## Contributing 🤝
Contributions are welcome! Please submit a pull request or open an issue to discuss any changes.

## License 📄
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
```
