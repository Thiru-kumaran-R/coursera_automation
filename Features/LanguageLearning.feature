Feature: Extract Language Learning courses and levels
  As a learner
  I want to explore language learning options
  So that I can see all languages, levels, and their counts

  Background:
    Given I am on the Coursera homepage

  Scenario: Display all languages and levels with counts
    When I navigate to Language Learning category
    Then I should extract all available languages
    And I should extract all levels for each language
    And I should display the total count of courses per language and level
