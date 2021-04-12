Survey Summary
===============

Created the result model, as this will purely hold the value of the result.
Any UI additions like styling, font can be done in a separate class

I was tempted to use a 2D array ,Arraylist[List<String>], where x-> Questions and y-> Participants answer
to the question. This would help save reparsing the answers for every participant in the ResultCruncher.
An additional field ` val answers: MutableList<String> = arrayListOf() ` in `Question` would store the parsed answers.
[ 5,5,3
  4,5,6
]
This would have saved me reparsing the answer list but I could not find a neater way 
to do it when I was parsing the csv i.e CSVReader.populateParticipants

Also I am not a fan of tying field validations in the data class itself i.e init{}block.
Sticking to clean architecture, I feel, validation is part of the core logic and i would
need tests to ensure the validation works.

# PreRequistes
- Gradle 5.2.1
- Jvm 1.8
- Kotlin 1.3.20

## Instructions
To build the application  => `./gradlew build`

To run the application => `./gradlew run --args="<filename1>,<filename2>"`

To run unit tests  => `./gradlew test`

To run the Integration tests  => `./gradlew integrationTest`

## Scenarios considered



## Assumptions
- Employee id to be a string and
 the survey answers are stored in an Arraylist to maintain insertion order
- Assuming that all employees have been mentioned in the response file,
the participation percentage is calculated
 i.e. total employee count = nbr of unique email/employee ids all entries
 - if no email or employee id is provided, however last submitted is present,
 the answers will count.
 - A single employee can have multiple entries and all are considered.
 - "Comma" , in a question is treated as a valid delimiter and the question will be split based on it.

  
## Areas to improve
 - Could use third party libraries to read csvs.
 - No validations on the type/value of input AND order of question files (what if only two columns are provided)
 Scenarios for not valid values .
 This can be handled by introducing interfaces.
 - Adding constant file for String constants.
 - Introduce smarts in existing code to identify headers i.e currently the if the line  has a  `@ or : or isblank` then
 it is not a header
 - the assumption of `A single employee can have multiple entries and all are considered.` can be 
 handled by overriding `equals` in the `Participant` data class. Thought it wasn't needed for now.