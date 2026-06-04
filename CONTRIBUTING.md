# Contributing

**Audience:** people submitting patches or running this repository's CI locally - not day-to-day integrators of the published JAR (they should start with **README.md**).

- **Build:** **JDK 8** and **Apache Maven 3.6.3+** on your **`PATH`** ( **`maven-enforcer-plugin`** enforces the minimum). Maven goals are the same on **Windows**, **macOS**, and **Linux**. From the repo root: **`mvn -B -Dgpg.skip=true clean verify`** runs the default offline Surefire set (see **README.md**). Optional faster local **`verify`** without the Javadoc JAR: **`mvn -B -Pdev-javadoc-off -Dgpg.skip=true clean verify`**. JaCoCo: **`mvn -B -Pcoverage -Dgpg.skip=true clean verify`**. **`mvn -B -Pintegration -Dgpg.skip=true clean test`** with **`local.properties`** / **`HI_*`** for mutual-TLS integration suites. **`mvn -B -Psample -Dgpg.skip=true -DskipTests=true clean compile`** type-checks **`src/sample/java`**. Shaded JAR (classifier **`all`**): **`mvn -B -Pfat-jar -Dgpg.skip=true clean verify`**. Optional **`./build.sh`**, **`build.ps1`**, **`build.bat`**: same **`verify`**; set **`MVN_SETTINGS`** only if you need **`mvn -s`**. **`settings.xml.example`** is optional - see its header.
- **Contributors vs release publisher (`pom.xml`):** For normal commits and PRs, **do not** change **`<version>`**, **`<scm><tag>`**, or **`distributionManagement`**, and leave **`maven-gpg-plugin`** **`skip`** at **`true`**. The release publisher follows **MAINTAINERS.md** (version bump, **`CHANGELOG.md`**, GPG, staging servers, tag) in a dedicated release process.
- **Public hosting:** Treat the repo as publishable to a public remote only after **README.md** -> **Publishing and public hosting**, **SECURITY.md**, and **CI** goals are satisfied; do not commit secrets or keystores.
- **Dependencies:** The library and its runtime stack resolve from **Maven Central** only - see **README.md** -> **Dependencies**. No separate vendor SOAP stack ZIP is required for **`mvn compile`**.
- **CI:** **`.github/workflows/ci.yml`** - **`mvn -B -Dgpg.skip=true clean verify`** on **Temurin 8**, then **`mvn -B -Psample -Dgpg.skip=true -DskipTests=true clean compile`** (no project **`settings.xml`** copy).
- **Do not commit to git:** **`local.properties`**, a personal **`settings.xml`** at the repo root if it contains secrets, real keystores, passwords, API tokens, private keys, or environment-specific URLs for production HI access. See **`SECURITY.md`**.
- **Line endings:** the repository uses **LF** (see **`.gitattributes`**). Configure Git so you do not re-normalise to CRLF on commit:
  - **Windows:** in your clone, **`git config core.autocrlf false`** before committing.
  - **macOS and Linux:** default Git settings are usually fine; avoid forcing **`core.autocrlf true`** for this repo.
- **Changes:** Keep commits focused; match existing style and **`pom.xml`** conventions.
- **Internals:** **`hi-wsdl`** alignment, WSDL test semantics, and Maven property rationale - **MAINTAINERS.md**.
