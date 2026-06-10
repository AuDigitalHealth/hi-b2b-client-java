***************************
	IMPORTANT
***************************

Healthcare Identifier WSDL and XSD files are licensed material from Services Australia / ADHA and
are NOT shipped in this Git repository. Download them from:

  https://healthsoftware.humanservices.gov.au/claiming/ext-vnd/

Extract the bundle to a directory whose immediate children are lowercase wsdl/ and schema/. Some
hosts and CI runners are case-sensitive, so the casing matters. Optional JAX-WS / JAXB binding
fragments that this repository does ship live under wsdls/xml/binding/.

See "Configuring the location" below to tell the build and/or the library where you put the tree.
See CONTRIBUTING.md for building this repository and README.md for runtime use of the library.


***************************
  Configuring the location
***************************

There are TWO separate settings. They use DIFFERENT names because they cover different lifecycles,
and you can point them at the SAME directory or at different directories.

  - hi.wsdl.tree.root      build time only (Maven wsimport, enforcer)
  - HI_WSDL_ARTIFACT_ROOT  runtime only    (the published library, HiWsdlArtifactRoot)

The Maven build NEVER reads HI_WSDL_ARTIFACT_ROOT. The library at runtime NEVER reads
hi.wsdl.tree.root. Set whichever one(s) you need for the workflow you are running.


1. Build time: hi.wsdl.tree.root
--------------------------------

Used by jaxws-maven-plugin (wsimport) and the maven-enforcer-plugin WSDL presence check when you
build this checkout (mvn compile / test / verify / package / install). The value must be a
directory whose immediate children are wsdl/ and schema/.

Resolution order (first match wins):

  1. -Dhi.wsdl.tree.root=<path>            CLI user property; quote on PowerShell if the path has
                                            spaces.
  2. HI_WSDL_TREE_ROOT                     environment variable.
  3. hi.wsdl.tree.root in an active        e.g. mvn -s settings.xml ... uses the repository
     Maven settings.xml profile             settings.xml hi-wsdl-tree profile, or merge that
                                            profile into your Maven user settings file.
  4. Default                               <project.basedir>/wsdls/xml

Relative paths are resolved against the project root. The published JAR excludes *.wsdl
(maven-jar-plugin), so the licensed tree is consumed during this checkout's build only and never
redistributed inside hi-b2b-client.

Pass -Dhi.wsdl.codegen.skip=true to skip wsimport and the enforcer presence check (only useful when
you already have a fresh merged tree under target/ from a previous build).


2. Runtime: HI_WSDL_ARTIFACT_ROOT
---------------------------------

Used by au.gov.nehta.vendorlibrary.hi.wsdl.HiWsdlArtifactRoot when the published library resolves
@WebServiceClient wsdlLocation values at runtime. Integration tests in this repo (mvn -Pintegration
test) also read this. The value must be a directory whose immediate children are wsdl/ and schema/.

Resolution order (first match wins):

  1. HiWsdlArtifactRoot.setRoot(Path)      programmatic; highest precedence.
  2. HI_WSDL_ARTIFACT_ROOT                 process environment variable.
  3. HI_WSDL_ARTIFACT_ROOT                 key in local.properties in the JVM working directory
                                            (typically next to pom.xml in a checkout, or your
                                            application's working directory in production).
                                            Re-read on each resolve.
  4. HI_WSDL_ARTIFACT_ROOT                 JVM system property (-D...).

local.properties is gitignored; see local.properties.example for the documented keys (including
HI_WSDL_ARTIFACT_ROOT). Prefer forward slashes in property values for cross-platform behaviour.


***************************
  Template files
***************************

Two template files ship in Git at the project root, beside pom.xml:

  settings.xml.example        Maven user settings template; sets hi.wsdl.tree.root for wsimport
                              and maven-enforcer-plugin so the build can find the licensed tree.
                              Activated by mvn -s settings.xml ...

  local.properties.example    Runtime / test configuration template; documents HI_WSDL_ARTIFACT_ROOT
                              and the HI_* keys (keystore, truststore, endpoints, product / vendor
                              identifiers) used by integration tests and by HiWsdlArtifactRoot.

Both files are TEMPLATES. They are committed so you can see the expected shape; the real values
belong in copies that you keep LOCAL to your machine or build agent:

  settings.xml.example       ->  settings.xml
  local.properties.example   ->  local.properties

Copy each one to the name on the right, edit it in place with the values you need, then run Maven
from the project root (e.g. mvn -s settings.xml ...). local.properties is read by the library /
tests automatically from the JVM working directory.

local.properties is gitignored. settings.xml is not currently gitignored but should be treated the
same way: do NOT commit settings.xml or local.properties (or any keystore, password, token, or
production endpoint URL) to this repository or to any other source control repository - public or
private. For CI and production use repository secrets, a vault, or per-machine environment
variables instead of checking the populated files in.


Which one applies to you:

  - Users of the library (applications depending on au.gov.nehta:hi-b2b-client from Maven Central
    or another repository) set HI_WSDL_ARTIFACT_ROOT only. hi.wsdl.tree.root is irrelevant to you;
    you are not running this project's Maven build.

  - Contributors building this library from a source checkout set hi.wsdl.tree.root so wsimport and
    the enforcer can find the licensed tree (see CONTRIBUTING.md). If the same checkout runs
    integration tests (mvn -Pintegration test), also set HI_WSDL_ARTIFACT_ROOT; both can point at
    wsdls/xml.
