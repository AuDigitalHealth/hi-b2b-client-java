# Maintainer guide (hi-b2b-client-java)

Audience: maintainers changing the build, WSDL layout, dependency coordinates, generated
types, client facades, or tests. Integrators use README.md and published Javadoc.
Contributors start with CONTRIBUTING.md.

This document uses relative paths from the project root (the directory that contains
pom.xml).

---

## 1. What this artifact is

Maven coordinates: au.gov.nehta:hi-b2b-client

Java library for Australia's Healthcare Identifiers (HI) B2B SOAP services (IHI, HPI-I,
HPI-O). It is separate from the My Health Record (MHR / PCEHR) client in the sibling
mhr-b2b-client-java repository. Applications that need both depend on two JARs.

Published artifact: compiled facades and helpers only. Licensed HI WSDL/XSD is never
packaged in the JAR (maven-jar-plugin excludes *.wsdl). Runtime callers must supply the
WSDL tree (see section 6).

---

## 2. Repository layout

| Path | Role |
| ---- | ---- |
| pom.xml | Build, wsimport executions, profiles, dependency versions |
| src/main/java/au/gov/nehta/vendorlibrary/hi/ | Public HI client facades and handlers |
| src/main/java/au/gov/nehta/vendorlibrary/ws/ | Shared JAX-WS helpers (WebServiceClientUtil, handlers) |
| src/main/java/au/gov/nehta/vendorlibrary/hi/wsdl/ | HiWsdlArtifactRoot (runtime WSDL resolution) |
| src/main/java/hi_override/org/w3/xmldsig/ | Hand-maintained XML-DSig types (override generated) |
| src/sample/java/ | Sample programs (sample profile only) |
| src/test/java/ | Unit and integration tests |
| src/test/resources/ | Test keystores (placeholders), JDBC fixtures |
| wsdls/xml/ | Default location for licensed WSDL/XSD (not in Git; see wsdls/readme.txt) |
| wsdls/xml/binding/ | JAX-WS/JAXB binding fragments shipped with the repo |
| target/generated-sources/wsimport/ | Merged wsimport output (build only) |
| local.properties.example | Template for runtime and integration-test configuration |
| settings.xml.example | Optional Maven user-settings template for hi.wsdl.tree.root |
| build.ps1, build.sh, build.bat | Thin wrappers around mvn clean verify |

Branch model: single main line (master). JDK 11, Jakarta XML Web Services (jaxws-rt 4.x).

---

## 3. Client library design

### 3.1 Layering

```
  Application
       |
  au.gov.nehta.vendorlibrary.hi.*Client  (facade: one class per HI WSDL operation set)
       |
  BaseClient_3<T>  extends  ClientBase<T>
       |
  Generated JAX-WS Service / Port types  (au.net.electronichealth.*)
       |
  WebServiceClientUtil  +  HiWsdlArtifactRoot  (port creation, WSDL URL, TLS)
       |
  JAX-WS RI (jaxws-rt) over HTTPS mutual TLS
```

- **ClientBase** holds the JAX-WS port, SSLSocketFactory, signing key/certificate,
  CertificateValidator, and LoggingHandler.
- **BaseClient_3** wires HIHeaderHandler and HISecurityHandler (WS-Security signing via
  smi-xsp), attaches the handler chain, and exposes the port to subclasses.
- Each **facade** (for example ConsumerSearchIHIClient) maps domain-friendly methods to
  generated port operations and HI header types.

Package layout under au.gov.nehta.vendorlibrary.hi:

| Package | Clients |
| ------- | ------- |
| ihi/ | Consumer IHI search, batch, create, update, merge, notify, resolve |
| hpii/ | Provider individual search, directory, TDS, batch async |
| hpio/ | Provider organisation search, manage, read, directory, batch async |
| readreferencedata/ | ReadReferenceDataClient |

There are 26 wsimport executions and a matching set of facade classes (one primary WSDL
each). The MCA tree under wsdls/xml/wsdl can contain more WSDLs than this JAR exposes;
adding wsimport plus a public facade is a deliberate release change.

### 3.2 Runtime requirements (integrator view; relevant when changing facades)

Every call expects the application to provide:

- HTTPS endpoint URL (per operation)
- SSLSocketFactory for mutual TLS
- X509 certificate and private key for SOAP message signing
- CertificateValidator (smi-xsp) for verifying inbound signatures
- HI message headers (product, user, organisation context) via generated types

WSDL resolution at runtime uses HiWsdlArtifactRoot (not Maven hi.wsdl.tree.root). See
section 6.

### 3.3 Compile/runtime dependencies (published JAR)

