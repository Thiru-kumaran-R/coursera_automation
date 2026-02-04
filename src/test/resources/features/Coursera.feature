Feature: Coursera Course Search

  Scenario: Search for Web Development Courses
    Given I navigate to the Coursera homepage
    When I search for "Web Development" courses
    And I filter by "Beginner" level and "English" language
    Then I extract the details of the first 2 courses and save to Excel