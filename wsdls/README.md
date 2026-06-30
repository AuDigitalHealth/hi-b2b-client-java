# Licensed WSDL and XSD

Healthcare Identifier WSDL and XSD files are licensed material from Services Australia / ADHA and are **not** shipped in this Git repository. Download them from:

https://healthsoftware.humanservices.gov.au/claiming/ext-vnd/

Extract the bundle to a directory whose immediate children are lowercase **`wsdl/`** and **`schema/`**. Some hosts and CI runners are case-sensitive, so the casing matters. Optional JAX-WS / JAXB binding fragments that this repository does ship live under **`wsdls/xml/binding/`**.

See [Configuring the location](#configuring-the-location) below. For building this repository see **CONTRIBUTING.md**; for runtime use of the library see **README.md** at the repository root.

---

## Configuring the location

There are **two** separate settings. They use **different** names because they cover different lifecycles, and you can point them at the **same** directory or at different directories.

| Property | When used |
| -------- | --------- |
| **`hi.wsdl.tree.root`** | Optional; legacy Ant tooling under **`wsdls/`** only |
| **`HI_WSDL_ARTIFACT_ROOT`** | Runtime (published library, **`HiWsdlArtifactRoot`**, integration tests) |

The default Maven lifecycle (**`mvn verify`**) compiles against **`au.gov.nehta:hi-wsdl`** from Maven Central (or a locally installed SNAPSHOT). It does **not** run **`wsimport`** and does **not** read **`hi.wsdl.tree.root`** or **`HI_WSDL_ARTIFACT_ROOT`**. The library at runtime never reads **`hi.wsdl.tree.root`**.

### Optional: `hi.wsdl.tree.root` (legacy Ant only)

Used only by optional Ant helpers under **`wsdls/`** (**`build.xml`** and related scripts) if you run them locally. Not used by **`mvn compile`**, **`test`**, **`verify`**, **`package`**, or **`install`** on the **`1.6.5`** line.

If set, the value must be a directory whose immediate children are **`wsdl/`** and **`schema/`**. Resolution order (first match wins):

1. **`-Dhi.wsdl.tree.root=<path>`** — CLI user property; quote on PowerShell if the path has spaces.
2. **`HI_WSDL_TREE_ROOT`** — environment variable.
3. **`hi.wsdl.tree.root`** in an active Maven **`settings.xml`** profile — e.g. merge the **`hi-wsdl-tree`** profile from **`settings.xml.example`** into your Maven user settings file.
4. **Default** — **`<project.basedir>/wsdls/xml`**

The published **`hi-b2b-client`** JAR excludes **`*.wsdl`** (**`maven-jar-plugin`**), so the licensed tree is never redistributed inside the client artifact.

### Runtime: `HI_WSDL_ARTIFACT_ROOT`

Used by **`au.gov.nehta.vendorlibrary.hi.wsdl.HiWsdlArtifactRoot`** when the published library resolves **`@WebServiceClient`** **`wsdlLocation`** values at runtime. Integration tests in this repo (**`mvn -Pintegration test`**) also read this. The value must be a directory whose immediate children are **`wsdl/`** and **`schema/`**.

Resolution order (first match wins):

1. **`HiWsdlArtifactRoot.setRoot(Path)`** — programmatic; highest precedence.
2. **`HI_WSDL_ARTIFACT_ROOT`** — process environment variable.
3. **`HI_WSDL_ARTIFACT_ROOT`** — key in **`local.properties`** in the JVM working directory (typically next to **`pom.xml`** in a checkout, or your application's working directory in production). Re-read on each resolve.
4. **`HI_WSDL_ARTIFACT_ROOT`** — JVM system property (**`-D...`**).

**`local.properties`** is gitignored; see **`local.properties.example`** for the documented keys (including **`HI_WSDL_ARTIFACT_ROOT`**). Prefer forward slashes in property values for cross-platform behaviour.

---

## Template files

Two template files ship in Git at the project root, beside **`pom.xml`**:

| File | Purpose |
| ---- | ------- |
| **`settings.xml.example`** | Optional Maven user settings fragment; documents **`hi.wsdl.tree.root`** for legacy Ant tooling parity. Not required for **`mvn verify`** on the **`1.6.5`** line. |
| **`local.properties.example`** | Runtime / test configuration template; documents **`HI_WSDL_ARTIFACT_ROOT`** and the **`HI_*`** keys (keystore, truststore, endpoints, product / vendor identifiers) used by integration tests and by **`HiWsdlArtifactRoot`**. |

Both files are **templates**. Copy and edit locally; do not commit populated copies:

```text
settings.xml.example    ->  settings.xml      (optional; gitignored if created at repo root)
local.properties.example ->  local.properties
```

**`local.properties`** is gitignored. Do **not** commit **`settings.xml`**, **`local.properties`**, keystores, passwords, tokens, or production endpoint URLs to this repository. For CI and production use repository secrets, a vault, or per-machine environment variables.

### Which setting applies to you

- **Library users** (applications depending on **`au.gov.nehta:hi-b2b-client`** from Maven Central or another repository) set **`HI_WSDL_ARTIFACT_ROOT`** only.

- **Contributors** running **`mvn verify`** need only JDK **11**, Maven, and the matching **`hi-wsdl`** artifact at **`${project.version}`** (see **Local builds** in **CONTRIBUTING.md** for unpublished SNAPSHOTs). Set **`HI_WSDL_ARTIFACT_ROOT`** only when running integration tests (**`mvn -Pintegration test`**).
