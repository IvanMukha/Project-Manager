FROM tomcat:latest
COPY build/libs/main-1.0-SNAPSHOT.war usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh","run"]