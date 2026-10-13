#!/usr/bin/env python3
"""Validate the Android/PHP repository layout and XML resources."""

from pathlib import Path
import subprocess
import xml.etree.ElementTree as ET


ROOT = Path(__file__).resolve().parents[1]
ANDROID = ROOT / "mobile/app/src/main"
API = ROOT / "api"


def main() -> None:
    required = [
        ANDROID / "AndroidManifest.xml",
        ROOT / "mobile/settings.gradle",
        API / "conn.php",
        ROOT / "LICENSE",
    ]
    missing = [str(path.relative_to(ROOT)) for path in required if not path.is_file()]
    if missing:
        raise SystemExit(f"Missing required files: {', '.join(missing)}")

    ET.parse(ANDROID / "AndroidManifest.xml")
    for path in ANDROID.glob("res/**/*.xml"):
        ET.parse(path)

    tracked = subprocess.check_output(["git", "ls-files"], cwd=ROOT, text=True).splitlines()
    generated = [path for path in tracked if "/.idea/" in f"/{path}" or "/build/" in f"/{path}"]
    if generated:
        raise SystemExit("Generated project files are tracked: " + ", ".join(generated))

    print(f"Validated Android resources and {len(list(API.glob('*.php')))} PHP endpoints.")


if __name__ == "__main__":
    main()
