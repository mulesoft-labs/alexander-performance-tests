# Mozart Performance Tests
## a.k.a Alexander The Great - a.k.a. The Unforgiven
Here we can found the framework and definition for every performance test suite defined.

# Getting started
## Requirements
* JDK 1.8

## Running a test

### 1. Configuration
Configure `src/main/resources/application.properties` for all relevant variables.
```
eastwoodUrl: # Host where an Eastwood cluster is located
orgId: # Organization to be used for tests. 
```

### 2.a Running locally
* NOTE: Used for local testing, the results are not representative as they won't have a linear response time variation. 

`mvn gatling:test -Dgatling.core.simulationClass=eastwood.testconnectivity.TestConnectivity`

### 2.b Running remote
* NOTE: Needs SSH access to the given IP and provides realistic results as it runs remote.

`./run_remote eastwood.testconnectivity.TestConnectivity 192.168.1.1`

#### Results
* Results of the run will be located in `DATE-TIME-results.zip` in the local dir.
* Push final results with a proper name here: https://drive.google.com/drive/u/1/folders/1HoXSYJZ0qL6EUdWWWMwr9sY3uPPhI1ic
* You can check the results by opening the ZIP file and searching for the `index.html` file. 

## Logging
We use plain logging with structured logging, so this solution can be plugged in to a system like ELK to easily parse 
important information

#### Example
```
Simulation Eastwood started...

================================================================================
2019-07-29 15:48:02                                           5s elapsed
---- Requests ------------------------------------------------------------------
> Global                                                   (OK=0      KO=0     )


---- Users ---------------------------------------------------------------------
[--                                                                        ]  0%
          waiting: 72     / active: 2      / done: 0     
================================================================================
```
