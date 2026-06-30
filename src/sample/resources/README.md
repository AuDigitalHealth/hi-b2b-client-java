Copyright 2011 NEHTA; Copyright 2021-2026 ADHA. Apache-2.0 — see **LICENSE.md** at repository root.

Sample programs live under `../java` (package `...hi.sample`). They illustrate how to construct HI SOAP
clients with keystores and endpoints; replace placeholders with values from your ADHA/Services Australia
registration (see **README.md**).

These sources are not compiled by the default Maven lifecycle. Type-check them with
`mvn -B -Psample -Dgpg.skip=true -DskipTests=true clean compile` (see **CONTRIBUTING.md**), or run
from your IDE as part of a Maven-imported project so **hi-wsdl** types and dependencies resolve.
