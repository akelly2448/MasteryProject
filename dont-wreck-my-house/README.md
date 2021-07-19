# Don't Wreck My House Plan

## General Requirements
* The administrator may view existing reservations for a host.
  * [ ] The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
  * [ ] If the host is not found, display a message.
  * [ ] If the host has no reservations, display a message.
  * [ ] Show all reservations for that host.
  * [ ] Show useful information for each reservation: the guest, dates, totals, etc.
  * [ ] Sort reservations in a meaningful way.
  
* The administrator may create a reservation for a guest with a host.
  * Books accommodations for a guest at a host.
    * [ ] The user may enter a value that uniquely identifies a guest or they can search for a guest and pick one out of a list.
    * [ ] The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
    * [ ] Show all future reservations for that host so the administrator can choose available dates.
    * [ ] Enter a start and end date for the reservation.
    * [ ] Calculate the total, display a summary, and ask the user to confirm. The reservation total is based on the host's 
      standard rate and weekend rate. For each day in the reservation, determine if it is a weekday or a weekend. If it's a 
      weekday, the standard rate applies. If it's a weekend, the weekend rate applies.
    * [ ] On confirmation, save the reservation.
  * Validation
    * [ ] Guest, host, and start and end dates are required.
    * [ ] The guest and host must already exist in the "database". Guests and hosts cannot be created.
    * [ ] The start date must come before the end date.
    * [ ] The reservation may never overlap existing reservation dates.
    * [ ] The start date must be in the future.
  
* The administrator may edit existing reservations.
  * Edits an existing reservation.
    * [ ] Find a reservation.
    * [ ] Start and end date can be edited. No other data can be edited.
    * [ ] Recalculate the total, display a summary, and ask the user to confirm. 
  * Validation
    * [ ] Guest, host, and start and end dates are required.
    * [ ] The guest and host must already exist in the "database". Guests and hosts cannot be created.
    * [ ] The start date must come before the end date.
    * [ ] The reservation may never overlap existing reservation dates.
  
* The administrator may cancel a future reservation.
  * Cancel a future reservation.
    * [ ] Find a reservation.
    * [ ] Only future reservations are shown.
    * [ ] On success, display a message.
  * Validation
    * [ ] You cannot cancel a reservation that's in the past.

## Technical Requirements
* Must be a Maven project.
* Spring dependency injection configured with either XML or annotations.
* All financial math must use BigDecimal.
* Dates must be LocalDate, never strings.
* All file data must be represented in models in the application.
* Reservation identifiers are unique per host, not unique across the entire application. Effectively, the combination 
  of a reservation identifier and a host identifier is required to uniquely identify a reservation.

## Models (~1 hr)

### Guest 
* A customer. Someone who wants to book a place to stay. Guest data is in ./data/guests.csv.
* Fields:
  * id
  * firstName
  * lastName
  * email
  * phone #
  * state
### Host  
* The accommodation provider. Someone who has a property to rent per night. Host data is in ./data/hosts.csv.
* Fields:
  *id
  * lastName
  * email 
  * phone # 
  * address 
  * city 
  * state 
  * postalCode 
  * standardRate 
  * weekendRate
### Location
* A rental property. In Don't Wreck My House, Location and Host are combined. The application enforces a limit 
  on one Location per Host, so we can think of a Host and Location as a single thing.
* For now, we will not create Location, but we will ask if it would be smart to create and pass as a field in Host for
  better chance for future development.
* Fields:
  * address
  * city
  * state
  * postalCode
### Reservation
* One or more days where a Guest has exclusive access to a Location (or Host). Reservation data is in ./data/reservations.
* A host reservation file name has the format: {host-identifier}.csv.  
* Fields: 
  *id
  * startDate
  * endDate
  * guestId
  * total

## Data
* Custom DataException

### GuestFileRepository (~1 hr)
* inject file
* GuestRepository interface
* public findAll()
* public findById()
* private deserialize

### HostFileRepository (~1 hr)
* inject file
* HostRepository interface
* public findAll()
* public findById()
* private deserialize()

### ReservationFileRepository (~1.5 hrs)
* inject directory
* ReservationRepository interface
* public find()  
* public add()
* public update()
* private getFilePath()
* private writeAll()
* private serialize()
* private deserialize()

## Domain

### GuestService (~1.5 hrs)
* inject repository
* public findAll()
* public findById()
* private validate()

### HostService (~1.5 hrs)
* inject repository
* public findAll()
* public findById()
* private validate()

### ReservationService (~2.5 hrs)
* inject repository
* public find()
* public add()
* public update()
* private validate()

### Response 
* private isSuccess()
* public getErrorMessages()
* public addErrorMessage()

### Result
* public getPayload()
* public setPayload()

## UI

### MainMenuOption (~15 min)
* Exit
* View Reservations for Host
* Make a Reservation
* Edit a Reservation
* Cancel a Reservation

### ConsoleIO (~30 min)
* Scanner
* Invalid input messages
* public print()
* public println()
* public printf()
* public readString()
* public readRequiredString()
* any other read methods with necessary overloads and requirements

### Controller (~1.5 hrs)
* inject services
* inject view
* public run()
* private runAppLoop()
* methods for each menu option
* add support methods as necessary

### View (~2.5 hrs)
* public selectMainMenuOption()
* methods for each menu option