# Security

## Reporting issues

For **security vulnerabilities in this library** (not routine support):

1. Prefer **GitHub private vulnerability reporting** for this repository if it is enabled (**Security** tab → **Report a vulnerability**).
2. Otherwise use your organisation's usual channel for **ADHA / AuDigitalHealth** repositories. Do **not** post exploit details, live credentials, or production URLs in a **public issue** before triage.

## This repository

- **Do not commit secrets to git.** That includes passwords, API tokens, private keys, real mutual-TLS keystores, production or staging HI endpoint URLs with embedded credentials, and Services Australia / vendor registration material — even inside comments, test fixtures, sample code, or documentation that is tracked in this repository.
- **Do not commit licensed ADHA WSDL/XSD** under **`wsdls/xml/wsdl/`** or **`wsdls/xml/schema/`** (placeholder **`README.md`** files only). Download the bundle under your licence; see **`wsdls/README.md`** and **README.md**.
- **`local.properties`** is gitignored. Never commit real HI credentials, keystores, or vendor registration material.
- **`settings.xml`** (copy of **`settings.xml.example`**) is gitignored when placed at the repository root; do not commit machine-specific paths or credentials there.
- **`src/test/resources/`** paths such as **`keystore.jks`** / **`truststore.jks`** appear in **`local.properties.example`** and test constants; they are **not** committed when absent. Create keystores locally or point **`HI_*`** at your registration material. Do not commit real keystores or production keys. In **`local.properties`** and similar Java property files, prefer **forward slashes** in filesystem paths so the same values work on **Windows**, **macOS**, and **Linux**.
