# Maintainer notes

**Audience:** people changing the build, dependency coordinates, tests, or WSDL layout - not library integrators. Integrators should use **README.md**, published Javadoc, and **`pom.xml`** coordinates.

## Branch scope (Java 8 / `javax`)

- **Goal:** Java **8** bytecode (`maven.compiler.release` **8**) with **`javax.xml.ws`** / JAXB usage in application code; SOAP types **`au.net.electronichealth.*`** come from **`au.gov.nehta:hi-wsdl`** on the classpath ( **`hi.wsdl.version`** ). The default lifecycle does **not** run **`wsimport`** in this artifact.
- **`wsdls/`** is optional reference and Ant **`wsimport`** material (see **`wsdls/readme.txt`**), not required to compile this library.

## Contributors vs release publisher (`pom.xml`)

**Contributors (PRs, ordinary commits):** Do not change **`<version>`** (stay on **`-SNAPSHOT`** unless the maintainer requests a bump), **`<scm><tag>`**, or **`distributionManagement`**. Leave **`maven-gpg-plugin`** **`skip`** **`true`** so default **`mvn verify`** does not require a signing key. Record user-visible work under **`CHANGELOG.md`** in the **`= <pom-version> =`** block that matches **`pom.xml`** **`<version>`**.

**Release publisher:** In the release change set: set **`<version>`** to the GA coordinate (no **`-SNAPSHOT`**); set **`<scm><tag>`** to the Git tag you will publish (match existing tag naming). Move **`CHANGELOG.md`** bullets from the snapshot section into a new **`= <GA-version> =`** section; add a fresh **`-SNAPSHOT`** block for the next development cycle. Use GPG and **`~/.m2/settings.xml`** **`<server>`** credentials for **`nexus-releases`** / **`nexus-snapshots`**, then **`mvn deploy`** or **`maven-release-plugin`** (**`release:prepare`** / **`release:perform`**) per organisation policy.

## Changelog and releases

**`CHANGELOG.md`** uses **`= version =`** section headers. Match the snapshot header to **`pom.xml`** **`<version>`** until the publisher cuts GA.

## Java / JAX stack (`pom.xml` properties)

- **`maven.compiler.release` 8** - bytecode and language level for Java 8 consumers.
- **`ee4j.jaxws.version`** - **`com.sun.xml.ws:jaxws-rt`** on **Maven Central** (**2.3.7** is the last **2.3.x** for this branch). Transitive API JARs may use **`jakarta.*`** **groupId** coordinates while still exposing **`javax.*`** packages - do not exclude them from **`jaxws-rt`** in consumer POMs. This project only excludes **`webservices-rt`** from **`common-library`** (old stack conflict with **`jaxws-rt`**).
- **`hi.wsdl.version`** - **`au.gov.nehta:hi-wsdl`** for generated SOAP types.
- **`nehta.lib.version`** - **`au.gov.nehta:common-library`** and explicit **`au.gov.nehta:smi-xsp`** ( **`smi-common-utils`** removed; **`ArgumentUtils`** and **`CertificateValidator`** come from **`smi-xsp`** / **`common-library`** transitives).

## Fast builds

Defaults in **`pom.xml`**: **`maven-source-plugin`** **`jar-no-fork`**, **`maven-javadoc-plugin`** **`jar`** attach (**`maven-javadoc-plugin` 3.12.0** has no **`jar-no-fork`** goal), **`verbose`** **`false`**, **`detectOfflineLinks`** **`false`**, **`maven-compiler-plugin`** **`proc`** **`none`**, **`maven-enforcer-plugin`** **`requireMavenVersion`** **3.6.3+**, **`maven-clean-plugin`** **`retryOnError`** / **`force`**. JaCoCo is **not** bound to the default lifecycle; use **`-Pcoverage`** for **`prepare-agent`** and the **`verify`** report. Optional **`-Pdev-javadoc-off`** skips the Javadoc JAR for faster local **`verify`**. **`fat-jar`** shading drops duplicate **`ArgumentUtils`** from **`smi-xsp`** and **`WebServiceClientUtil`** / **`ArgumentUtils`** from **`common-library`** where this artifact provides its own copies.

## Optional `wsdls/` Ant + `wsimport`

Optional Ant targets under **`wsdls/build.xml`** need a directory of JAX-WS tool/runtime JARs (same coordinates as the root **`pom.xml`**). Populate **`wsdls/local-lib/ee4j-jaxws-from-maven/`** from Central (gitignored) using **`wsdls/ee4j-jaxws-lib-pom.xml`** when present.

Keep **`ee4j.jaxws.version`** in **`wsdls/ee4j-jaxws-lib-pom.xml`** aligned with **`ee4j.jaxws.version`** in the repository root **`pom.xml`**.

## `hi-wsdl` vs `wsdls/`

- Generated SOAP types (`au.net.electronichealth.*`) come from **`au.gov.nehta:hi-wsdl`** at **`hi.wsdl.version`**. **`mvn compile`** does not require a populated **`wsdls/xml/wsdl`** tree.
- **`wsdls/`** holds WSDL/XSD material for reference, diffing, and optional codegen. Inventory and layout are described in **`wsdls/readme.txt`**.

## Maven settings (`settings.xml`)

**`settings.xml.example`** (tracked) defines optional profile **`hi-wsdl-tree`** for parity with other branches. **CI** ( **`.github/workflows/ci.yml`** ) does **not** copy it to a project **`settings.xml`**; use plain **`mvn`** so contributor **`~/.m2/settings.xml`** applies. Root **`build.*`** may pass **`-s`** only when **`MVN_SETTINGS`** is set.

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

Copyright 2011 NEHTA. Copyright 2021-2026 ADHA (Australian Digital Health Agency). Licensed under the Apache License, Version 2.0 - see **LICENSE.txt**.
