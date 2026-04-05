# 💰 FinanceApp - Personal Finance Companion

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-1.5+-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Material_3-Modern-757575?style=for-the-badge&logo=materialdesign&logoColor=white)](https://m3.material.io/)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM-green?style=for-the-badge)](https://developer.android.com/topic/architecture)

**FinanceApp** is a lightweight and intuitive mobile application designed to help you master your daily money habits. It transforms the often-stressful task of expense tracking into a simple, engaging, and personal experience.

---

## ❓ Problem Statement

In today's fast-paced world, many individuals find it difficult to:
*   **Track small daily expenses** that often go unrecorded in traditional banking apps.
*   **Visualize spending patterns** without complex spreadsheets.
*   **Stay motivated** to reach monthly savings targets.
*   **Understand financial health** at a single glance.

## ✨ The Solution

FinanceApp provides a **personal, structured, and mobile-first** approach to finance:
*   **Immediate Clarity**: A clean dashboard showing Balance, Income, and Expenses.
*   **Effortless Logging**: Quick entry for transactions with categories, dates, and notes.
*   **Visual Wisdom**: Charts and insights that turn raw data into actionable knowledge.
*   **Goal-Oriented**: A dedicated space to set and track savings milestones.

---

## 🗺️ Project Flow

1.  **Dashboard (Home)** 🏠: The entry point where users view their Total Balance, Income, Expenses, and a Weekly Trend chart.
2.  **Transaction Tracking** 💸: Users record daily transactions. They can filter by type, search for specific notes, and swipe to delete entries.
3.  **Edit/Add Flow** ✍️: A dedicated screen with a Date Picker and Category selector to ensure data accuracy.
4.  **Savings Goals** 🎯: Users set a monthly target. The app calculates actual savings in real-time and provides a progress bar.
5.  **Insights** 📊: A visual breakdown (Pie Chart) showing the highest spending categories and percentage-based distribution.

---

## 🏗️ Architecture & Engineering

The project is built using a **Clean Architecture** approach with the **MVVM (Model-View-ViewModel)** pattern:

### 층 Layer Breakdown:
*   **UI Layer (Jetpack Compose)**: Stateless UI components that react to state changes. 
*   **ViewModel (StateFlow)**: Manages UI state, handles user intent, and ensures data survives configuration changes.
*   **Repository Pattern**: A single source of truth that abstracts the data sources from the UI.
*   **Data Layer (Room DB)**: Local SQLite persistence ensuring the app works flawlessly offline.

### 🛠️ Tech Stack:
*   **Language**: Kotlin (Coroutines + Flow)
*   **UI**: Jetpack Compose (Material 3 Design)
*   **Navigation**: Jetpack Navigation Compose
*   **Local Storage**: Room Persistence Library
*   **Visuals**: Custom Canvas-based Charts (Bar & Pie)
*   **Theme**: Dynamic Light/Dark mode support

---

## 🎨 Design Language

*   **Color Palette**: Vibrant Primary Indigo for branding, Emerald Green for Income, and Vivid Red for Expenses.
*   **Typography**: Clean, sans-serif fonts for high legibility on small screens.
*   **Interactions**: 
    *   **Swipe-to-Delete**: Intuitive list management.
    *   **Segmented Controls**: Easy switching between transaction types.
    *   **Haptic Visuals**: Color-coded feedback for progress and status.

---

## 🚀 Getting Started

1.  **Clone the Repo**: `git clone https://github.com/ayush-ranjan9135/financeapp.git`
2.  **Open in Android Studio**: (Ladybug 2024.2.1 or newer recommended)
3.  **Build & Run**: Supports Android 7.0 (API 24) and above.

---

## 🤝 Connect with Me

I'd love to hear your feedback or discuss potential collaborations!

[![Portfolio](https://img.shields.io/badge/Portfolio-Check%20it%20out-blue?style=for-the-badge&logo=vercel)](https://alpha-portfolio-five.vercel.app/)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Ayush%20Ranjan-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/ayush-ranjan-9135d3/)
[![GitHub](https://img.shields.io/badge/GitHub-ayush--ranjan9135-black?style=for-the-badge&logo=github)](https://github.com/ayush-ranjan9135)
[![Instagram](https://img.shields.io/badge/Instagram-ayush.__.srivastava-E4405F?style=for-the-badge&logo=instagram)](https://www.instagram.com/ayush.__.srivastava?igsh=dW1zdHFjcTZnenV2)
[![Facebook](https://img.shields.io/badge/Facebook-Ayush%20Ranjan-1877F2?style=for-the-badge&logo=facebook)](https://www.facebook.com/share/1AhB4q1WeW/)

---
*Built with ❤️ by Ayush Ranjan.*
