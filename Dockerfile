ARG JDK_IMAGE
FROM ${JDK_IMAGE}
WORKDIR /app
ARG JAR_FILE
COPY ${JAR_FILE}  /app/myserver.jar
EXPOSE 8080

# _JAVA_OPTIONS ( 该参数 openjdk , HotSpot 都支持)
ENV _JAVA_OPTIONS "-Xms256m -Xmx512m -Djava.awt.headless=true"
CMD java -jar myserver.jar       #shell 模式  sh -c
#CMD ["java","-jar","myserver.jar"] # exec 模式   pid=1

#ENTRYPOINT java -jar myserver.jar       #shell 模式 ，会完全忽略命令行参数
#ENTRYPOINT ["java","-jar","myserver.jar"]     #exec 模式 , 追加命令行参数

# docker run -d -p 8080:8080 -v /tmp:/log --rm  --name mySpootDemo  74580d0e2715
# docker run -d -p 8088:8080 -v /tmp:/log --rm  --name mySpootDemo2  74580d0e2715

 #• 如果 ENTRYPOINT 使用了 shell 模式，CMD 指令会被忽略。
 #• 如果 ENTRYPOINT 使用了 exec 模式，CMD 指定的内容被追加为 ENTRYPOINT 指定命令的参数。
 #• 如果 ENTRYPOINT 使用了 exec 模式，CMD 也应该使用 exec 模式。
 #• ENTRYPOINT 指令也是可以被命令行覆盖的，需要显式的指定 --entrypoint 参数。