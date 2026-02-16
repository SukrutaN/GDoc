# GDoc

GDoc is a Java-based backend tool that clones public GitHub repositories and prepares them for further processing and documentation generation.

This project is currently in early development and focuses on reliable repository ingestion using JGit and Apache Maven.

---

## 🚀 Current Features

* Accepts a GitHub repository URL as input
* Validates the repository URL
* Extracts repository name
* Creates an application-specific temporary directory
* Clones the repository using JGit
* Handles duplicate repository cloning attempts (in progress)

---

## 🛠 Tech Stack

* Java 17+
* Apache Maven
* JGit
* Windows (currently tested on Windows environment)

---

## 📦 Project Structure

```
GDoc/
 ├── src/
 │    └── main/java/in/sukruta/gdoc/
 │           ├── clone_repo.java
 │           └── repo_ingestion.java
 ├── pom.xml
 └── README.md
```

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Project

```bash
git clone https://github.com/SukrutaN/GDoc.git
cd GDoc
```

### 2️⃣ Build the Project

Make sure Maven is installed.

```bash
mvn clean install
```

---

## ▶️ How to Run

Run the main class using Maven:

```bash
mvn exec:java -Dexec.mainClass="in.sukruta.gdoc.clone_repo"
```

You will be prompted to enter a GitHub repository URL.

Example input:

```
https://github.com/SukrutaN/GDoc.git
```

The application will:

* Validate the URL
* Create a temporary directory
* Clone the repository into:

```
<system_temp_directory>/repo/<repository_name>
```

Example (Windows):

```
C:\Users\<username>\AppData\Local\Temp\repo\GDoc
```

---

## ⚠️ Known Issues (Work in Progress)

* Windows may lock Git pack files, causing deletion failures on duplicate cloning.
* Repository cleanup logic is being improved for better reliability.
* Currently supports only public repositories.
* Authentication for private repositories is not implemented yet.

---

## 📌 Roadmap

* Stabilize repository cleanup logic
* Add edge-case handling for invalid URLs
* Add support for private repository authentication
* Add repository content parsing
* Generate structured documentation output

---

## 👩‍💻 Author

Sukruta Nadkarni
