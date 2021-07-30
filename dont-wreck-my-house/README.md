# Don't Wreck My House Plan

## General Requirements
* The administrator may view existing reservations for a host.
  * [x] The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
  * [x] If the host is not found, display a message.
  * [x] If the host has no reservations, display a message.
  * [x] Show all reservations for that host.
  * [x] Show useful information for each reservation: the guest, dates, totals, etc.
  * [ ] Sort reservations in and appealing meaningful way.
  
* The administrator may create a reservation for a guest with a host.
  * Books accommodations for a guest at a host.
    * [x] The user may enter a value that uniquely identifies a guest or they can search for a guest and pick one out of a list.
    * [x] The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
    * [x] Show all future reservations for that host so the administrator can choose available dates.
    * [x] Enter a start and end date for the reservation.
    * [x] Calculate the total, display a summary, and ask the user to confirm. The reservation total is based on the host's 
      standard rate and weekend rate. For each day in the reservation, determine if it is a weekday or a weekend. If it's a 
      weekday, the standard rate applies. If it's a weekend, the weekend rate applies.
    * [x] On confirmation, save the reservation.
  * Validation
    * [x] Guest, host, and start and end dates are required.
    * [x] The guest and host must already exist in the "database". Guests and hosts cannot be created.
    * [x] The start date must come before the end date.
    * [x] The reservation may never overlap existing reservation dates.
    * [x] The start date must be in the future.
  
* The administrator may edit existing reservations.
  * Edits an existing reservation.
    * [x] Find a reservation.
    * [x] Start and end date can be edited. No other data can be edited.
    * [x] Recalculate the total, display a summary, and ask the user to confirm. 
  * Validation
    * [x] Guest, host, and start and end dates are required.
    * [x] The guest and host must already exist in the "database". Guests and hosts cannot be created.
    * [x] The start date must come before the end date.
    * [x] The reservation may never overlap existing reservation dates.
  
* The administrator may cancel a future reservation.
  * Cancel a future reservation.
    * [x] Find a reservation.
    * [ ] Only future reservations are shown.
    * [x] On success, display a message.
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
  * int id
  * String firstName
  * String lastName
  * String email
  * String phone #
  * String state
### Host  
* The accommodation provider. Someone who has a property to rent per night. Host data is in ./data/hosts.csv.
* A rental property. In Don't Wreck My House, Location and Host are combined. The application enforces a limit
  on one Location per Host, so we can think of a Host and Location as a single thing.  
* Fields:
  * id (system-generated)
  * String lastName
  * String email 
  * String phone # 
  * String address
  * String city
  * String state
  * String postalCode
  * BigDecimal standardRate 
  * BigDecimal weekendRate
  
### Reservation
* One or more days where a Guest has exclusive access to a Location (or Host). Reservation data is in ./data/reservations.
* A host reservation file name has the format: {host-id}.csv.
* Fields: 
  * int id
  * LocalDate startDate
  * LocalDate endDate
  * int guestId
  * BigDecimal total
  * String hostId

## Data
* Custom DataException
* Perform tests for public methods

### GuestFileRepository (~1 hr)
* [x] inject file
* [x] GuestRepository interface
* [x] public findById()  
* [x] public findAll()
  * [x] BufferedReader, FileReader, catch IOException, swallow FileNotFound
* [x] private deserialize
  * [x] split line, check for length, return Guest
* [x] Create GuestFileRepositoryTest
* [x] test public methods

### HostFileRepository (~1 hr)
* [x] inject file
* [x] HostRepository interface
* [x] public findById()  
* [x] public findAll()
  * [x] BufferedReader, FileReader, catch IOException, swallow FileNotFound
* [x] private deserialize()
  * [x] split line, check for length, return Host
* [x] Create HostFileRepositoryTest 
* [x] test public methods

### ReservationFileRepository (~1.5 hrs)
* [x] inject directory
* [x] ReservationRepository interface
* [x] public findByHostId()  
* [x] public add()
* [x] public update()
* [x] public deleteById()  
* [x] private getFilePath()
* [x] private writeAll()
* [x] private serialize()
  * [x] StringBuilder, DELIMITER
* [x] private deserialize()
  * [x] split line, check for length, return Host
* [x] Create ReservationRepositoryTest
* [x] test public methods

## Domain
* perform tests for public methods with repo doubles
* test positive and negative conditions
* tests for GuestService and HostService should be very similar

### GuestService (~1.5 hrs)
* [x] inject repository
* [x] public findById()
* [x] public findByLastName()
* [x] create service test and test methods

### HostService (~1.5 hrs)
* [x] inject repository
* [x] public findById()
* [x] public findByLastName()
* [x] create service test and test methods

### ReservationService (~2.5 hrs)
* [x] inject repository
* [ ] public find()
* [x] public add()
  * [x] calculate $
  * [x] call repository.add(reservation)
* [x] public update()
  * [x] calculate $  
  * [x] call repository.update(reservation)
* [x] public delete()
* [x] public calculateTotal()  
* [x] private validate()
* [x] create service test and test methods

### Response 
* [x] private isSuccess()
* [x] public getErrorMessages()
* [x] public addErrorMessage()

### Result
* [x] public getPayload()
* [x] public setPayload()

## UI

### MainMenuOption (~15 min)
* [x] Exit
* [x] View Reservations for Host
* [x] Make a Reservation
* [x] Edit a Reservation
* [x] Cancel a Reservation

### ConsoleIO (~30 min)
* [x] Scanner
* [x] Invalid input messages
* [x] public print()
* [x] public println()
* [x] public printf()
* [x] public readString()
* [x] public readRequiredString()
* [x] public readLocalDate()
* [X] readInt() and overloaded sibling 
* [x] public readBigDecimal()
* any other read methods with necessary overloads and requirements

### Controller (~1.5 hrs)
* [x] inject services
* [x] inject view
* [x] public run()
* [x] private runAppLoop()
* [x] viewReservationsByHost()
* [x] createReservation()
* [x] editReservation()
* [x] deleteReservation()  
* [x] add support methods as necessary

### View (~2.5 hrs)
* [x] public selectMainMenuOption()
* [x] display methods
* [x] choose methods (guest, host, reservation)  
* methods for each menu option


### Stretch Goals
* [x] add a guest
* [x] add a host
* [ ] update a guest
  * can only change email, phone, and state
  * update any reservations associated with guest
* [ ] update a host
  * can update everything except last name
  * update any reservations
* [ ] delete a guest
* [ ] delete a host
* [ ] display reservations for a guest