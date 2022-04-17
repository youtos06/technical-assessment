Feature: Register a user

  Scenario: Register a new valid user
    Given a new user
    And fill in "Name" with "Raja"
    And fill in "Birth Date" with "1995-10-05"
    And fill in "Gender" with "F"
    And fill in "Country" with "FR"
    Then register the user
    And Receive status code of 201
    And Field "name" equals "Raja"
    And Field "gender" equals "F"

  Scenario: Register a new valid user with just mandatory fields
    Given a new user
    And fill in "Name" with "nami"
    And fill in "Birth Date" with "2000-02-01"
    And fill in "Country" with "FR"
    Then register the user
    And Receive status code of 201
    And Field "name" equals "nami"

  Scenario: Conflict - not register same user
    Given a new user
    And fill in "Name" with "Raja"
    And fill in "Birth Date" with "1995-10-05"
    And fill in "Country" with "FR"
    Then register the user
    And Receive status code of 409

  Scenario: Bad Request - Not register a user with missing mandatory field
    Given a new user
    And fill in "Name" with "Youness"
    And fill in "Gender" with "F"
    And fill in "Country" with "FR"
    Then register the user
    And Receive status code of 400

  Scenario: Bad Request - Not register a non adult user
    Given a new user
    And fill in "Name" with "Youness"
    And fill in "Birth Date" with "2020-07-14"
    And fill in "Gender" with "M"
    And fill in "Country" with "FR"
    Then register the user
    And Receive status code of 400

  Scenario: Bad Request - Not register a non valid gender
    Given a new user
    And fill in "Name" with "Youness"
    And fill in "Birth Date" with "1997-07-14"
    And fill in "Gender" with "no-gender"
    And fill in "Country" with "FR"
    Then register the user
    And Receive status code of 400

  Scenario: Bad Request - Not register a non french citizen
    Given a new user
    And fill in "Name" with "Youness"
    And fill in "Birth Date" with "1997-07-14"
    And fill in "Gender" with "M"
    And fill in "Country" with "italy"
    Then register the user
    And Receive status code of 400