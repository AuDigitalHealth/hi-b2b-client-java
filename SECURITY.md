# Security

## Reporting issues

Report security-sensitive findings through your organisation's usual channel for **ADHA / AuDigitalHealth** repositories (do not open a public issue with exploit details before it is triaged).

## This repository

- **Do not commit secrets to git.** That includes passwords, API tokens, private keys, real mutual-TLS keystores, production or staging HI endpoint URLs with embedded credentials, and Services Australia / vendor registration material - even inside comments, test fixtures, sample code, or documentation that is tracked in this repository.
- **`local.properties`** is gitignored. Never commit real HI credentials, keystores, or vendor registration material.
- **`settings.xml`** (copy of **`settings.xml.example`**) is gitignored when placed at the repository root; do not commit machine-specific **`hi.wsdl.tree.root`** or credentials there.
- **`src/test/resources/`** paths such as **`keystore.jks`** / **`truststore.jks`** appear in **`local.properties.example`** and test constants; they are **not** committed when absent. Create keystores locally or point **`HI_*`** at your registration material. Do not commit real keystores or production keys. In **`local.properties`** and similar Java property files, prefer **forward slashes** in filesystem paths so the same values work on **Windows**, **macOS**, and **Linux**.
