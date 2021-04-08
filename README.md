Survey Summary
===============

Created the result model, as this will purely hold the value of the result.
Any UI additions like styling, font can be done in a separate class


# PreRequistes
- Gradle 5.2.1
- Jvm 1.8
- Kotlin 1.3.20

## Instructions
To Build & run Unit tests  => `./gradlew build`

To run the application => `./gradlew run --args="<filename1>,<filename2>"`

To run the Integration tests  => `./gradlew integrationTest`

## Scenarios considered



## Assumptions
- Employee id to be a number and
 the survey answers are stored in an Arraylist to maintain insertion order
- Assuming that all employees have been mentioned in the response file,
the participation percentage is calculated
 i.e. total employee count = nbr of unique email/employee ids
 - if no email or employee id is provided, however last submitted is present,
 the answers will count.
 - A single employee can have multiple entries
  
## Areas to improve
 - A single employee can have multiple entries
 - Could use third party libraries to read csvs
 - Using mockito to mock Resultcruncher in unit tests.
 - Introduce smarts in existing code to identify headers i.e header has a date or not blank
