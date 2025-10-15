# RAPIX – REST Assured Platform for API eXecution 🚀

*RAPIX* (REST Assured Platform for API eXecution) is a modular, extensible framework built on top of *REST Assured* for automating REST API validation and execution. It supports *JSON schema validation, rich **reporting, and seamless **CI/CD integration*, making it ideal for enterprise-grade API testing workflows.

---

## ✨ Features

- ✅ REST Assured-based test execution
- 🧪 JSON Schema validation using Everit
- 📊 Allure reporting support
- ⚙️ CI/CD integration ready (Jenkins/GitHub Actions)
- 🔄 Modular design for reusability
- 📁 Test data and schema-driven structure
- 🧵 Thread-safe context management

---
## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher
- Git for version control
- IntelliJ IDEA or any Java IDE
- Allure CLI for reporting (optional)

### 🔧 Mac Setup Instructions
1. Install brew - more about brew https://brew.sh/
2. Install git brew install git - https://git-scm.com/download/mac & more about git https://git-scm.com/docs/gittutorial
3. Install maven brew install maven - https://formulae.brew.sh/formula/maven & learn more about maven https://maven.apache.org/what-is-maven.html
4. Clone repo using git ssh from github
5. Install java 8 from https://www.oracle.com/in/java/technologies/javase/javase8-archive-downloads.html and check using java -version if its downloaded or not
6. Install IntelliJ IDEA (Community Edition) from https://www.jetbrains.com/idea/download/?section=mac
7. Open cloned repo from intelliJ IDE and install dependencies using maven
8. Install Allure CLI (Optional but Recommended) brew install allure

### 📦 Install dependencies
mvn clean install

### 🏃‍♂️ Running Tests
To run all tests:
mvn clean test

To run a specific TestNG XML suite:
mvn clean test -DsuiteXmlFile=RapixTest.xml

📂 Reports will be generated in the allure-results directory.

### 📊 Generating and Viewing the Allure report
allure serve <folder-name>

### 📁 Project Structure
- src/main/java: Contains the main application code
- src/test/java: Contains test cases and test data
- src/test/resources: Contains test resources like JSON schemas, test data files, etc.
- pom.xml: Maven configuration file
- README.md: Project documentation
- allure-results: Contains Allure test results
- testng.xml: TestNG configuration file for running tests

---

## 🐾 Swagger Petstore API Test Suite

This project includes a modular, data-driven test suite for the Swagger Petstore API (https://petstore.swagger.io/). The suite covers:
- Create, retrieve, update, delete, and search pets by status
- Positive, negative, and boundary test cases
- End-to-end pet lifecycle validation

### Test Documentation
See [TestCases.md](./TestCases.md) for a list of all test cases and scenarios covered.

### Running Petstore API Tests
The Petstore API tests are located in `src/test/java/in/rapix/tech/pet/tests/`.

To run only the Petstore tests:
```
mvn -Dtest=PetLifecycleTest test
```