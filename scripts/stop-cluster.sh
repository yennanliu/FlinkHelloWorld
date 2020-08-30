#!/bin/bash
JAVA_HOME="$(/usr/libexec/java_home --version 1.8 --failfast 2>/dev/null)" exec "/usr/local/Cellar/apache-flink/1.11.1/libexec/libexec/stop-cluster.sh"  "$@"
