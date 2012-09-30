#!/usr/bin/env sh
echo "Running Prepare for Travis"
curl http://www.ettrema.com/maven2/com/ettrema/milton-api/1.8.1.4/milton-api-1.8.1.4.jar > /tmp/milton-api-1.8.1.4.jar
curl http://www.ettrema.com/maven2/com/ettrema/milton-api/1.8.1.4/milton-api-1.8.1.4.pom > /tmp/milton-api-1.8.1.4.pom
curl http://www.ettrema.com/maven2/com/ettrema/milton-servlet/1.8.1.4/milton-servlet-1.8.1.4.jar > /tmp/milton-servlet-1.8.1.4.jar
curl http://www.ettrema.com/maven2/com/ettrema/milton-servlet/1.8.1.4/milton-servlet-1.8.1.4.pom > /tmp/milton-servlet-1.8.1.4.pom
mvn install:install-file -DgroupId=com.ettrema -DartifactId=milton-api -Dpackaging=pom -Dversion=1.8.1.4 -Dfile=/tmp/milton-api-1.8.1.4.pom
mvn install:install-file -DgroupId=com.ettrema -DartifactId=milton-api -Dpackaging=jar -Dversion=1.8.1.4 -Dfile=/tmp/milton-api-1.8.1.4.jar
mvn install:install-file -DgroupId=com.ettrema -DartifactId=milton-servlet -Dpackaging=pom -Dversion=1.8.1.4 -Dfile=/tmp/milton-servlet-1.8.1.4.pom
mvn install:install-file -DgroupId=com.ettrema -DartifactId=milton-servlet -Dpackaging=jar -Dversion=1.8.1.4 -Dfile=/tmp/milton-servlet-1.8.1.4.jar

