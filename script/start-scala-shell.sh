#!/bin/bash
JAVA_HOME="$(/usr/libexec/java_home --version 1.8 --failfast 2>/dev/null)" exec "/usr/local/Cellar/apache-flink/1.12.0/libexec/libexec/start-scala-shell.sh"  "$@"
