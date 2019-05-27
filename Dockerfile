ARG JDK_IMAGE
FROM ${JDK_IMAGE}
WORKDIR /app
ARG JAR_FILE
COPY ${JAR_FILE}  /app/myserver.jar
EXPOSE 8080

# _JAVA_OPTIONS ( 该参数 openjdk , HotSpot 都支持)
ENV _JAVA_OPTIONS "-Xms256m -Xmx512m -Djava.awt.headless=true"
CMD java -jar myserver.jar
#CMD ["java","-jar","myserver.jar"]

# docker run -d -p 8080:8080 -v /tmp:/log --rm  --name mySpootDemo  74580d0e2715
# docker run -d -p 8088:8080 -v /tmp:/log --rm  --name mySpootDemo2  74580d0e2715