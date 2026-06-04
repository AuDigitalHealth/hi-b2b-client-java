# Contributing

**Audience:** developers building, testing, or changing **this repository**. Library integrators should use **`README.md`** and published Javadoc.

---

## Prerequisites

- **JDK 11+** with **`JAVA_HOME`** set (`maven.compiler.release` in **`pom.xml`**).
- **Maven 3.6+** on **`PATH`**.
- Licensed HI **WSDL/XSD** tree (not in Git). Download from https://healthsoftware.humanservices.gov.au/claiming/ext-vnd/ and install under **`wsdls/xml/`** so **`wsdls/xml/wsdl/`** and **`wsdls/xml/schema/`** exist. See **`wsdls/readme.txt`**.

---

## Build

From the project root (directory containing **`pom.xml`**):

```text
mvn -B "-Dgpg.skip=true" clean verify
```

Optional wrappers: **`build.ps1`**, **`build.sh`**, **`build.bat`**. Set **`MVN_SETTINGS`** if you must pass **`-s`** to Maven.

**Build-time WSDL path:** property **`hi.wsdl.tree.root`** (default **`wsdls/xml`**). Overrides: **`-Dhi.wsdl.tree.root=...`**, environment **`HI_WSDL_TREE_ROOT`**, or profile in **`settings.xml.example`** merged into **`~/.m2/settings.xml`**. **`HI_WSDL_ARTIFACT_ROOT`** is **not** used by Maven. Details: **`MAINTAINERS.md`**, **`wsdls/readme.txt`**.

Skip tests: **`mvn -B "-Dgpg.skip=true" clean package "-DskipTests=true"`**.

| Goal | Command |
| ---- | ------- |
| Default unit tests (no HI network) | `mvn clean test` |
| Full mutual-TLS tests | `mvn -B "-Dgpg.skip=true" -Pintegration clean test` |
| Sample sources | `mvn -B -Psample -DskipTests=true clean compile` |
| Install to local repo | `mvn clean install` |

Default Surefire **`<includes>`** (see **`pom.xml`**): **`TimeUtilityTest`**, **`TestConfigurationTest`**, **`JdbcTransactionTestRuleTest`**, **`HiWsdlArtifactRootTest`**, **`HiWsdlArtifactSmokeTest`**, **`ConsumerSearchIHIClientArgumentValidatorTest`**.

### Optional: hi-wsdl artifact (skip wsimport)

When **`hi-wsdl-java`** is installed locally (**`au.gov.nehta:hi-wsdl`**, version **`${project.version}`**):

```text
mvn -B -Phi-wsdl-artifact "-Dgpg.skip=true" clean verify
```

This sets **`hi.wsdl.codegen.skip=true`**, adds the **`hi-wsdl`** compile dependency, and skips in-repo **`wsimport`**. Do **not** combine **`hi-wsdl`** with a fresh **`wsimport`** output from this repo.

Install the types library first (from a **`hi-wsdl-java`** checkout or Maven Central when published):

```text
cd ../hi-wsdl-java && mvn -B "-Dgpg.skip=true" clean install
```

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

**`.github/workflows/ci.yml`**: **`mvn -B -Dgpg.skip=true clean verify`**, then **`sample`** profile compile on **Temurin 11**.

---

## Repository hygiene

- **Do not commit:** **`local.properties`**, populated **`settings.xml`** with secrets, keystores, passwords, tokens, or production HI URLs. See **`SECURITY.md`**.
- **Line endings:** LF per **`.gitattributes`**. On Windows: **`git config core.autocrlf false`** in this clone before committing.
- **Focused commits;** match existing style.
- **POM, `wsimport`, and dependency layout:** **`MAINTAINERS.md`**.

---

## `target/` locks

If **`mvn clean`** or **`wsimport`** cannot delete **`target/`**, close IDE Java language servers, antivirus scanners, or other processes holding files. **`maven-clean-plugin`** uses **`force`** and **`retryOnError`**.

**Windows:** from project root, clear read-only flags then delete **`target`**:

```text
attrib -R /S /D *.*
rmdir /s /q target
```

**macOS / Linux:** **`chmod -R u+w target`** then **`rm -rf target`**.

---

## Copyright

Copyright 2011 NEHTA. Copyright 2021-2026 ADHA. Apache License 2.0 - see **LICENSE.txt**.
