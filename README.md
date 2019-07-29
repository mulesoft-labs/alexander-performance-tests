# Alexander performance tests
## a.k.a Alexander the great
Here we can found the framework and definition for every performance test suite defined

# Getting started
## Requirements
* JDK 1.8

## Running a test
`mvn gatling:test -Dgatling.core.simulationClass=Massa`

## Logging
We use plain logging with structured logging, so this solution can be plugged in to a system like ELK to easily parse 
important information

#### Example
```
Simulation Massa started...

================================================================================
2019-07-29 15:48:02                                           5s elapsed
---- Requests ------------------------------------------------------------------
> Global                                                   (OK=0      KO=0     )


---- Users ---------------------------------------------------------------------
[--                                                                        ]  0%
          waiting: 72     / active: 2      / done: 0     
================================================================================
```