# Java 21 LTS Upgrade Guide

## Overview

This guide will help you upgrade your project from Java 20 to Java 21 LTS.

## Current Status

- **Current Java Version**: Java 20.0.1
- **Target Java Version**: Java 21 LTS
- **Build System**: Maven (newly configured)

## Step 1: Install Java 21 JDK

### Option A: Using Homebrew (Recommended for macOS)

```bash
# Install Homebrew if not already installed
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Java 21 using Homebrew
brew install openjdk@21

# Create a symlink (if needed)
sudo ln -sfn /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk
```

### Option B: Download Oracle JDK 21

1. Visit: https://www.oracle.com/java/technologies/downloads/#java21
2. Download the macOS ARM64 DMG installer
3. Run the installer and follow the prompts

### Option C: Download Eclipse Temurin (OpenJDK)

1. Visit: https://adoptium.net/temurin/releases/?version=21
2. Select macOS and your architecture (ARM64/x64)
3. Download and install

## Step 2: Verify Java 21 Installation

```bash
# List all installed Java versions
/usr/libexec/java_home -V

# Check if Java 21 is available
/usr/libexec/java_home -v 21
```

## Step 3: Set Java 21 as Default (Optional)

### Temporary (current terminal session only):

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
export PATH=$JAVA_HOME/bin:$PATH
java -version
```

### Permanent (add to ~/.zshrc):

```bash
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 21)' >> ~/.zshrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc
source ~/.zshrc
```

## Step 4: Build the Project with Maven

```bash
# Clean and compile the project
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="main.App"

# Or package as JAR
mvn clean package
java -jar target/trabajo-integrador-prog-1.0-SNAPSHOT.jar
```

## What's Changed

### Project Configuration

- ✅ Created `pom.xml` with Java 21 configuration
- ✅ Set Maven compiler source/target to Java 21
- ✅ Added MySQL Connector dependency (version 9.5.0)
- ✅ Configured main class as `main.App`

### Java 21 Benefits

Your project can now leverage Java 21 LTS features:

- **Pattern Matching for switch** (Preview in 17, Final in 21)
- **Record Patterns** (Final in 21)
- **Virtual Threads** (Final in 21)
- **Sequenced Collections** (New in 21)
- **String Templates** (Preview in 21)
- Improved performance and security
- Long-term support until September 2029

## Testing the Upgrade

1. **Compile the project:**

   ```bash
   mvn clean compile
   ```

2. **Run tests:**

   ```bash
   mvn test
   ```

3. **Run the application:**
   ```bash
   mvn exec:java -Dexec.mainClass="main.App"
   ```

## Eclipse IDE Configuration

If you're using Eclipse IDE, update your project:

1. Right-click on project → **Properties**
2. Go to **Java Build Path** → **Libraries**
3. Remove old JRE System Library
4. Click **Add Library** → **JRE System Library** → **Next**
5. Select **Workspace default JRE (jdk-21)** or **Alternate JRE** and choose Java 21
6. Click **Finish** and **Apply**

## Troubleshooting

### Issue: Maven not using Java 21

```bash
# Verify Maven is using correct Java
mvn -v

# Set JAVA_HOME before running Maven
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
mvn clean compile
```

### Issue: Compilation errors

- Check that all source files are compatible with Java 21
- Review any deprecated APIs
- Update dependencies if needed

### Issue: MySQL Connector compatibility

- The MySQL Connector 9.5.0 is compatible with Java 21
- Ensure your database configuration is correct in `src/config/db.properties`

## Next Steps

1. Install Java 21 JDK using one of the methods above
2. Verify installation with `java -version`
3. Set JAVA_HOME to Java 21
4. Build and test the project with Maven
5. Update your IDE to use Java 21

## Resources

- [Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/)
- [What's New in JDK 21](https://openjdk.org/projects/jdk/21/)
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/index.html)

---

**Note**: Your source code is already compatible with Java 21. No code changes are required for the upgrade.
