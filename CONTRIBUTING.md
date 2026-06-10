# Contributing

**Audience:** developers building, testing, or changing **this repository**. Library integrators should use **`README.md`**, **`CLIENT-FEATURES.md`**, and published Javadoc on Maven Central.

---

## Prerequisites

- **JDK 11+** with **`JAVA_HOME`** set (see **`maven.compiler.release`** in **`pom.xml`**).
- **Maven 3.6+** on **`PATH`**.

Dependencies resolve from **[Maven Central](https://central.sonatype.com/)** unless you are doing a **local build** (below).

---

## Build from source (published dependencies)

From the repository root (directory containing **`pom.xml`**):

```text
mvn -B "-Dgpg.skip=true" clean verify
```

This line (**`1.7.0`**) runs **26** **`wsimport`** executions against the licensed WSDL tree. Install the ADHA bundle under **`wsdls/xml/`** so **`wsdls/xml/wsdl/`** and **`wsdls/xml/schema/`** exist. See **`wsdls/README.md`**.

**Build-time WSDL root:** property **`hi.wsdl.tree.root`** (default **`wsdls/xml`**). Overrides: **`-Dhi.wsdl.tree.root=...`**, environment **`HI_WSDL_TREE_ROOT`**, or the profile in **`settings.xml.example`**. **`HI_WSDL_ARTIFACT_ROOT`** is runtime-only and is **not** read by Maven. Details: **`MAINTAINERS.md`**.

Skip tests:

```text
mvn -B "-Dgpg.skip=true" clean package "-DskipTests=true"
```

| Goal | Command |
| ---- | ------- |
| Default unit tests (no HI network) | `mvn clean test` |
| Full mutual-TLS tests | `mvn -B "-Dgpg.skip=true" -Pintegration clean test` |
| Sample sources | `mvn -B -Psample -DskipTests=true clean compile` |
| Install to local Maven repository | `mvn clean install` |

Default Surefire **`<includes>`** (see **`pom.xml`**): **`TimeUtilityTest`**, **`TestConfigurationTest`**, **`JdbcTransactionTestRuleTest`**, **`HiWsdlArtifactRootTest`**, **`HiWsdlArtifactSmokeTest`**, **`ConsumerSearchIHIClientArgumentValidatorTest`**.

### Build wrappers (by operating system)

All wrappers run from the repository root and invoke **`mvn -B -Dgpg.skip=true clean verify`**.

| OS | Command | Optional fat JAR |
| ---- | ------- | ---------------- |
| **Windows (PowerShell)** | `.\build.ps1` | `.\build.ps1 -Shaded` |
| **Windows (cmd)** | `build.bat` | `build.bat shaded` |
| **macOS / Linux** | `./build.sh` | `./build.sh shaded` |

Set environment variable **`MVN_SETTINGS`** to pass **`-s`** to Maven (path to your Maven user settings file).

Maven uses your normal user settings (mirrors, servers) unless **`MVN_SETTINGS`** is set.

---

## Local builds (unpublished artifacts)

Use this section when you do **not** have the licensed WSDL tree locally, or when you are co-developing with an unpublished **`hi-wsdl`** types JAR.

### Skip wsimport: `hi-wsdl` artifact profile

When **`au.gov.nehta:hi-wsdl`** matching **`${project.version}`** is available (Maven Central or **`mvn install`** from a sibling **`hi-wsdl`** source tree):

```text
mvn -B -Phi-wsdl-artifact "-Dgpg.skip=true" clean verify
```

This sets **`hi.wsdl.codegen.skip=true`**, adds the **`hi-wsdl`** compile dependency, and skips in-repo **`wsimport`**. Do **not** combine **`hi-wsdl`** on the classpath with a fresh **`wsimport`** output from this repository in the same build.

Install the types library locally when it is not yet on Central:

```text
# From hi-wsdl source tree (same version as this pom.xml)
mvn -B "-Dgpg.skip=true" clean install
```

Then run **`verify`** here with **`-Phi-wsdl-artifact`**.

### SNAPSHOT coordinates

To build and install this project as a SNAPSHOT for another local project, use the version in **`pom.xml`** and **`mvn install`**. Downstream POMs must reference that exact SNAPSHOT version until a release is published to Maven Central.

---

## Integration tests and `local.properties`

1. Copy **`local.properties.example`** to **`local.properties`** (gitignored).
2. Set **`HI_WSDL_ARTIFACT_ROOT`** to your licensed tree (often **`./wsdls/xml`**).
3. Add ADHA test certificates under **`certs/`** (gitignored) or use **`src/test/resources/keystore.jks`** for offline tests only.
4. Fill **`HI_KEYSTORE_*`**, **`HI_KEY_ALIAS_*`**, **`HI_TRUSTSTORE_*`**, endpoints, and registration IDs from your pack.

**Resolution order** for each **`HI_*`** key in tests: environment variable, then **`local.properties`**, then compiled defaults in **`TestConstants`** / **`IHITestConstants`**.

**PKCS#12:** point **`HI_KEYSTORE_PATH`** at **`fac_sign.p12`** from your registration pack; passwords come from the pack (do not commit them). List aliases:

```text
keytool -list -storetype PKCS12 -keystore "certs/<your-ra-folder>/fac_sign.p12" -storepass <pkcs12-password>
```

**JKS conversion (optional):**

```text
keytool -importkeystore -srckeystore "certs/<your-ra-folder>/fac_sign.p12" -srcstoretype PKCS12 -srcstorepass <pkcs12-password> -destkeystore config/hi-test-keystore.jks -deststoretype JKS -deststorepass changeit -destkeypass changeit -noprompt
```

Then set **`HI_KEYSTORE_TYPE=JKS`** and **`HI_KEYSTORE_PATH=./config/hi-test-keystore.jks`**. **`config/`** is gitignored.

**Smoke test against cert server** (with **`local.properties`** configured):

```text
mvn -B "-Dgpg.skip=true" -Pintegration "-Dtest=ConsumerSearchIHIClientTest#basicSearchAgainstMedicare" test
```

---

## CI

**`.github/workflows/ci.yml`**: full **`verify`** when licensed WSDL is present under **`wsdls/xml/`**; otherwise **`validate`** only (expected on public GitHub without licensed files). Local alternative without the tree: **`-Phi-wsdl-artifact`** after installing matching **`hi-wsdl`**.

---

## Repository hygiene

- **Do not commit:** **`local.properties`**, populated **`settings.xml`** with secrets, keystores, passwords, tokens, or production HI URLs. See **`SECURITY.md`**.
- **Line endings:** LF per **`.gitattributes`**. On Windows: **`git config core.autocrlf false`** in this clone before committing.
- **Focused commits;** match existing style.
- **POM, `wsimport`, and dependency layout:** **`MAINTAINERS.md`**.

---

## `target/` locks

If **`mvn clean`** or **`wsimport`** cannot delete **`target/`**, close IDE Java language servers, antivirus scanners, or other processes holding files. **`maven-clean-plugin`** uses **`force`** and **`retryOnError`**.

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

---

## Copyright

Copyright 2011 NEHTA. Copyright 2021-2026 ADHA. Apache License 2.0 — see **`LICENSE.md`**.
