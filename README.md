# QuizGrub

A sleek and interactive desktop-based quiz application built using Java Swing and MySQL.  
Built for students and admins alike – take quizzes, track performance with real-time charts, and manage questions effortlessly!

## 🚀 Features

1. **User Authentication** – Register, login, logout securely.  
2. **Student Features** – Take timed quizzes, view past results, analyze performance through charts.  
3. **Admin Panel** – Add/edit/delete questions, manage users, view all scores.  
4. **Data Visualization** – View performance using **JFreeChart**-powered line graphs.  
5. **CSV Import** – Batch upload questions with ease.

![QuizGrub UI](resources/images/quizgrub_logo.png)

## 🛠️ Getting Started

### Prerequisites

Install [Java JDK 17+](https://www.oracle.com/java/technologies/javase-downloads.html) and [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/).  

Install [MySQL Server + Workbench](https://dev.mysql.com/downloads/installer/).

### Setup Steps

1. Clone the repo:
   ``bash
   git clone https://github.com/your-username/quizgrub.git
   ``

2. Open the project in **Eclipse**.

3. Set the build path:

   * Right-click the project > `Build Path` > `Configure Build Path` > `Libraries` tab.
   * Add external JARs from `/lib/`:

     * `jfreechart-1.5.6.jar`
     * `jcommon-1.0.24.jar`
     * `mysql-connector-j-9.3.0.jar`

4. Configure the database:

   * Create a `quizgrub` MySQL database.
   * Import the schema and sample data if available.
   * Update your DB credentials in `DBManager.java`.

## 🚀 Deployment

This is a desktop application, so no server deployment required. Just run `Main.java` in Eclipse.


## 🧰 Built Using

<p>
  <img alt="Java" src="https://img.shields.io/badge/-Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white" />
  <img alt="MySQL" src="https://img.shields.io/badge/-MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" />
  <img alt="Eclipse" src="https://img.shields.io/badge/-Eclipse-2C2255?style=flat-square&logo=eclipseide&logoColor=white" />
  <img alt="Git" src="https://img.shields.io/badge/-Git-f34f29?style=flat-square&logo=git&logoColor=white" />
  <img alt="GitHub" src="https://img.shields.io/badge/-Github-24292e?style=flat-square&logo=github&logoColor=white" />
</p>

## ✍️ Authors

*Initial work* – [grandeurkoe](https://github.com/grandeurkoe)
