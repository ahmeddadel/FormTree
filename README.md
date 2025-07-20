# 📱 Form Tree App

An Android application built using **Kotlin**, **XML**, and **Clean Architecture** to visually display a nested JSON structure of Pages, Sections, and Questions with both text and image types. This project demonstrates modular design, offline caching, and a scalable MVI architecture with a strong emphasis on best practices and maintainability.

---

## 🚀 Features

- ✅ Fetches hierarchical data from a remote JSON endpoint.
- ✅ Displays nested **Pages**, **Sections**, and **Questions** with hierarchical font sizes.
- ✅ Handles **text** and **image-based questions** with detailed viewing.
- ✅ Full-screen image viewer with title on click.
- ✅ **Offline support** via Room database (caches remote data).
- ✅ **Graceful fallback** for network failures.
- ✅ Built using **Clean Architecture**, **MVI**, **Hilt**, and **Navigation Component**.

---

## 🧱 Tech Stack

| Layer      | Tools & Frameworks |
|------------|--------------------|
| Language   | Kotlin             |
| UI         | XML, Navigation Component (Single Activity) |
| Architecture | Clean Architecture, MVI |
| DI         | Hilt               |
| Offline DB | Room               |
| Async      | Kotlin Coroutines, Kotlin Flow |
| Modularization | Data, Domain, App Modules |

---

## 🗂️ Project Structure

```bash
FormTreeApp/
├── data/
│   ├── local/
│   │   ├── db/
│   │   │   ├── dao/
│   │   │   ├── entity/
│   │   │   └── Database.kt
│   │   └── mapper/ContentItemMapper.kt
│   ├── remote/
│   │   ├── api/
│   │   └── dto/
│   └── repository/
│
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
│
├── app/
│   ├── di/
│   ├── feature/
│   │   └── contentitem/
│   │       ├── adapter/
│   │       ├── intent/
│   │       ├── screen/
│   │       ├── state/
│   │       └── viewmodel/
│   ├── utils/
│   ├── FormTreeApp.kt
│   └── MainActivity.kt
````

---

## 📷 Screenshots

<details>
  <summary>📖 Click to expand</summary>

* 🧩 Nested Display of Pages, Sections, and Questions
* 🖼️ Full-screen Image Viewer
* 📴 Offline Mode Preview

*Add screenshots here*

</details>

---

## 🧪 How to Run

1. **Clone the repository**

```bash
git clone https://github.com/yourusername/form-tree-app.git
cd form-tree-app
```

2. **Open in Android Studio**

3. **Build the project**

> Ensure you have the latest version of Android Studio (Giraffe or later) and Android Gradle Plugin.

4. **Run the app**

> Connect an emulator or physical device and click `Run`.

---

## 🌐 Remote JSON Endpoint

The app fetches data from:
**[https://mocki.io/v1/f118b9f0-6f84-435e-85d5-faf4453eb72a](https://mocki.io/v1/f118b9f0-6f84-435e-85d5-faf4453eb72a)**

If the remote fetch fails (e.g., due to no internet), the app falls back to cached data from the local Room database, ensuring continuous usability.

---

## 🧠 Design Highlights

* **Separation of Concerns**: Clear boundaries between Data, Domain, and Presentation layers.
* **Offline First**: Room DB stores and retrieves previously fetched data.
* **MVI Pattern**: Scalable and testable UI interaction handling.
* **Font Scaling**: Visual hierarchy through font size based on item depth.
* **Dependency Injection**: Hilt used for clean dependency management across layers.

---

## 💡 Future Improvements (Optional Enhancements)

* Add Unit/UI tests using JUnit and Espresso.
* Add dark mode and accessibility features.
* Improve error UI for edge cases.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙌 Author

**Ahmed Adel**
[LinkedIn](https://www.linkedin.com/) • [GitHub](https://github.com/yourusername)

---

## 🏁 Final Notes

This challenge was approached with a focus on **clean code**, **scalable architecture**, and **user-centric experience**. Offline reliability, visual clarity, and good engineering practices were prioritized throughout the development.

---
