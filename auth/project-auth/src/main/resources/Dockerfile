# 使用官方的 OpenJDK 11 镜像作为基础镜像
FROM openjdk:11-jre

# 设置工作目录
WORKDIR /app

# 复制本地的 JAR 文件到容器中
COPY auth/project-auth/target/project-auth-1.0.3-SNAPSHOT.jar /app

# 暴露应用程序的端口（根据你的 Spring Boot 应用程序的配置）
EXPOSE 31001

# 启动 Spring Boot 应用
CMD ["java", "-jar", "project-auth-1.0.3-SNAPSHOT.jar"]