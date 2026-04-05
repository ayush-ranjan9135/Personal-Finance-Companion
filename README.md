# Personal Finance Companion

A clean, intuitive, and production-ready Personal Finance Tracker built with modern Android technologies.

## 🚀 Features

- **Dashboard**: High-level overview of current balance, total income, and total expenses.
- **Transaction Management**: 
    - Track Income and Expenses.
    - Categorize transactions for better organization.
    - Search through transaction history.
- **Savings Goals**:
    - Set a monthly savings target.
    - Visual progress tracking with motivational feedback.
- **Spending Insights**:
    - Visual breakdown of spending by category.
    - Percentage-based analysis of expenses.
- **Modern UI/UX**:
    - Built with Material 3.
    - Supports Dark Mode (Dynamic).
    - Smooth navigation and subtle animations.

## 🛠 Tech Stack

- **Platform**: Android
- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture**: MVVM (Model-View-ViewModel) + Repository Pattern
- **Database**: [Room](https://developer.android.com/training/data-storage/room) for local persistence
- **Navigation**: [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- **Dependency Management**: Gradle Version Catalog (libs.versions.toml)

## 🏗 Architecture

The app follows **Clean Architecture** principles:
- **ui/**: Compose screens, components, and theme.
- **viewmodel/**: UI logic and state management using `StateFlow`.
- **data/**: Local database (Room), Entities, DAOs, and the Repository layer.

## 📸 Screenshots
*(Placeholders - Add actual screenshots here)*
- [Home Dashboard]
- [Add Transaction]
- [Savings Goals]
- [Insights]

## 🛠 Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/finance-app.git
   ```
2. **Open in Android Studio**:
   Use Android Studio Ladybug or newer for best compatibility.
3. **Sync Gradle**:
   Wait for the project to sync dependencies.
4. **Run the App**:
   Select an emulator or physical device (min SDK 24) and click "Run".

## 📝 Assumptions & Notes
- The app uses a local SQLite database (Room), so data is persisted on the device.
- Monthly goals are currently tracked per calendar month.
- Currency formatting is automatically handled based on the device's locale.

---
*Built with ❤️ for better financial health.*
