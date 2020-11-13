FROM java:8
LABEL maintainer="Maciej Wadas"
COPY Sql_Ruler10.java .
RUN curl -L -o /mysql-connector-java-8.0.13.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.13/mysql-connector-java-8.0.13.jar
RUN javac Sql_Ruler10.java
CMD java Sql_Ruler10
CMD ["java", "-classpath", "mysql-connector-java-8.0.13.jar:.", "Sql_Ruler10"]