| Dependency | Purpose |
| ---------- | ------- |
| au.gov.nehta:smi-xsp | XML-DSig, certificate validation, HI security utilities |
| com.sun.xml.ws:jaxws-rt | Jakarta XML Web Services reference implementation |

Test-only: junit, h2 (JDBC test rule). Groovy is build-time only (gmavenplus-plugin).

---

## 4. Build process design

### 4.1 Goals

1. Generate Jakarta JAX-WS/JAXB types from licensed HI WSDL under hi.wsdl.tree.root.
2. Merge per-WSDL outputs into one compile source tree without duplicate types.
3. Compile hand-written facades plus generated types into hi-b2b-client.jar.
4. Exclude WSDL from the published JAR.
5. Default test run: offline unit tests only; full mutual-TLS suite via -Pintegration.

### 4.2 Lifecycle (Maven phases)

```
initialize
  gmavenplus: configure-hi-wsdl-tree-root  -> sets hi.wsdl.tree.root
  enforcer: require-licensed-wsdl-tree     -> sentinel WSDL must exist (unless skip)

generate-sources
  gmavenplus: clean-wsimport-codegen       -> optional wipe when hi.wsdl.codegen.clean=true
  jaxws-maven-plugin: 26 x wsimport        -> target/generated-sources/wsimport-parts/<id>/

process-sources
  gmavenplus: merge-and-strip-wsimport     -> target/generated-sources/wsimport/
  build-helper: add-wsimport-source

compile / test / package
  compiler, surefire, jar (WSDL excluded), optional gpg/javadoc/source attachments
```

**Merge rule:** Groovy script copies each wsimport-parts subtree into
target/generated-sources/wsimport/; first file wins on name clash. Then removes
wsimport-parts paths from compile source roots.

**Stale detection:** Each wsimport execution has its own staleFile under target/jaxws/.

### 4.3 Key Maven properties (pom.xml)

| Property | Default / role |
| -------- | -------------- |
| maven.compiler.release | 11 |
| jaxws.rt.version | jaxws-rt and jaxws-tools version (4.0.4) |
| jaxws.maven.plugin.version | jaxws-maven-plugin (4.0.2 on JDK 11; 4.0.3+ may need JDK 17 to run wsimport) |
| groovy.version | gmavenplus-plugin script runtime only |
| hi.wsdl.codegen.skip | false; true skips wsimport and enforcer |
| hi.wsdl.codegen.clean | false; true deletes prior wsimport output before codegen |
| hi.wsdl.tree.root | Build-time WSDL root; resolved in initialize (see section 6) |
| maven.shade.plugin.version | fat-jar profile |
| skipTests | false; default surefire includes are offline tests only |

### 4.4 wsimport configuration

- Plugin: com.sun.xml.ws:jaxws-maven-plugin (not legacy Metro webservices-rt).
- xnocompile true, extension true, -XautoNameResolution.
- vmArg -Djavax.xml.accessExternalSchema=all for schema resolution.
- WSDL paths under ${hi.wsdl.tree.root}/wsdl/... per execution in pom.xml.
- Optional binding files under wsdls/xml/binding/ where configured.

### 4.5 Profiles

