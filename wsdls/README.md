# Licensed WSDL and XSD

Healthcare Identifier WSDL and XSD files are licensed material from Services Australia / ADHA and are **not** shipped in this Git repository. Download them from:

https://healthsoftware.humanservices.gov.au/claiming/ext-vnd/

Extract the bundle to a directory whose immediate children are lowercase **`wsdl/`** and **`schema/`**. Some hosts are case-sensitive, so the casing matters.

Optional JAX-WS / JAXB binding fragments that this repository ships live under **`wsdls/xml/binding/`**.

---

## Integrators (published JAR from Maven Central)

Applications depend on **`au.gov.nehta:hi-b2b-client`** from Maven Central. At runtime the library resolves WSDL locations through **`au.gov.nehta.vendorlibrary.hi.wsdl.HiWsdlArtifactRoot`**.

Set **`HI_WSDL_ARTIFACT_ROOT`** to a directory whose immediate children are **`wsdl/`** and **`schema/`**. Resolution order (first match wins):

1. **`HiWsdlArtifactRoot.setRoot(Path)`**
2. **`HI_WSDL_ARTIFACT_ROOT`** environment variable
3. **`HI_WSDL_ARTIFACT_ROOT`** JVM system property (**`-D...`**)
4. **`HI_WSDL_ARTIFACT_ROOT`** in **`local.properties`** in the JVM working directory, if present

Details and related TLS/registration keys: **`README.md`**, **`local.properties.example`**.

The published JAR excludes **`*.wsdl`**; the licensed tree is never redistributed inside the client artifact.

---

## Contributors (building this repository from source)

Maven **`verify`** runs **`wsimport`** against the licensed tree. Default build-time root is **`wsdls/xml`** (relative to the repository root, beside **`pom.xml`**).

| Property | Purpose |
| -------- | ------- |
| **`hi.wsdl.tree.root`** | Build-time WSDL/XSD root for **`wsimport`** |
| **`HI_WSDL_ARTIFACT_ROOT`** | Runtime only (integration tests, not Maven compile) |

Override **`hi.wsdl.tree.root`** (first match wins):

1. **`-Dhi.wsdl.tree.root=<path>`** — Maven user property
2. **`HI_WSDL_TREE_ROOT`** — environment variable
3. **`hi.wsdl.tree.root`** in an active Maven **`settings.xml`** profile (see **`settings.xml.example`**)
4. Default: **`wsdls/xml`**

Without the licensed tree under that root, use **`-Phi-wsdl-artifact`** and a matching **`au.gov.nehta:hi-wsdl`** artifact from Maven Central (or a local install — see **Local builds** in **`CONTRIBUTING.md`**).

Optional Ant helpers under **`wsdls/`** (**`build.xml`**) use the same layout; they are not required for **`mvn verify`**.

---

## Template files

Templates at the repository root (beside **`pom.xml`**):

| File | Purpose |
| ---- | ------- |
| **`local.properties.example`** | Documents **`HI_WSDL_ARTIFACT_ROOT`** and **`HI_*`** keys for runtime and integration tests |
| **`settings.xml.example`** | Optional Maven settings fragment for **`hi.wsdl.tree.root`** |

Copy locally; do not commit populated copies:

```text
local.properties.example  ->  local.properties
settings.xml.example      ->  settings.xml   (optional, at repo root)
```

Use repository secrets, a vault, or environment variables in production — not committed property files.
