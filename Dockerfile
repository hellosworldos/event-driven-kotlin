FROM gradle:8.13-jdk21-alpine

WORKDIR /app

# Cache dependencies
COPY build.gradle.kts settings.gradle.kts ./
RUN gradle dependencies --no-daemon 2>/dev/null || true

COPY . .
