# Loan Matching Application


## Usage
1. (Optional) Download jar from Github Releases
1. Build the application with `./gradlew clean build`
1. Run with `java -jar build/libs/kata-loan-matching-1.0-SNAPSHOT.jar [path to csv] [loan request]`

# Running with different Matching modes
To change the matching modes between the different implementations, change the Matcher being used in `com.kata.loan.App`

## Improvements
1. Add better matching implementations
1. Add test coverage for App class which would require something like Guice for IoC
1. Group the lenders to risk 'buckets' to make matching easier and better for lenders