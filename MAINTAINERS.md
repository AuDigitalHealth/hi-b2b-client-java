# Maintainer notes

**Audience:** people changing the build, dependency coordinates, tests, or WSDL layout - not library integrators. Integrators should use **README.md**, published Javadoc, and **`pom.xml`** coordinates.

## Branch scope (Java 11 migration)

- **Goal:** run the former Java **8** client on **Java 11** with minimal dependencies and only necessary dependency upgrades.
- **WSDL/XSD in Git:** **`wsdls/xml/wsdl/`** and **`wsdls/xml/schema/`** are tracked in this branch; **`.github/workflows/ci.yml`** and a clean clone use **`hi.wsdl.tree.root`** default **`${project.basedir}/wsdls/xml`**.
- **Overrides:** **`-Dhi.wsdl.tree.root`**, **`HI_WSDL_TREE_ROOT`**, or user **`settings.xml`** keep Maven invocations aligned with **other branches** that store WSDL outside the repository.
- **API surface:** avoid adding or removing behaviour except targeted bug fixes; WSDLs may describe operations not wrapped by **`au.gov.nehta.vendorlibrary.hi.*`** - leave that gap as-is.

## Contributors vs release publisher (`pom.xml`)

**Contributors (PRs, ordinary commits):** Do not change **`<version>`** (stay on **`-SNAPSHOT`** unless the maintainer requests a bump), **`<scm><tag>`**, or **`distributionManagement`**. Leave **`maven-gpg-plugin`** **`skip`** **`true`** so default **`mvn verify`** does not require a signing key. Record user-visible work under **`CHANGELOG.md`** in the **`= <pom-version> =`** block that matches **`pom.xml`** **`<version>`**.

**Release publisher:** In the release change set: set **`<version>`** to the GA coordinate (no **`-SNAPSHOT`**); set **`<scm><tag>`** to the Git tag you will publish (match existing tag naming, e.g. **`hi-b2b-client-1.6.3`**). Move **`CHANGELOG.md`** bullets from the snapshot section into a new **`= <GA-version> =`** section; add a fresh **`…-SNAPSHOT`** block for the next development cycle. Use GPG and **`~/.m2/settings.xml`** **`<server>`** credentials for **`nexus-releases`** / **`nexus-snapshots`**, then **`mvn deploy`** or **`maven-release-plugin`** (**`release:prepare`** / **`release:perform`**) per organisation policy.

## Changelog and releases

**`CHANGELOG.md`** uses **`= version =`** section headers (not **`## [Unreleased]`**). Match the snapshot header to **`pom.xml`** **`<version>`** until the publisher cuts GA.

## Java / Jakarta stack (`pom.xml` properties)

- **`maven.compiler.release` 11** - language level and bytecode for this branch.
- **`jaxws.rt.version`** - Eclipse EE4J **`com.sun.xml.ws:jaxws-rt`** and the **`jaxws-tools`** version passed into **`jaxws-maven-plugin`** (Maven Central only). **`jaxws.maven.plugin.version`** is the **`jaxws-maven-plugin`** artifact; **`4.0.4`** declares required Java **17** for the plugin (fails on a JDK **11** build), so this branch keeps **`4.0.2`** while **`jaxws.rt.version`** / **`jaxws-tools`** track **`4.0.4`**. **`jakarta.xml.ws-api`**, **`jakarta.xml.bind-api`**, and **`jakarta.xml.soap-api`** are declared explicitly (versions **`jakarta.xml.ws-api.version`**, **`jakarta.xml.bind-api.version`**, **`jakarta.xml.soap-api.version`** in **`pom.xml`**) so compile use matches the **`jaxws-rt`** line; keep them aligned when bumping **`jaxws.rt.version`**.
- **`jaxb.xjc.version`** (**`4.0.7`**) - pinned on **`jaxws-maven-plugin`** as **`jaxb-xjc`** so schema-fragment Javadoc from **`wsimport`** is valid (older XJC emits broken **`<pre>`** blocks).
- **`groovy.version`** - **`groovy`** only for **`gmavenplus-plugin`** build scripts (not published with the library JAR).
- **`maven-compiler-plugin`** uses **`proc`** **`none`** (no annotation processors in this tree; change if you add one). **`maven-javadoc-plugin`** uses **`doclint`** **`none`** and **`detectOfflineLinks`** **`false`** for a faster **`verify`** (weaker automatic **`{@link}`** validation).
- **`hi.wsdl.codegen.skip`** - set **`true`** to skip **`wsimport`** and the **`maven-enforcer-plugin`** WSDL sentinel when iterating on non-SOAP code (requires an existing merged generated tree under **`target/generated-sources/wsimport`** for a successful compile).
- **`hi.wsdl.tree.root`** - build-time directory whose children are **`wsdl/`** and **`schema/`**. The first script of the **`gmavenplus-plugin`** **`initialize`** execution sets it from **`-Dhi.wsdl.tree.root`**, **`HI_WSDL_TREE_ROOT`**, active **`settings.xml`** profile property, or default **`${project.basedir}/wsdls/xml`**. Runtime **`HI_WSDL_ARTIFACT_ROOT`** is not used by Maven.

