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

**Release publisher:** Set GA **`<version>`** and **`<scm><tag>`**; move **`CHANGELOG.md`** bullets into a new **`= <GA-version> =`** section. See **Release** below.

## Release

Publishing uses **`central-publishing-maven-plugin`** (Sonatype Central Portal). Copy **`settings.xml.example`** → **`settings.xml`**, server id **`central`**.

**Parallel release lines (maintainers only):** each Git branch publishes a **different Maven version** — integrators choose by coordinate, not branch name.

| Branch | Java | HI client / WSDL version | Facades |
| ------ | ---- | ------------------------ | ------- |
| **`java-8-javax`** → **`master`** | 8 / javax | **1.6.3** | 14 |
| **`java-11-jakarta`** | 11 / Jakarta | **1.6.5** | 14 |
| **`java-11-jakarta-full-wsdl`** | 11 / Jakarta | **1.7.0** | 26 |

Release **`hi-wsdl`** and **`hi-b2b-client`** at the **same GA version** on the matching branch pair before integrators upgrade.

### SNAPSHOT or manual GA

1. Update **CHANGELOG.md** (and **`pom.xml`** / SCM **`<tag>`** for manual GA).
2. **`mvn -B "-Prelease" clean verify`**
3. **`mvn -B "-Prelease" deploy`**

Git/SCM settings for **`maven-release-plugin`** live in **`pom.xml`** properties (**`scm.repo.url`**, **`release.*`**). Tags default to **`{artifactId}-{version}`** (e.g. **`hi-b2b-client-1.7.0`**).

### Automated GA (`maven-release-plugin`)

Run on the **target branch** with a **clean** working tree. The plugin commits version bumps, creates the release tag, deploys from the tag checkout, bumps to the next **`-SNAPSHOT`**, and **pushes branch + tag** (**`pushChanges`** / **`remoteTagging`** in **`pom.xml`**). Git remote credentials (SSH or HTTPS) must work non-interactively.

```text
mvn -B "-Prelease" release:prepare release:perform -DreleaseVersion=1.7.0 -DdevelopmentVersion=1.7.1-SNAPSHOT -Dtag=hi-b2b-client-1.7.0
```

Replace versions and **`-Dtag`** for the branch you are on (**`hi-wsdl-1.6.5`**, **`hi-b2b-client-1.6.3`**, etc.). Omit **`-D…`** only if you accept interactive prompts.

**After success:** confirm the artifact on Central; repeat on the paired types/client repo. No extra Git steps unless push failed (then **`git push origin <branch>`** and **`git push origin <tag>`**).

**`-Dgpg.skip=false`** is equivalent to **`-Prelease`** for signing.

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
