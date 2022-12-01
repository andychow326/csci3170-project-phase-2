Group number: 33
Group member name   (SID):
    Lam Lok Hin     (1155143392)
    Lau Yu Hin      (1155143546)
    Chow Chi Hong   (1155159063)

Prerequisite:
    Note that this project is using JDK 8 and we will use docker for development usage, for the following setup to work, we need to
    
    1. Install JDK 8
    2. Install docker

Environment setup:
    This project is using the file config.properties for configuration of database credentials.
    Please perform the following procedure based on your current environment:

    Development (running on local machine):
        1.  Run 'make setup-local'
        2.  To host the mysql database on your local machine, 
            Run 'docker compose up -d'

    Production (running on CSE machine):
        1.  Run 'make setup-prod'

Running everything:
    1.  To compile the project,
        Run 'make build'
    2.  To start the script,
        Run 'make start'
    
    There is an alias script for running both the above script,
    you can do so by the following:

        Run 'make run'

Project Architecture:
    This project has the following resources in the path of `src/main/resources`:
    - demo_data             (A folder containing demo data)
    - sample_data           (A folder containing sample data)
    - config.properties     (Configuration file storing database credentials)
    - mysql-jdbc.jar        (JDBC connector)
    - schema.sql            (Schema file that defined the table schema)

    This project has the the following packages in the path of `src/main/java`:
    - client        (Client for the user to interact with)

        -> Main.java:               Main function of the whole project, which will first read the config file
        -> DatabaseClient.java:     An Object that storing Database Connection and Migrator

    - config        (Contains a Config Object for storing configurations, mainly database credentials)

        -> Config.java:             Have a method to extracting data from the config file (config.properties) to the Config Object

    - dao           (Database Access Object[DAO] for extracting and converting query results to JAVA Object)

        -> BaseDao.java:    An interface that defined some base methods for the DAO
        -> Dao.java:        An interface that defined some general methods for the Dao
        -> DaoImpl.java:    Implementations of the Dao.java interface

        Implementations of the BaseDao.java interface and child of Dao.java:

        -> CategoryDao.java:        Perform query on the `category` table and extract results data to consturct Category Java Object
        -> ManufacturerDao.java:    Perform query on the `manufacturer` table and extract results data to consturct manufacturer Java Object
        -> PartDao.java:            Perform query on the `part` table and extract results data to consturct Part Java Object
        -> SalespersonDao.java:     Perform query on the `salesperson` table and extract results data to consturct Salesperson Java Object
        -> TransactionDao.java:     Perform query on the `transaction` table and extract results data to consturct Transaction Java Object

        -> ManufacturerRelationalDao.java:      Child of ManufacturerDao.java which implements relational attributes for the `manufacturer` table
        -> PartRelationalDao.java:              Child of PartDao.java which implements relational attributes for the `part` table
        -> SalespersonRelationalDao.java:       Child of TransactionDao.java which implements relational attributes for the `transaction` table

    - migrator      (Migrator for migrating schema file to the database)

        -> Migrator.java:           Perform database schema migration using specific label for determining migrate up (Create migration) or migrate down (Undo migration)

    - model         (Models of JAVA Object which storing data extracted from DAO)

        -> BaseModel.java:                The parent model of all and checking of data format
        -> Category.java:                 The model of the Category and call dao function to create a object for each row of Category 
        -> Manufacturer.java:             The model of the Manufacturer and call dao function to create a object for each row of Manufacturer
        -> Part.java:                     The model of the Part, call dao function to create a object for each row of Part
        -> Salesperson.java:              The model of the Salesperson and call dao function to create a object for each row of Salesperson
        -> Transaction.java:              The model of the Transaction and call dao function to create a object for each row of Transaction
        
        -> ManufacturerRelational.java:   The child model of the Manufacturer and store total sales value of the parts from that Manufacturer
        -> PartRelational.java:           The child model of the Part and store Manufacturer object and Category object
        -> SalespersonRelational.java:    The child model of the Salesperson and introduce total transaction of that Salesperson

    - salessystem   (Sales System that performs different operations)

        -> BaseOperation.java:            valid user input and handle all errors
        -> SalesSystem.java:              Display the mainmenu and direct to different user pages
        -> AdministratorOperation.java:   Display the administratistor menu and execute the three administrator functions
        -> ManagerOperation.java:         Display the manager menu and execute the four manager functions
        -> SalespersonOperation.java:     Display the salesperson menu and execute the two salesperson functions
