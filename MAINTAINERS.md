# Maintainer notes

**Audience:** people changing the build, dependency coordinates, tests, or WSDL layout — not library integrators. Integrators should use **README.md**, published Javadoc, and Maven Central coordinates.

## Release lines

| Version | Java | APIs | `Service` stubs |
| ------- | ---- | ---- | ----------------- |
| **1.6.3** | 8 | **`javax.xml.ws`**, **`javax.xml.bind`** | **14** (standard HI B2B) |
| **1.6.5** | 11 | **Jakarta** XML WS / Bind | **14** (standard HI B2B) |
| **1.7.0** | 11 | **Jakarta** XML WS / Bind | **26** (full MCA) |

**Git branch mapping (maintainers / checkout only — do not use in integrator docs):**

| Version | Git branch |
| ------- | ---------- |
| **1.6.3** | `java-8-javax` |
| **1.6.5** | `java-11-jakarta` |
| **1.7.0** | `java-11-jakarta-full-wsdl` |

**`master`** is frozen and must stay in sync with **`origin/master`** — do not edit; use **`git worktree`** locally.

**This checkout:** **`1.6.5-SNAPSHOT`** (**`1.6.5`** release line).

## Release scope (`1.6.5`)

- **Goal:** deliver the standard **14**-stub HI B2B client on **Java 11** with **Jakarta** XML WS / Bind and minimal dependencies.
- **SOAP types:** **`au.gov.nehta:hi-wsdl`** at **`hi.wsdl.version`** in **`pom.xml`** (published to Maven Central with the Jakarta line). This repository does **not** run in-repo **`wsimport`**.
- **Licensed WSDL/XSD:** not shipped in the published **`hi-b2b-client`** JAR. Integrators supply the ADHA bundle at runtime via **`HiWsdlArtifactRoot`** (**`HI_WSDL_ARTIFACT_ROOT`**).
- **API surface:** **14** standard HI B2B facade classes under **`au.gov.nehta.vendorlibrary.hi.*`** (**1.6.5** line).

## Contributors vs release publisher (`pom.xml`)

**Contributors:** Do not change **`<version>`**, **`<scm><tag>`**, or **`distributionManagement`** without maintainer direction. Leave **`maven-gpg-plugin`** **`skip`** **`true`** for default **`mvn verify`**. Record user-visible work under **`CHANGELOG.md`** in the **`= <pom-version> =`** block.

**Release publisher:** Set GA **`<version>`** and **`<scm><tag>`**; move **`CHANGELOG.md`** bullets into a new **`= <GA-version> =`** section; deploy via Sonatype staging per organisation policy.

## Java / Jakarta stack (`pom.xml` properties)

- **`maven.compiler.release` 11**
- **`jaxws.rt.version`** — **`jaxws-rt` 4.0.4** (Maven Central)
- **`hi.wsdl.version`** — **`${project.version}`** (same SNAPSHOT or GA as **`hi-b2b-client`**); **`mvn install`** in **`hi-wsdl-java`** at the matching version before unpublished **`verify`**; coordinate GA releases with **`hi-wsdl-java`**
- **`maven-javadoc-plugin`** — **`doclint`** **`none`**, **`detectOfflineLinks`** **`false`**
- **`maven-enforcer-plugin`** — bans legacy Metro and **`javax.xml.ws` / `javax.xml.bind`**

## Runtime WSDL resolution

- **`HiWsdlArtifactRoot`** + **`HI_WSDL_ARTIFACT_ROOT`** / **`local.properties`** / **`setRoot(Path)`** in **`WebServiceClientUtil`**
- **`maven-jar-plugin`** excludes **`*.wsdl`** from **`hi-b2b-client`**

## Copyright

Copyright 2011 NEHTA. Copyright 2021-2026 ADHA. Licensed under the Apache License, Version 2.0 — see **LICENSE.md**.
