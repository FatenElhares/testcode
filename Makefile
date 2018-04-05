# Project variables
PROJECT_NAME ?= pixalione
ORG_NAME ?= pixalione
REPO_NAME ?= pixalione

# Filenames
DEV_COMPOSE_FILE := docker-compose.yml

# Docker Compose Project Names
REL_PROJECT := $(PROJECT_NAME)$(BUILD_ID)
DEV_PROJECT := $(REL_PROJECT)dev


#Application Service Name - must match Docker Compose release specification application service name
APP_SERVICE_NAME := app


# Build tag expression - can be used to evaulate a shell expression at runtime
BUILD_TAG_EXPRESSION ?= date -u +%Y%m%d%H%M%S

# Execute shell expression
BUILD_EXPRESSION := $(shell $(BUILD_TAG_EXPRESSION))

# Build tag - defaults to BUILD_EXPRESSION if not defined
BUILD_TAG ?= $(BUILD_EXPRESSION)


#check and Inspect Logic 

INSPECT := $$(docker-compose -p $$1 -f $$2 ps -q $$3 | xargs -I ARGS docker inspect -f "{{ .State.ExitCode}}" ARGS)

CHECK := @bash -c '\
  if [[ $(INSPECT) -ne 0 ]]; \
  then exit $(INSPECT); fi' VALUE

# Use these settings to specify a custom Docker registry 
DOCKER_REGISTRY := docker.io

#DOCKER_REGISTRY := https://index.docker.io/v1/


# WARNING: Set DOCKER_REGISTRY_AUTH to empty for Docker Hub
# Set DOCKER_REGISTRY_AUTH to auth endpoint for private Docker registry
DOCKER_REGISTRY_AUTH ?=


.PHONY: test build release clean tag buildtag login logout publish

testbuild:

	${INFO} "Creating ConfigServer Image..."
	@ apt-get install maven --assume-yes
	@ cd ConfigServerService && mvn package

	${INFO} "Creating Eureka Server Image..."
	@ cd EurekaServer && mvn package

	${INFO} "Creating Stemmer Image..."
	@ cd Stemmer_service && mvn package


	${INFO} "Creating Lemmatizer Image..."
	@ cd Stemmer_service && mvn package

	${INFO} "Stage Test and Build Complete"

release:
	${INFO} "Starting Release phase..."
	@ docker-compose up -d
	${INFO} "Realising is Done ! ..."

clean :
	${INFO} "Destroying development environment..."
	@ docker-compose -p $(DEV_PROJECT) -f $(DEV_COMPOSE_FILE) down -v
	${INFO} "Removing dangling images..."
	docker images -q -f dangling=true -f label=application=$(REPO_NAME) | xargs -I ARGS docker rmi -f ARGS
	${INFO} "Clean Complete"


tag :
	${INFO} "Tagging release image with tags $(TAG_ARGS)..."
	@ $( foreach tag ,$(TAG_ARGS) ,docker tag $(IMAGE_ID) $(DOCKER_REGISTRY)/$(ORG_NAME)/$(REPO_NAME):$(tag);)
	${INFO} "Taggingg Complete"

buildtag:
	${INFO} "Tagging release image with suffix $(BUILD_TAG) and build tags $(BUILDTAG_ARGS)..."
	@ $(foreach tag,$(BUILDTAG_ARGS), docker tag $(IMAGE_ID) $(DOCKER_REGISTRY)/$(ORG_NAME)/$(REPO_NAME):$(tag).$(BUILD_TAG);)
	${INFO} "Tagging complete"

login:
	${INFO} "Logging in to Docker registry $$DOCKER_REGISTRY..."
	@ docker login -u fatenhrs -p insat123456
#	@ docker login -u $$DOCKER_USER -p $$DOCKER_PASSWORD  $(DOCKER_REGISTRY_AUTH)
	${INFO} "Logged in to Docker registry $$DOCKER_REGISTRY"

logout:
	${INFO} "Logging out of Docker registry $$DOCKER_REGISTRY..."
	@ docker logout
	${INFO} "Logged out of Docker registry $$DOCKER_REGISTRY"	

publish:
	${INFO} "Publishing release image $(IMAGE_ID) to $(DOCKER_REGISTRY)/$(ORG_NAME)/$(REPO_NAME)..."
	@ $(foreach tag,$(shell echo $(REPO_EXPR)), docker push $(tag);)
	${INFO} "Publish complete"



# Cosmetics
YELLOW := "\e[1;33m"
NC := "\e[0m"

# Shell Functions
INFO := @bash -c '\
  printf $(YELLOW); \
  echo "=> $$1"; \
printf $(NC)' SOME_VALUE

#Get container ID of application service container 
APP_CONTAINER_ID := $$(docker-compose -p $(REL_PROJECT) -f $(REL_COMPOSE_FILE) ps -q $(APP_SERVICE_NAME) )

#Get image ID of application service
IMAGE_ID := $$(docker inspect -f '{{ .Image }}' $(APP_CONTAINER_ID))

# Repository Filter
ifeq ($(DOCKER_REGISTRY), docker.io)
	REPO_FILTER := $(ORG_NAME)/$(REPO_NAME)[^[:space:]|\$$]*
else
	REPO_FILTER := $(DOCKER_REGISTRY)/$(ORG_NAME)/$(REPO_NAME)[^[:space:]|\$$]*
endif



# Introspect repository tags
REPO_EXPR := $$(docker inspect -f '{{range .RepoTags}}{{.}} {{end}}' $(IMAGE_ID) | grep -oh "$(REPO_FILTER)" | xargs)

# Extract build tag arguments
ifeq (buildtag,$(firstword $(MAKECMDGOALS)))
	BUILDTAG_ARGS := $(wordlist 2,$(words $(MAKECMDGOALS)),$(MAKECMDGOALS))
  ifeq ($(BUILDTAG_ARGS),)
  	$(error You must specify a tag)
  endif
  $(eval $(BUILDTAG_ARGS):;@:)
endif




# Extract tag arguments 
ifeq (tag,$(firstword $(MAKECMDGOALS) ))
   TAG_ARGS := $(wordlist 2 , $(words $(MAKECMDGOALS)),$(MAKECMDGOALS)))
    
   ifeq ($(TAG_ARGS),)
      $(error you must specify a tag)
   endif 
    $(eval $(TAG_ARGS) :;@:)

endif
