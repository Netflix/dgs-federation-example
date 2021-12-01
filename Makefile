.PHONY: help all reviews/check shows/check
.DEFAULT_GOAL := help

SHELL = /bin/sh

## Gradle
GW = ./gradlew
GFLAGS ?=
GW_CMD = $(GW) $(GFLAGS)

reviews/check: ## Builds and checks the reviews-dgs
	@cd reviews-dgs && \
		$(GW_CMD) clean check

shows/check: ## Builds and checks the shows-dgs
	@cd shows-dgs && \
		$(GW_CMD) clean check

all: reviews/check shows/check  ## Cleans, checks/tests, publishes the plugin locally and runs the examples.


help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
