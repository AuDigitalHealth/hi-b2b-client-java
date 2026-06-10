# WSDL / binding change log

Historical notes for the **`wsdls/`** tree (bindings and legacy Ant helpers). For the main library release history see **CHANGELOG.md** at the repository root.

## 1.6.0

- Added Provider Read Provider Organisation client
- Added Provider Read Provider Administrative Individual client
- Added Provider Manage Provider Organisation client
- Added Provider Manage Provider Directory client
- Added Provider Manage Provider Administrative Individual client

## 1.5.0

- Unnecessary JAXB bindings have been removed from this build. This is everything barring the consumer binding and **`xmldsig_override.xml`**, which is needed for downstream projects (multiple different XMLDSig-bound types cause trouble for downstream projects).
- Added Provider Batch Async Search for Provider Organisation and Individual
