#!/usr/bin/env bash
# Build from source: mvn clean verify from repository root.
# WSDL tree override: HI_WSDL_TREE_ROOT or -Dhi.wsdl.tree.root=... (see CONTRIBUTING.md).
# Optional: MVN_SETTINGS=path/to/settings.xml
set -euo pipefail
cd "$(dirname "$0")"
extra=()
case "${1:-}" in
    '') ;;
    shaded|-shaded|--shaded) extra=(-Pfat-jar) ;;
    *)
        echo "Unknown argument: $1" >&2
        echo "Optional: shaded | -shaded | --shaded" >&2
        exit 2
        ;;
esac
settings_args=()
if [[ -n "${MVN_SETTINGS:-}" ]]; then
    settings_args=(-s "$MVN_SETTINGS")
fi
if [ "${#extra[@]}" -eq 0 ]; then
    echo 'Building hi-b2b-client JAR'
else
    echo 'Building hi-b2b-client FAT/UBER JAR'
fi
exec mvn -B "${settings_args[@]}" "${extra[@]}" -Dgpg.skip=true clean verify
