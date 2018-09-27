Feature: Order TShirt


Scenario Outline: Order Tshirt and verify Order History

Given Launch the application using the URL as http://automationpractice.com
And Login using username as <Email> and Password as <Password>
And Select the Tshirt by adding to Cart
When Checkout the Selected Tshirt by completing Payment
Then Verify Order in Order History
And Logout

Examples:
|Email|Password|
|someone@example.com|Password123|
