Feature: Order TShirt


Scenario: Order Tshirt and verify Order History

Given Launch the application and login using the URL as http://automationpractice.com
And Select the Tshirt by adding to Cart
When Checkout the Selected Tshirt by completing Payment
Then Verify Order in Order History
And Logout

