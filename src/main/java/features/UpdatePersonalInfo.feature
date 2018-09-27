Feature: Update Personal Info


Scenario Outline: Update FirstName in my Account

Given Launch the application and login using the URL as http://automationpractice.com
And Click My Personal Information Link
When Update FirstName as <FirstName>
Then Successfully updated message appears
And Logout

Examples:
|FirstName|
|Interview|

