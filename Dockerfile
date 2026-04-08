FROM amazoncorretto:17
WORKDIR /app

# 빌드 단계

# gradle 메타 복사
COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle
RUN ./gradlew --no-daemon build -x test || true \
 && ./gradlew --no-daemon dependencies || true

# 소스 파일을 컨테이너로 복사
COPY src ./src

# 빌드
RUN ./gradlew --no-daemon clean bootJar -x test

# 포트 노출
EXPOSE 80

# 환경변수 설정(프로젝트, JVM)
ENV PROJECT_NAME=discodeit \
    PROJECT_VERSION=1.2-M8 \
    JVM_OPTS=""

# 애플리케이션 실행(shell을 통해 환경변수를 인식하도록 한다.)
ENTRYPOINT ["sh", "-c", "java $JVM_OPTS -jar ./build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar"]
