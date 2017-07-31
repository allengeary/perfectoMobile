@mapp_low
Feature: MetLife Feature
  @mapp_TC-001
  Scenario Outline: MetLife
    Given I reset and start metlife application
    And I log into MetLife using "<username>" and "<password>" and I am "<expected_name>"
    Then I wait "60" seconds for "button.lifeInsurance" to appear
    Then I click on "button.lifeInsurance"
    Then I wait "10" seconds for "label.lifeInsuranceHeader" to appear
    Then I click on "button.backButton"
    Then I log out of MetLife
    Examples: { 'datafile' : 'src/main/resources/data/testData.xls', sheetName: 'TC-001' }
    
  @mapp_TC-002
  Scenario Outline: MetLife
    Given I reset and start metlife application
    And I log into MetLife using "<username>" and "<password>" and I am "<expected_name>"
    Then I log out of MetLife
    Examples: { 'datafile' : 'src/main/resources/data/testData.xls', sheetName: 'TC-002' }
    
  @mapp_TC-003
  Scenario Outline: MetLife
    Given I reset and start metlife application
    And I log into MetLife using "<username>" and "<password>" and I am "<expected_name>"
    Then I click on "button.disabilityInsurance"
    Then I wait "10" seconds for "label.disabilityInsuranceHeader" to appear
    Then I click on "button.backButton"
    Then I log out of MetLife
    Examples: { 'datafile' : 'src/main/resources/data/testData.xls', sheetName: 'TC-003' }
    
  @mapp_TC002
  Scenario Outline: MetLife
    Given I reset and start metlife application
    And I log into MetLife using "<username>" and "<password>" and I am "<expected_name>"
    Then I log out of MetLife
    Examples: { 'datafile' : 'src/main/resources/data/testData.xls', sheetName: 'TC002' }