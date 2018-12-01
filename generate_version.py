#!/usr/bin/env python3
import os

command = "./gradlew clean build bintrayUpload -PbintrayUser=costular -PbintrayKey=3ecc6a88e3452b785e84ff6336e0f02ae5279aa0 -PdryRun=false"
os.system(command)
