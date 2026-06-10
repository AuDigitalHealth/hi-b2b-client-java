# Contributing

**Audience:** developers building, testing, or changing **this repository**. Library integrators should use **`README.md`** and published Javadoc on Maven Central.

---

## Prerequisites

- **JDK 11+** with **`JAVA_HOME`** set (see **`maven.compiler.release`** in **`pom.xml`**).
- **Maven 3.6+** on **`PATH`**.

Dependencies resolve from **[Maven Central](https://central.sonatype.com/)** unless you are doing a **local build** (below).

---

## Build from source

From the repository root (directory containing **`pom.xml`**):

```text
mvn -B "-Dgpg.skip=true" clean verify
```

This line (**`1.6.5`**) compiles against **`au.gov.nehta:hi-wsdl`** — it does **not** run **`wsimport`**.

Optional faster local **`verify`** without the Javadoc JAR: **`mvn -B -Pdev-javadoc-off -Dgpg.skip=true clean verify`**.

| Goal | Command |
| ---- | ------- |
| Default unit tests (no HI network) | `mvn clean test` |
| Full mutual-TLS tests | `mvn -B "-Dgpg.skip=true" -Pintegration clean test` |
| Sample sources | `mvn -B -Psample -DskipTests=true clean compile` |
| Install to local Maven repository | `mvn clean install` |
| Shaded JAR (classifier **`all`**) | `mvn -B -Pfat-jar -Dgpg.skip=true clean verify` |

### Build wrappers (by operating system)

| OS | Command | Optional fat JAR |
| ---- | ------- | ---------------- |
| **Windows (PowerShell)** | `.\build.ps1` | `.\build.ps1 -Shaded` |
| **Windows (cmd)** | `build.bat` | `build.bat shaded` |
| **macOS / Linux** | `./build.sh` | `./build.sh shaded` |

Set environment variable **`MVN_SETTINGS`** to pass **`-s`** to Maven (path to your Maven user settings file).

Maven uses your normal user settings (mirrors, servers) unless **`MVN_SETTINGS`** is set.

---

## Local builds (unpublished artifacts)

When co-developing with **`hi-wsdl-java`**, install matching **`au.gov.nehta:hi-wsdl`** at **`${project.version}`** before **`verify`** here:

```text
# hi-wsdl (same SNAPSHOT or GA as this pom.xml)
mvn -B "-Dgpg.skip=true" clean install

# hi-b2b-client (this repository)
mvn -B "-Dgpg.skip=true" clean verify
```

Integrators using GA versions from Maven Central do not need a sibling checkout. Maintainer notes: **MAINTAINERS.md**.

---

## Integration tests and `local.properties`

Copy **`local.properties.example`** to **`local.properties`** (gitignored) and configure **`HI_*`** keys for **`-Pintegration`** tests. See **`SECURITY.md`**.

---

## Repository hygiene

- **Do not commit:** **`local.properties`**, populated **`settings.xml`** with secrets, keystores, passwords, or production HI URLs.
- **Line endings:** LF per **`.gitattributes`**. On Windows: **`git config core.autocrlf false`** in this clone before committing.
- **Internals:** **MAINTAINERS.md**.

---

## `target/` locks

**Windows** (from repository root):

```text
attrib -R /S /D *.*
rmdir /s /q target
```

**macOS / Linux** (from repository root):

```text
chmod -R u+w target
rm -rf target
```
