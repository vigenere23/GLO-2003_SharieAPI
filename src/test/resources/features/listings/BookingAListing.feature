Feature: User can book a listing

  Scenario Outline: Booking an existing listing at an available time
    Given There's a booking existing with a specific ID and with the next 7 days \(including today) available to book
    When I book that listing <day> day(s) from now, which is in the range of the next 7 days \(including today)
    Then It should return the status code 201
    And The response should be empty
    And It should make that day no more available in that listing's availabilities

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
    Given There's a booking existing with a specific ID and with the next 7 days \(including today) available to book
    When I book that listing <day> day(s) from now, which is NOT in the range of the next 7 days \(including today)
    Then It should return the status code 400
    And The response should contain an error
    And The error should tell me that the listing is not available at that specific date

    Examples:
      | day |
      | -3  |
      | -1  |
      | 7   |
      | 10  |

  Scenario: Booking a non-existing listing at any time
    Given There are no existing listings
    When I try to book any listing by any ID at any time
    Then It should return the status code 400
    And It should return an error
    And The error should tell me that the listing does not exist
