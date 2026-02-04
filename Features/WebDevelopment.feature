Feature: Search and display Web Development courses
  As a learner
  I want to search for beginner-level web development courses in English
  So that I can view the first two courses with details

  Background:
    Given I am on the Coursera homepage

  Scenario: Display first two beginner-level web development courses in English
    When I search for "Web Development" courses
    And I filter courses by "Beginner" level
    And I filter courses by "English" language
    Then I should see the first two courses displayed
    And each course should show the name
    And each course should show the total learning hours
    And each course should show the rating
