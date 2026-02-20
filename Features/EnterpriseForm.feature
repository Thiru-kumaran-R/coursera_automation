Feature: Validate Ready to Transform form under For Enterprise
  As an enterprise user
  I want to fill the Ready to Transform form
  So that I can verify error messages for invalid inputs


  Scenario: Capture error message for invalid email input
    When I navigate to "For Enterprise"
    And I go to "Courses for Campus" under Product
    And I scroll down to the form
    And I fill the form with invalid email input
    And I submit the form
    Then I should capture and display the error message