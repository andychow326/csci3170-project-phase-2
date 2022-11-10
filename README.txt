Group number: 33
Group member name   (SID):
    Lam Lok Hin     (1155143392)
    Lau Yu Hin      (1155143546)
    Chow Chi Hong   (1155159063)

Prerequisite:
    Note that this project is using JDK 8 and we will use docker
    for development usage, for the following setup to work,
    we need to
    
    1. Install JDK 8
    2. Install docker

Environment setup:
    This project is using the file config.properties for configuration
    of database credentials. Please perform the following procedure
    based on your current environment.

    Development (running on local machine):
        1.  Run 'make setup-local'
        2.  To host the mysql database on your local machine, 
            Run 'docker compose up -d'

    Production (running on CSE machine):
        1.  Run 'make setup-prod'

Running locally:
    1.  To compile the project,
        Run 'make build'
    2.  To start the script,
        Run 'make start'
    
    There is an alias script for running both the above script,
    you can do so by the following:

        Run 'make run'
