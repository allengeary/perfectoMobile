@mlo_low
Feature: MetLife Feature
  @mlo_TC001
  Scenario: Group Only user with Dental DNW Only Participating Product Validation : To validate that when Group user with Dental DNW Only Participating Product logs into desktop,the Dental LOB is displayed on MyAccounts page
    Given I open browser to webpage "qa.phoenix.ead.metlife.com"
    Then I log out of MetLife Online
    When I enter "paperless1" to "mlo.text.userid"
    And I enter "metlife23" to "mlo.text.password"
    Then I click on "mlo.button.login"
    Then I wait "60" seconds for "mlo.label.dentalHeader" to appear
    
  @mlo_TC003
  Scenario: Verify that Find a dentist link will be available for Dental LOB
    Given I open browser to webpage "qa.phoenix.ead.metlife.com"
    Then I log out of MetLife Online
    When I enter "abaudamb1@ml.com" to "mlo.text.userid"
    And I enter "metlife1" to "mlo.text.password"
    Then I click on "mlo.button.login"
    Then I wait "60" seconds for "mlo.label.dentalHeader" to appear
    Then I scroll down
    Then I wait "10" seconds for "mlo.link.findDentist" to appear
    
  @mlo_TC004
  Scenario: Verify dental office will be available for Dental LOB
    Given I open browser to webpage "qa.phoenix.ead.metlife.com"
    Then I wait for "5" seconds
    When I enter "abaudamb1@ml.com" to "mlo.text.userid"
    And I enter "metlife1" to "mlo.text.password"
    Then I click on "mlo.button.login"
    
  @mlo_TC005
  Scenario: Verify whether the short term disability participating experience displays Claim Number,on clicking which,the user is re-directed to the cliam detail page for that claim number in the product application
    Given I open browser to webpage "qa.phoenix.ead.metlife.com"
    Then I wait for "5" seconds
    When I enter "verizon68" to "mlo.text.userid"
    And I enter "metlife50" to "mlo.text.password"
    Then I click on "mlo.button.login"    
 
  @mlo_TC006
  Scenario: Verify whether the short term disability participating experience displays Claim Number,on clicking which,the user is re-directed to the cliam detail page for that claim number in the product application
    Given I open browser to webpage "qa.phoenix.ead.metlife.com"
    Then I wait for "5" seconds
    When I enter "optlife1" to "mlo.text.userid"
    And I enter "metlife1" to "mlo.text.password"
    Then I click on "mlo.button.login"
    
  @mlo_TC007
  Scenario: Verify whether the short term disability participating experience displays Claim Number,on clicking which,the user is re-directed to the cliam detail page for that claim number in the product application
    Given I open browser to webpage "qa.phoenix.ead.metlife.com"
    Then I wait for "5" seconds
    When I enter "randstad6599" to "mlo.text.userid"
    And I enter "metlife1" to "mlo.text.password"
    Then I click on "mlo.button.login"    
    
  @mlo_TC008
  Scenario: Verify whether the short term disability participating experience displays Claim Number,on clicking which,the user is re-directed to the cliam detail page for that claim number in the product application
    Given I open browser to webpage "qa.phoenix.ead.metlife.com"
    Then I wait for "5" seconds
    When I enter "consolidated_retail15" to "mlo.text.userid"
    And I enter "metlife1" to "mlo.text.password"
    Then I click on "mlo.button.login"    
    
  @mlo_TC009
  Scenario: Verify whether the short term disability participating experience displays Claim Number,on clicking which,the user is re-directed to the cliam detail page for that claim number in the product application
    Given I open browser to webpage "qa.phoenix.ead.metlife.com"
    Then I wait for "5" seconds
    When I enter "ushretail4@ml.com" to "mlo.text.userid"
    And I enter "metlife1" to "mlo.text.password"
    Then I click on "mlo.button.login"      
    
    
  @mlo_TC010
  Scenario: Verify whether the short term disability participating experience displays Claim Number,on clicking which,the user is re-directed to the cliam detail page for that claim number in the product application
    Given I open browser to webpage "qa.phoenix.ead.metlife.com"
    Then I wait for "5" seconds
    When I enter "verizon68" to "mlo.text.userid"
    And I enter "metlife50" to "mlo.text.password"
    Then I click on "mlo.button.login"      