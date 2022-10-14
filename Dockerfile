FROM jdk8:latest

LABEL maintainer="tangzhijie"

ENV ACTIVE_PROFILE=sit

ADD *.jar ./app.jar

EXPOSE 9000

ENTRYPOINT ["sh","-c","java ${AGENT_PARAMS} ${PARAMS} -Djava.security.egd=file:/dev/./urandom -jar ./app.jar --spring.profiles.active=${ACTIVE_PROFILE}"]
