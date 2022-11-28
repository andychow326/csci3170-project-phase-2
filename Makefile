JAVA_HOME=/usr/libexec/java_home -v 1.8

.PHONY: setup-local
setup-local: ./config.properties.local
	cp ./config.properties.local ./src/main/resources/config.properties

.PHONY: setup-prod
setup-prod: ./config.properties.production
	cp ./config.properties.production ./src/main/resources/config.properties

.PHONY: clean
clean:
	@rm -rf ./target
	@git checkout -- ./target

.PHONY: build
build: ./src/main/resources/config.properties ./src/main/resources/mysql-jdbc.jar
	@printf "Compiling..."
	@make clean
	@$(JAVA_HOME) --exec javac -d ./target/classes ./src/main/java/**/*.java
	@cp ./src/main/resources/config.properties ./target/classes/
	@cp ./src/main/resources/mysql-jdbc.jar ./target/classes/
	@printf "Done!\n"

.PHONY: start
start: ./target/classes/client/Main.class ./target/classes/mysql-jdbc.jar
	@$(JAVA_HOME) --exec java -cp ./target/classes/mysql-jdbc.jar:./target/classes client.Main

.PHONY: run
run:
	@make build
	@make start

.PHONY: check-tidy
check-tidy:
	git status --porcelain | grep '.*'; test $$? -eq 1
