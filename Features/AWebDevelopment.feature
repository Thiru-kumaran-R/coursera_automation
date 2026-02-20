Feature: Search and display Web Development courses
  As a learner
  I want to search for beginner-level web development courses in English
  So that I can view the first two courses with details


  Scenario Outline: Display first two beginner-level web development courses in English
    When I search for "<Course Name>" courses
    And I filter courses by "<Level>" level
    And I filter courses by "<Language>" language
    Then I should see the first two courses displayed
    And each course should show the name
    And each course should show the total learning hours
    And each course should show the rating

    Examples:
    |Course Name | Level | Language |
    |Web Development | Beginner | English|