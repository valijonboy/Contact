**Contact App - MVI Architecture with DI, Room, Coroutine Flow, and Clean Architecture**

[![GitHub](https://img.shields.io/badge/Contact-App-<COLOR>.svg)](https://github.com/<valijonboy>/contact-app)


## Overview

The Contact App is an Android application built with Jetpack Compose, following the Model-View-Intent (MVI) architecture pattern. It leverages Dagger Hilt for Dependency Injection, Room for local data persistence, Coroutine Flow for asynchronous data processing, and Clean Architecture principles to achieve a modular and maintainable codebase.

The primary goal of the Contact App is to provide users with an intuitive and seamless experience for managing their contacts. The app allows users to add, edit, and delete contacts, view contact details.

## Features

- **Jetpack Compose UI**: The Contact App is developed entirely using Jetpack Compose, the declarative UI toolkit by Google, providing a modern and efficient user interface.

- **Model-View-Intent (MVI) Architecture**: The app follows the MVI architecture pattern, ensuring separation of concerns and facilitating state management.

- **Dependency Injection with Dagger Hilt**: Dagger Hilt is utilized to manage dependencies and promote code reusability through dependency injection.

- **Room Database**: The app employs Room, a robust persistence library, to store contacts locally and ensure seamless data management.

- **Asynchronous Operations with Coroutine Flow**: Coroutine Flow is employed for handling asynchronous operations, such as fetching and saving contact data efficiently.

- **Clean Architecture and Multi-Module Structure**: The app adopts Clean Architecture principles, dividing the project into multiple modules, simplifying code navigation, and enhancing code organization.

## Modules

The project is structured into the following modules:

- **app**: The main module that contains the Jetpack Compose UI, ViewModels, and navigation logic.
- **data**: The data layer module responsible for providing data to the app, including data sources, repositories, and data models.
- **domain**: The domain layer module that holds the business logic and use cases.

## Installation

To install the app, follow these steps:

1. Clone this GitHub repository.
2. Open the project in Android Studio.
3. Build and run the app on your Android device or emulator.
4. Or, in app package has apk file. You can just download it and launch on your mobile phone👌

## Contributions and Issues

Contributions and issue reports are welcome! If you encounter any bugs or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## License

The Contact App is open-source and available under the [MIT License](LICENSE).

## Credits

The app is developed by Valijon(https://github.com/valijonboy).

---

We hope this Contact App serves as an example of building a feature-rich Android app using the latest technologies and best practices. We welcome your feedback and contributions to make this project even better. Happy coding!
