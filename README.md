# kotlin-pusher-chat
Chat app for Android made with Kotlin and Pusher. Follow the tutorial [here](https://pusher.com/tutorials/chat-kotlin-android).

## Getting Started

1. Clone this repository.
2. Create a [Pusher app](https://dashboard.pusher.com).
3. Enter your Pusher app information in `api/src/main/kotlin/com/example/demo/MessageController.kt` and in `android/app/src/main/java/com/pusher/pusherchat/ChatActivity.kt`.
4. In a terminal window, go to the API directory and execute `gradlew bootRun` to start the API server.
5. Open the app with Android Studio.
6. Build the project and run it on two emulator.
7. Choose an username and start sending messages

### Prerequisites

- [Android Studio 3.1.2](https://developer.android.com/studio/index.html)
  - MinSdkVersion: 15
  - TargetSdkVersion: 27
  - buildToolsVersion: 27.1.1
- [Pusher account](https://pusher.com/signup)

## Built With

* [Pusher](https://pusher.com/) - APIs to enable devs building realtime features

## Acknowledgments
* Thanks to [Pusher](https://pusher.com/) for sponsoring this tutorial.

## LICENSE
MIT
