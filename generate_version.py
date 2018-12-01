#!/usr/bin/env python3
import os

api_key = os.environ['BINTRAY_KEY']
command = "./gradlew clean build bintrayUpload -PbintrayUser=costular -PbintrayKey={bintray} -PdryRun=false".format(bintray=api_key)
os.system(command)
