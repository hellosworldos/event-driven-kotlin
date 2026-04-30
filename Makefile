DOCKER_RUN = docker compose run --rm gradle

.PHONY: build test lint analyze check clean docker-build docker-test docker-lint docker-analyze docker-check

build:
	./gradlew build --no-daemon

test:
	./gradlew test --no-daemon

lint:
	./gradlew ktlintCheck --no-daemon

lint-fix:
	./gradlew ktlintFormat --no-daemon

analyze:
	./gradlew detekt --no-daemon

check: lint analyze test

clean:
	./gradlew clean --no-daemon

docker-build:
	docker compose build

docker-test:
	$(DOCKER_RUN) gradle test --no-daemon

docker-lint:
	$(DOCKER_RUN) gradle ktlintCheck --no-daemon

docker-analyze:
	$(DOCKER_RUN) gradle detekt --no-daemon

docker-check: docker-lint docker-analyze docker-test
