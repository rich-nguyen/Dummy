java -Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=384M -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1044 -jar ./sbt-launch.jar "$@"