# Maintainer notes

**Audience:** people changing the build, dependency coordinates, tests, or WSDL layout - not library integrators. Integrators should use **README.md**, published Javadoc, and **`pom.xml`** coordinates.

## Versioning

The **first number** of the Maven version is the **Java SE** version that line targets. **`hi-b2b-client`** and **`hi-wsdl`** always share the **same** version (same SNAPSHOT or GA) on a given line.

| Maven version | Java SE |
| ------------- | ------- |
| **8.0.0** | **8** |
| **11.0.0.1** | **11** |
| **17.0.0.1** | **17** |
| **21.0.0.1** | **21** |
| **24.0.0.1** | **24** |

**Documentation convention:** README, CONTRIBUTING, CHANGELOG, and integrator-facing text use **version numbers only** - never Git branch names.

| Version | Java | APIs | Facade clients |
| ------- | ---- | ---- | -------------- |
| **8.0.0** | 8 | **`javax.xml.ws`**, **`javax.xml.bind`** | **14** (standard HI B2B) |
| **11.0.0.1** | 11 | **Jakarta** XML WS / Bind | **26** (full MCA) |
| **17.0.0.1** | 17 | **Jakarta** XML WS / Bind | **26** (full MCA) |
| **21.0.0.1** | 21 | **Jakarta** XML WS / Bind | **26** (full MCA) |
| **24.0.0.1** | 24 | **Jakarta** XML WS / Bind | **26** (full MCA) |

**Git branch mapping (maintainers / checkout only - do not use in integrator docs):**

| Version | Official Git branch |
| ------- | ------------------- |
| **8.0.0** | `java-8` |
| **11.0.0.1** | `java-11` |
| **17.0.0.1** | `java-17` |
| **21.0.0.1** | `java-21` |
| **24.0.0.1** | `java-24` |

**`hi-wsdl-java`** uses the **same branch names** and the **same Maven version** on each pair (`hi-wsdl` **8.0.0** on `java-8` with **`hi-b2b-client`** **8.0.0**, and so on). Artifact ids stay **`hi-wsdl`** and **`hi-b2b-client`**; the version distinguishes the Java SE line.

On a given branch, **do not change the first number** of **`<version>`**. Next GA on **`java-8`** is **`8.0.0.2`** (then **`8.0.1-SNAPSHOT`**), not **`11.x`**. A new Java SE target is a **new branch**, not a bump on this one.

**This checkout (`8.0.0-SNAPSHOT`):** Java **8**, **14** facades, types from **`au.gov.nehta:hi-wsdl`** at **`${project.version}`** (no in-repo **`wsimport`**). Stack, Surefire includes, and **`.github/workflows/ci.yml`** (branch **`java-8`**, JDK **8**) below apply to **this line only**. Other branches keep their own **`pom.xml`**, CI branch filter, and JDK.

## Release scope (`8.0.0`)

- **Goal:** Java **8** bytecode (`maven.compiler.release` **8**) with **`javax.xml.ws`** / JAXB usage in application code; SOAP types **`au.net.electronichealth.*`** come from **`au.gov.nehta:hi-wsdl`** on the classpath (**`hi.wsdl.version`**). The default lifecycle does **not** run **`wsimport`** in this artifact.
- **`wsdls/`** is optional reference and Ant **`wsimport`** material (see **`wsdls/readme.txt`**), not required to compile this library.

## Contributors vs release publisher (`pom.xml`)

**Contributors (PRs, ordinary commits):** Do not change **`<version>`** (stay on **`-SNAPSHOT`** unless the maintainer requests a bump), **`<scm><tag>`**, or **`distributionManagement`**. If a maintainer requests a SNAPSHOT bump on this branch, change only the trailing numbers (**`8.0.1-SNAPSHOT`**), never the Java SE digit. Leave **`maven-gpg-plugin`** **`skip`** **`true`** so default **`mvn verify`** does not require a signing key. Record user-visible work under **`CHANGELOG.md`** in the **`= <pom-version> =`** block that matches **`pom.xml`** **`<version>`**.

**Release publisher:** In the release change set: set **`<version>`** to the GA coordinate (no **`-SNAPSHOT`**); set **`<scm><tag>`** to the Git tag you will publish (match existing tag naming). Move **`CHANGELOG.md`** bullets from the snapshot section into a new **`= <GA-version> =`** section; add a fresh **`-SNAPSHOT`** block for the next development cycle. Deploy via Sonatype Central Portal (**`central-publishing-maven-plugin`**; copy **`settings.xml.example`** -> **`settings.xml`**, server id **`central`**). See **Release** below.

## Release

Publishing uses **`central-publishing-maven-plugin`** (Sonatype Central Portal). Copy **`settings.xml.example`** -> **`settings.xml`**, server id **`central`**.