## Generated SOAP types (`wsimport`)

- Types under **`au.net.electronichealth.*`** are generated in the default lifecycle by **`jaxws-maven-plugin`** from WSDLs under **`${hi.wsdl.tree.root}/wsdl/...`**, merged into **`target/generated-sources/wsimport`**, and compiled from there (**`build-helper-maven-plugin`** adds that source root).
- **`maven-jar-plugin`** excludes **`*.wsdl`** from the published JAR; integrators supply WSDL at runtime (**`HiWsdlArtifactRoot`**) or on the classpath.
- **`gmavenplus-plugin`**: **`initialize`** uses one **`execute`** invocation with two scripts (resolve **`hi.wsdl.tree.root`**, then delete previous **`wsimport`** / **`wsimport-parts`** / **`jaxws`** trees under **`target/`**). **`process-sources`** merges each **`wsimport-parts`** tree into **`target/generated-sources/wsimport`** (first writer wins), strips **`wsimport-parts`** compile source roots, then deletes the **`wsimport-parts`** directory so Javadoc does not see duplicate **`package-info.java`** files.
- **`maven-enforcer-plugin`** (**`initialize`**) requires **`${hi.wsdl.tree.root}/wsdl/consumer/20100731/HI_ConsumerSearchIHI-3.0.wsdl`** unless **`hi.wsdl.codegen.skip`** is **`true`**.

## Fast builds

Defaults in **`pom.xml`**: **`jaxws-maven-plugin`** **`quiet`** **`true`**, **`maven-source-plugin`** **`jar-no-fork`**, **`maven-javadoc-plugin`** **`detectOfflineLinks`** **`false`**, **`maven-compiler-plugin`** **`proc`** **`none`**, **`gmavenplus-plugin`** single **`initialize`** **`execute`**. **`maven-clean-plugin`** uses **`force`** and **`retryOnError`** ( **`fast`** stays **`false`** for reliable **`wsimport`** output dirs after **`clean`** on Windows). Optional profile **`dev-javadoc-off`** sets **`maven.javadoc.skip`** **`true`** for a faster local **`verify`**. CLI-only: **`-DskipTests=true`**, **`-Dhi.wsdl.codegen.skip=true`** only when **`target/generated-sources/wsimport`** is already populated from a prior **`wsimport`** run.

## `sample` profile

- **`sample` profile** adds **`src/sample/java`** as a compile source root (**`build-helper-maven-plugin`**, **`generate-sources`**). Use **`mvn -Psample clean compile`** (often with **`-DskipTests=true`**) to type-check sample programs; same profile is used in **`.github/workflows/ci.yml`**.

## Optional Ant under **`wsdls/`**

The default build uses root **Maven** only. If you still invoke Ant under **`wsdls/`**, copy EE4J **`jaxws-tools`** / **`jaxws-rt`** (and transitives) into **`wsdls/local-lib/ee4j-jaxws-from-maven/`** (gitignored):

```text
mvn -f wsdls/ee4j-jaxws-lib-pom.xml package
```

Keep **`ee4j.jaxws.version`** in **`wsdls/ee4j-jaxws-lib-pom.xml`** aligned with **`jaxws.rt.version`** in the root **`pom.xml`**.

## `wsdls/xml/wsdl` vs shipped clients

- The MCA tree under **`wsdls/xml/wsdl`** can contain more WSDLs than this artifact exposes as **`au.gov.nehta.vendorlibrary.hi.*`** facade classes. Extending **`wsimport`** and the public API belongs on a dedicated change set or release.
- **`wsdls/readme.txt`** - ADHA download pointer, a **full inventory** of tracked files under **`wsdls/xml/wsdl/`** and **`wsdls/xml/schema/`** (paths relative to **`xml/`**), and a short **Maven** footer for this repository.

## Copyright

Copyright 2011 NEHTA. Copyright 2021-2026 ADHA (Australian Digital Health Agency). Licensed under the Apache License, Version 2.0 - see **LICENSE.txt**.
