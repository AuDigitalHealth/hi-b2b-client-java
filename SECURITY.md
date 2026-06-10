# Security

## If you integrate this library

Do **not** embed mutual-TLS private keys, keystore passwords, or production HI URLs in source control in **your** application. Load secrets from your platform's vault, environment, or secure configuration. The **`local.properties`** pattern in this repository applies only to **this repository's** tests (see **`local.properties.example`**).

## Reporting issues

For **security vulnerabilities in this library** (not routine support):

1. Prefer **GitHub private vulnerability reporting** for this repository if it is enabled (**Security** tab -> **Report a vulnerability**).
2. Otherwise use your organisation's usual channel for **ADHA / AuDigitalHealth** repositories. Do **not** post exploit details, live credentials, or production URLs in a **public issue** before triage.

## This repository

- **Do not commit secrets to git.** That includes passwords, API tokens, private keys, real mutual-TLS keystores, production or staging HI endpoint URLs with embedded credentials, and Services Australia / vendor registration material - even inside comments, test fixtures, sample code, or documentation that is tracked in this repository.
- **Licensed HI WSDL/XSD** (see **README.md**, **CONTRIBUTING.md**, and **wsdls/README.md**) are required at **`hi.wsdl.tree.root`** (default **`wsdls/xml`** next to **`pom.xml`**) to build from source. The published **`hi-b2b-client`** JAR does **not** include **`*.wsdl`**. If your organisation or vendor terms treat those files as non-public, do not push them to a public repository; install them on build agents or developer machines from an approved channel instead.
- **Local configuration templates:** **`settings.xml.example`** (Maven user settings; **`hi.wsdl.tree.root`**) and **`local.properties.example`** (runtime / test configuration; **`HI_WSDL_ARTIFACT_ROOT`** plus other **`HI_*`** keys) are tracked in Git as **templates**. Copy them to **`settings.xml`** and **`local.properties`** respectively, fill in your values, and keep the copies **local-only**. **`local.properties`** is gitignored; treat **`settings.xml`** the same way. **Never** commit the populated copies, real HI credentials, keystores, or vendor registration material to this repository or any other source control repository - public or private. See **`wsdls/README.md`** → **Template files**.
- **`src/test/resources/*.jks`** and **`src/sample/resources/*.jks`** in this tree are **test / sample placeholders** with documented defaults (see **`local.properties.example`** and **`README.md`**). Do not use them for production endpoints or production keys.