**Parallel release lines (maintainers only):** each Git branch publishes a **different Maven version** - integrators choose by coordinate, not branch name. The first number of that version is the targeted Java SE version. Run **`release:prepare` / `release:perform`** (or manual deploy) **on that branch** (not detached HEAD).

| Branch (both repos) | Java | `hi-wsdl` / `hi-b2b-client` | Facades |
| ------------------- | ---- | --------------------------- | ------- |
| **`java-8`** | 8 / javax | **8.0.0** | 14 |
| **`java-11`** | 11 / Jakarta | **11.0.0.1** | 26 |
| **`java-17`** | 17 / Jakarta | **17.0.0.1** | 26 |
| **`java-21`** | 21 / Jakarta | **21.0.0.1** | 26 |
| **`java-24`** | 24 / Jakarta | **24.0.0.1** | 26 |

**Order:** publish **`hi-wsdl-java`** first (same branch and GA), then this repo. Client **`verify`** / **`release:perform`** needs **`hi-wsdl`** at that GA on Central (or a prior local **`mvn install`**). Do not reverse the order.

**`-DdevelopmentVersion`:** keep the same first number as **`-DreleaseVersion`** (example on this line: **`8.0.0`** then **`8.0.1-SNAPSHOT`**).

### SNAPSHOT or manual GA

Matching **`hi-wsdl`** at **`${project.version}`** must already resolve (Central GA or local **`mvn install`** from **`hi-wsdl-java`** on the same branch).

1. Update **CHANGELOG.md** (and **`pom.xml`** / SCM **`<tag>`** for manual GA).
2. **`mvn -B "-Prelease" clean verify`**
3. **`mvn -B "-Prelease" deploy`**

Git/SCM settings for **`maven-release-plugin`** live in **`pom.xml`** properties (**`scm.repo.url`**, **`release.*`**). Tags default to **`{artifactId}-{version}`** (e.g. **`hi-b2b-client-8.0.0`**).

### Automated GA (`maven-release-plugin`)

Run on the **target branch** with a **clean** working tree. The plugin commits version bumps, creates the release tag, deploys from the tag checkout, bumps to the next **`-SNAPSHOT`**, and **pushes branch + tag** (**`pushChanges`** / **`remoteTagging`** in **`pom.xml`**). Git remote credentials (SSH or HTTPS) must work non-interactively.

```text
mvn -B "-Prelease" release:prepare release:perform -DreleaseVersion=8.0.0 -DdevelopmentVersion=8.0.1-SNAPSHOT -Dtag=hi-b2b-client-8.0.0
```

Replace **`-DreleaseVersion`**, **`-DdevelopmentVersion`**, and **`-Dtag`** for the branch you are on (same first number; e.g. **`hi-wsdl-11.0.0.1`** / **`hi-b2b-client-11.0.0.1`** on **`java-11`**). Omit **`-D...`** only if you accept interactive prompts.

**After success:** confirm **`hi-wsdl`** GA on Central, then this artifact. No extra Git steps unless push failed; then from the release branch:

`git push origin java-8` (or **`java-11`**, **`java-17`**, **`java-21`**, **`java-24`**) and **`git push origin <tag>`**.

**`-Dgpg.skip=false`** is equivalent to **`-Prelease`** for signing.

## Changelog and releases

**`CHANGELOG.md`** uses **`= version =`** section headers. Match the snapshot header to **`pom.xml`** **`<version>`** until the publisher cuts GA.

## New Java SE line

When adding a line (e.g. Java **25**): create **`java-25`** on **both** **`hi-wsdl-java`** and **`hi-b2b-client-java`** from the nearest existing line; set **`<version>`** first number to **25** (e.g. **`25.0.0.1-SNAPSHOT`**); set **`maven.compiler.release`**, JAX-WS coordinates, CI **`java-version`** / branch filter, and docs to that line. Do not retarget an existing branch.

## Java / JAX stack (`pom.xml` properties)

This subsection is **this `java-8` checkout**. Other lines document their stack in their own **MAINTAINERS** / **`pom.xml`**.

- **`maven.compiler.release` 8** - bytecode and language level for Java 8 consumers.
- **`ee4j.jaxws.version`** - **`com.sun.xml.ws:jaxws-rt`** on **Maven Central** (**2.3.7** is the last **2.3.x** for this branch). Transitive API JARs may use **`jakarta.*`** **groupId** coordinates while still exposing **`javax.*`** packages - do not exclude them from **`jaxws-rt`** in consumer POMs. This project only excludes **`webservices-rt`** from **`common-library`** (old stack conflict with **`jaxws-rt`**).
- **`hi.wsdl.version`** - **`${project.version}`** (same SNAPSHOT or GA as **`hi-b2b-client`**; first number is the Java SE target). Unpublished **`verify`**: **`mvn install`** matching **`hi-wsdl-java`** on the **same branch** first. GA: publish **`hi-wsdl`** then this artifact.
- **`nehta.lib.version`** - **`au.gov.nehta:common-library`** (**8.0.0**; SNAPSHOT while unpublished). **`nehta.xsp.lib.version`** - **`au.gov.nehta:smi-xsp`**. **`smi-common-utils`** is not a direct dependency; **`ArgumentUtils`** and **`CertificateValidator`** come from **`smi-xsp`** / **`common-library`** transitives.

