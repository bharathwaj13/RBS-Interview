Feature: Update Personal Info


Scenario Outline: Update FirstName in my Account

Given Launch the application using the URL as http://automationpractice.com
And Login using username as <Email> and Password as <Password>
And Click My Personal Information Link
When Update FirstName as <FirstName> using Password as <Password>
Then Successfully updated message appears
And Logout

Examples:
|Email|Password||FirstName|
|someone@example.com|Password123||Interview|


