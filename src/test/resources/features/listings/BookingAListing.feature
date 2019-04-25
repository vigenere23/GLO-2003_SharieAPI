Feature: User can book a listing

  Scenario Outline: Booking an existing listing at an available time
    Given I post a new listing
    And That listing has the next 7 day(s) \(including today) available for booking
    When I book that listing <day> day(s) from now, which is supposed to be available
    Then It should return the status code 204
    And The response should be empty
    And It should remove 1 day from that listing's availabilities
    And The removed day should be the one booked

    Examples:
      | day |
      | 0   |
      | 1   |
      | 2   |
      | 3   |
      | 4   |
      | 5   |
      | 6   |

  Scenario Outline: Booking an existing listing at an unavailable time
    Given I post a new listing
    And That listing has the next 7 day(s) \(including today) available for booking
    When I book that listing <day> day(s) from now, which is NOT supposed to be available
    Then It should return the status code 400
    And The response should contain an error
    And The response error should say "One of the date is not available"

    Examples:
      | day |
      | -3  |
      | -1  |
      | 7   |
      | 10  |

  Scenario: Booking a non-existing listing
    Given There are no existing listings
    When I try to book a non-existing listing \(with id "0")
    Then It should return the status code 404
    And The response should contain an error
    And The response error should say "No listing with id '0' was found"