## Default tests

Default Surefire **`<includes>`** (see **`pom.xml`**): **`TimeUtilityTest`**, **`TestConfigurationTest`**, **`HiWsdlArtifactRootTest`**, **`HiRequestElementOrderParityTest`**, **`ConsumerSearchIHIClientArgumentValidatorTest`**. **`-Pintegration`** runs mutual-TLS tests with **`local.properties`** / **`HI_*`**.

## Fast builds

Defaults in **`pom.xml`**: **`maven-source-plugin`** **`jar-no-fork`**, **`maven-javadoc-plugin`** **`jar`** attach (**`maven-javadoc-plugin` 3.12.0** has no **`jar-no-fork`** goal), **`verbose`** **`false`**, **`detectOfflineLinks`** **`false`**, **`maven-compiler-plugin`** **`proc`** **`none`**, **`maven-enforcer-plugin`** **`requireMavenVersion`** **3.6.3+**, **`maven-clean-plugin`** **`retryOnError`** / **`force`**. JaCoCo is **not** bound to the default lifecycle; use **`-Pcoverage`** for **`prepare-agent`** and the **`verify`** report. Optional **`-Pdev-javadoc-off`** skips the Javadoc JAR for faster local **`verify`**. **`fat-jar`** shading drops duplicate **`ArgumentUtils`** from **`smi-xsp`** and **`WebServiceClientUtil`** / **`ArgumentUtils`** from **`common-library`** where this artifact provides its own copies.

## Optional `wsdls/` Ant + `wsimport`

Optional Ant targets under **`wsdls/build.xml`** need a directory of JAX-WS tool/runtime JARs (same coordinates as the root **`pom.xml`**). Populate **`wsdls/local-lib/ee4j-jaxws-from-maven/`** from Central (gitignored) using **`wsdls/ee4j-jaxws-lib-pom.xml`** when present.

Keep **`ee4j.jaxws.version`** in **`wsdls/ee4j-jaxws-lib-pom.xml`** aligned with **`ee4j.jaxws.version`** in the repository root **`pom.xml`**.

## `hi-wsdl` vs `wsdls/`

- Generated SOAP types (`au.net.electronichealth.*`) come from **`au.gov.nehta:hi-wsdl`** at **`hi.wsdl.version`**. **`mvn compile`** does not require a populated **`wsdls/xml/wsdl`** tree.
- **`wsdls/`** holds WSDL/XSD material for reference, diffing, and optional codegen. Inventory and layout are described in **`wsdls/readme.txt`**.

## Maven settings (`settings.xml`)

**`settings.xml.example`** (tracked) defines optional profile **`hi-wsdl-tree`** for parity with other branches. **CI** ( **`.github/workflows/ci.yml`** ) does **not** copy it to a project **`settings.xml`**; use plain **`mvn`** so contributor Maven user settings apply. Root **`build.*`** may pass **`-s`** only when **`MVN_SETTINGS`** is set.

## Integration tests (`-Pintegration`)

The **`integration`** Maven profile widens Surefire to **`**/*Test.java`** and sets **`hi.require.live.config=true`**. Use with **`local.properties`** / **`HI_*`** when running mutual-TLS tests locally or in CI.

## Cleaning `target/`

If **`mvn clean`** or codegen cannot delete **`target/`** (locked files, read-only flags, antivirus, IDE indexers, or cloud-sync folders), close processes that hold the directory, then remove **`target/`** manually.

### Windows

From the repository root, clear read-only flags then delete the folder, for example:

`attrib -R target /S /D`

Then delete the **`target`** directory in File Explorer or with your shell.

### macOS and Linux

From the repository root:

`chmod -R u+w target && rm -rf target`

Avoid relying on build trees inside folders that are continuously synced by desktop sync tools if deletes are flaky.

## Facade surface vs WSDL inventory

Some binding WSDLs under **`wsdls/`** may describe operations whose generated types are not present in a given **`hi-wsdl`** JAR. Those operations are not exposed as **`au.gov.nehta.vendorlibrary.hi.*`** clients until **`hi-wsdl`** (or an alternate codegen path) supplies the SEI types.

## Copyright

Copyright 2011 NEHTA. Copyright 2021-2026 ADHA (Australian Digital Health Agency). Licensed under the Apache License, Version 2.0 - see **LICENSE.md**.
