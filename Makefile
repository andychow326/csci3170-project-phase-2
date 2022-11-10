.PHONY: setup-local
setup-local: ./config.properties.local
	cp ./config.properties.local ./src/main/resources/config.properties

.PHONY: setup-prod
setup-prod: ./config.properties.production
	cp ./config.properties.production ./src/main/resources/config.properties

.PHONY: clean
clean:
	@rm -rf ./target

.PHONY: build
build: ./src/main/resources/config.properties ./src/main/resources/mysql-jdbc.jar
	@printf "Compiling..."
	@make clean
	@javac -d ./target/classes ./src/main/java/**/*.java
	@cp ./src/main/resources/config.properties ./target/classes/
	@cp ./src/main/resources/mysql-jdbc.jar ./target/classes/
	@printf "Done!\n"

.PHONY: start
start: ./target/classes/client/Main.class ./target/classes/mysql-jdbc.jar
	@java -cp ./target/classes/mysql-jdbc.jar:./target/classes client.Main

.PHONY: run
run:
	@make build
	@make start
