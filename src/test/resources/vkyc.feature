@vkyc_mobile
Feature: CIM finance Functionality

  @e2e @vkyc
  Scenario: verify end to end scenarios of VKYC functionality
    Given User Launch VKyc Application
    And Trigger VCIP
    When User enter Url and tap on Invoke SDK
    And User clicks on Get Started button
    When User doing PAN verification
    And User added Occupation details
    And User doing Video call verification


