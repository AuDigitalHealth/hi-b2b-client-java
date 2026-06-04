# Security

## Reporting issues

For **security vulnerabilities in this library** (not routine support):

1. Prefer **GitHub private vulnerability reporting** for this repository if it is enabled (**Security** tab -> **Report a vulnerability**).
2. Otherwise use your organisation's usual channel for **ADHA / AuDigitalHealth** repositories. Do **not** post exploit details, live credentials, or production URLs in a **public issue** before triage.

## This repository

- **Do not commit secrets to git.** That includes passwords, API tokens, private keys, real mutual-TLS keystores, production or staging HI endpoint URLs with embedded credentials, and Services Australia / vendor registration material-even inside comments, test fixtures, sample code, or documentation that is tracked in this repository.
- **`local.properties`** is gitignored. Never commit real HI credentials, keystores, or vendor registration material.
- **`src/test/resources/*.jks`** and **`src/sample/resources/*.jks`** in this tree are **test / sample placeholders** with documented defaults (see **`local.properties.example`** and **`README.md`**). Do not use them for production endpoints or production keys.