| Profile | Effect |
| ------- | ------ |
| integration | Surefire runs **/*Test.java (needs local.properties / HI_* and certs) |
| sample | Adds src/sample/java at generate-sources |
| fat-jar | maven-shade-plugin produces hi-b2b-client-*-all.jar with runtime deps shaded |

Build wrappers (build.ps1 etc.): mvn -B -Dgpg.skip=true clean verify; pass shaded for
fat-jar.

### 4.6 Optional Ant under wsdls/

Default build is Maven-only. Legacy Ant under wsdls/ expects EE4J jars copied to
wsdls/local-lib/ee4j-jaxws-from-maven/ via wsdls/ee4j-jaxws-lib-pom.xml. Keep
ee4j.jaxws.version aligned with jaxws.rt.version if you use Ant.

---

## 5. External inputs and local configuration

Nothing in this section belongs in Git when populated.

### 5.1 Licensed WSDL tree (build time)

**Property:** hi.wsdl.tree.root

**Layout:** immediate children wsdl/ and schema/ (same layout as wsdls/xml/ after
extracting the ADHA vendor bundle). See wsdls/readme.txt for download and inventory.

**Resolution order (first match):**

1. -Dhi.wsdl.tree.root=...
2. Environment variable HI_WSDL_TREE_ROOT
3. hi.wsdl.tree.root from an active Maven settings.xml profile
4. Default: wsdls/xml

Maven never reads HI_WSDL_ARTIFACT_ROOT.

**Skip codegen:** -Dhi.wsdl.codegen.skip=true (compile still needs an existing
target/generated-sources/wsimport from a prior full build).

### 5.2 Licensed WSDL tree (runtime and integration tests)

**Class:** au.gov.nehta.vendorlibrary.hi.wsdl.HiWsdlArtifactRoot

**Key:** HI_WSDL_ARTIFACT_ROOT

**Layout:** same wsdl/ and schema/ as build tree.

**Resolution order:**

1. HiWsdlArtifactRoot.setRoot(Path)
2. Environment HI_WSDL_ARTIFACT_ROOT
3. HI_WSDL_ARTIFACT_ROOT in local.properties (JVM user.dir)
4. System property -DHI_WSDL_ARTIFACT_ROOT=...

WebServiceClientUtil resolves @WebServiceClient wsdlLocation filenames by searching under
wsdl/ beneath that root.

### 5.3 local.properties

Copy local.properties.example to local.properties beside the JVM working directory
(typically project root when running tests).

Used for:

- HI_WSDL_ARTIFACT_ROOT (runtime WSDL path; often ./wsdls/xml in a checkout)
- HI_KEYSTORE_*, HI_KEY_ALIAS_*, HI_TRUSTSTORE_* (mutual TLS and signing for
  -Pintegration tests)
- HI_MEDICARE_ENDPOINT_BASE and per-service endpoint overrides
- HI_USER_*, HI_VENDOR_*, HI_HPIO_* qualified identifiers for test messages
- HI_PRODUCT_* strings

**Test resolution order** (TestConfiguration): environment variable overrides
local.properties overrides compiled default in test constants.

Gitignored: local.properties, certs/, populated keystores, config/ with secrets.

### 5.4 settings.xml

Copy settings.xml.example to settings.xml (or merge the hi-wsdl-tree profile into
~/.m2/settings.xml). Sets hi.wsdl.tree.root for builds that rely on a settings file.

Treat populated settings.xml like local.properties: do not commit secrets or machine-only
paths. Optional env MVN_SETTINGS points build scripts at a settings file.

### 5.5 Test keystores

src/test/resources/keystore.jks and truststore.jks are placeholders for offline tests.
Integration tests should use PKCS#12 or JKS material from your ADHA registration pack
under certs/ (gitignored) via local.properties paths.

---

## 6. Tests (maintainer view)

| Command | Scope |
| ------- | ----- |
| mvn -B "-Dgpg.skip=true" test | Default offline unit tests (see pom.xml surefire includes) |
| mvn -B "-Dgpg.skip=true" -Pintegration test | All *Test.java; requires HI network, local.properties, certs |

Default includes: TimeUtilityTest, TestConfigurationTest, JdbcTransactionTestRuleTest,
HiWsdlArtifactRootTest, ConsumerSearchIHIClientArgumentValidatorTest.

HiWsdlArtifactRootTest can skip reading local.properties when HI_WSDL_ARTIFACT_ROOT is
set in the environment.

After mvn clean, run a full compile before relying on -Dhi.wsdl.codegen.skip=true.

---

## 7. Common maintainer tasks

**Add a new HI WSDL and facade**

1. Confirm WSDL exists under ${hi.wsdl.tree.root}.
2. Add a jaxws-maven-plugin execution in pom.xml (destDir, sourceDestDir, wsdlDirectory,
   wsdlFiles, wsdlLocation).
3. Add the execution id to the merge names list in gmavenplus merge-and-strip-wsimport.
4. Implement facade under au.gov.nehta.vendorlibrary.hi extending BaseClient_3.
5. Add integration test and/or argument validator unit test as appropriate.

**Bump jaxws-rt**

1. Update jaxws.rt.version in pom.xml.
2. Full mvn clean verify with licensed tree present.
3. If using wsdls/ Ant helper, align wsdls/ee4j-jaxws-lib-pom.xml.

**Refresh licensed WSDL from ADHA**

1. Extract vendor bundle into wsdls/xml (or your hi.wsdl.tree.root).
2. mvn clean verify; fix binding or merge clashes if wsimport fails.
3. Re-run -Pintegration when endpoints and certs are configured.

**target/ locked on Windows**

Close IDE Java language server and antivirus holders. maven-clean-plugin uses force and
retryOnError. See CONTRIBUTING.md for manual delete steps.

---

## 8. Sibling project alignment

Keep plugin and shared dependency versions aligned with mhr-b2b-client-java where both
use the same stack (jaxws-rt, jaxws-maven-plugin, gmavenplus, surefire, compiler, shade).
MHR ships PCEHR WSDL in Git; HI does not ship HI WSDL in Git.

---

## Copyright

Copyright 2011 NEHTA. Copyright 2021-2026 ADHA (Australian Digital Health Agency).
Licensed under the Apache License, Version 2.0. See LICENSE.txt.
