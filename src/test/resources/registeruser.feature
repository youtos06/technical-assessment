Feature: Register a user

  Scenario: Register a new valid user
    Given a new user
    And I fill in "Name" with "Youness El Housni"
    And I fill in "Birth Date" with "1997-07-14"
    And I fill in "Gender" with "male"
    And I fill in "Country" with "france"
    Then register the user
    And Receive status code of 201


  Scenario: Not register a user with missing mandatory field
    Given a new user
    And I fill in "Name" with "Youness El Housni"
    And I fill in "Gender" with "male"
    And I fill in "Country" with "france"
    Then register the user
    And Receive status code of 400

  Scenario: Not register a non adult user
    Given a new user
    And I fill in "Name" with "Youness El Housni"
    And I fill in "Birth Date" with "2020-07-14"
    And I fill in "Gender" with "male"
    And I fill in "Country" with "france"
    Then register the user
    And Receive status code of 400

  Scenario: Not register a non valid gender
    Given a new user
    And I fill in "Name" with "Youness El Housni"
    And I fill in "Birth Date" with "1997-07-14"
    And I fill in "Gender" with "no-gender"
    And I fill in "Country" with "france"
    Then register the user
    And Receive status code of 400

  Scenario: Not register a non french citizen
    Given a new user
    And I fill in "Name" with "Youness El Housni"
    And I fill in "Birth Date" with "1997-07-14"
    And I fill in "Gender" with "male"
    And I fill in "Country" with "italy"
    Then register the user
    And Receive status code of 400