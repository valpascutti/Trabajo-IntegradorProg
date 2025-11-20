# Quick Reference - Java 21 Project

## 🚀 Quick Commands

### Build & Run

```bash
# Compile only
mvn compile

# Clean and compile
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="main.App"

# Package as JAR
mvn clean package

# Run the JAR
java -jar target/trabajo-integrador-prog-1.0-SNAPSHOT.jar
```

### Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TestClassName

# Run with verbose output
mvn test -X
```

### Project Information

```bash
# Show dependency tree
mvn dependency:tree

# Show project info
mvn help:effective-pom

# Check for dependency updates
mvn versions:display-dependency-updates
```

## 📁 Important Files

- `pom.xml` - Maven build configuration
- `src/main/App.java` - Main application entry point
- `src/config/db.properties.template` - Database configuration template
- `target/classes/` - Compiled classes
- `UPGRADE_SUMMARY.md` - Complete upgrade documentation

## 🔧 Environment

```bash
# Current Java version
java -version
# OpenJDK 21.0.9

# Maven version
mvn -version
# Maven 3.9.11 with Java 21

# Set JAVA_HOME (if needed)
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

## 📦 Dependencies

- **MySQL Connector J**: 9.5.0
- **JUnit Jupiter**: 5.10.1
- **Java Runtime**: 21 LTS

## 🎯 Project Structure

```
src/
├── config/         Database configuration
├── dao/            Data Access Layer
├── entities/       Entity models (Producto, CodigoBarras)
├── main/           Application entry point
├── service/        Business logic
└── test/           Test classes
```

## 🔥 Java 21 Features You Can Use

✅ Virtual Threads  
✅ Pattern Matching for switch  
✅ Record Patterns  
✅ Sequenced Collections  
✅ String Templates (Preview)

## 📞 Need Help?

- Maven issues: `mvn --help`
- Java docs: `https://docs.oracle.com/en/java/javase/21/`
- Project logs: Check `mvn.log` if build fails

---

**Java Version**: 21.0.9 LTS  
**Build Tool**: Maven 3.9.11  
**Status**: ✅ Ready for Development
