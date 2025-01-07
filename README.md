# SkyPaths: Command Line Application for Airport and Runway Queries

## Overview
SkyPaths is a command-line application developed in Scala for querying and generating reports on airport and runway data. The application processes three CSV files containing information on countries, airports, and runways. It provides functionalities to search airports by country and generate various reports based on the data.

## Features

### 1. Query Functionality
- Users can search for airports and runways by providing a country name or country code.
- Fuzzy matching is supported to allow partial or approximate country name inputs (e.g., entering "zimb" will match "Zimbabwe").

### 2. Reports Functionality
The following reports are available:
- **Top 10 countries with the highest and lowest number of airports** (including the count).
- **Types of runways per country**, based on the "surface" column in the runway data.
- **Top 10 most common runway latitudes**, based on the "le_ident" column in the runway data.

## How to Run

### Prerequisites
- Scala and SBT (Simple Build Tool) must be installed on your system.

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/TRach07/project_scala_aviation.git
   cd <repository-folder>
   ```
2. View the commit log and ensure you're at the correct commit:
   ```bash
   git log
   ```
3. Run the application using SBT:
   ```bash
   sbt run
   ```
4. Follow the on-screen prompts to interact with the application.

Alternatively, you can launch the application from an IDE where the commit hash is visible.

## Code Structure

### Packages and Classes

- **`ui.CommandLineApp`**: Contains the main method and handles user interactions.
- **`storage.DataStorage`**: Responsible for parsing the CSV files and storing the data in memory.
- **`query.QueryEngine`**: Provides methods to query the data and generate reports.
- **`model`**: Contains case classes representing the data entities and their companion objects for CSV parsing.

### Data Models

1. **`Country`**:
   - Fields: `id`, `code`, `name`, `continent`
   - Companion object provides a method `fromCsv` to parse a CSV line into a `Country` instance.

2. **`Airport`**:
   - Fields: `id`, `name`, `countryCode`
   - Companion object provides a method `fromCsv` to parse a CSV line into an `Airport` instance.

3. **`Runway`**:
   - Fields: `id`, `airportId`, `surface`, `leIdent`
   - Companion object provides a method `fromCsv` to parse a CSV line into a `Runway` instance.

## Functionality Details

### Query Functionality
- Queries airports and runways based on user input (country name or code).
- Supports fuzzy matching for country names using the Jaro-Winkler distance.

### Report Generation
1. **Top 10 Countries by Number of Airports**:
   - Lists the 10 countries with the highest and lowest number of airports, along with their counts.
2. **Runway Types per Country**:
   - Displays the distinct types of runways (based on surface) for each country.
3. **Common Runway Latitudes**:
   - Lists the 10 most frequently occurring runway latitudes.


