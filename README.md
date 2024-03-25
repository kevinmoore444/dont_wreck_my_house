# dont_wreck_my_house
Dev10 Midterm Assessment

Introduction
Using everything you've learned in Java Fundamentals, design, implement, and test an application that allows a user to reserve accommodations for a guest with a host location. Don't Wreck My House is similar to Airbnb. A guest chooses a place to stay for a specific date range. If the host location is available, it may be reserved by the guest. Reserved locations are not available to guests during reserved dates.

High-Level Requirements
The application user is an accommodation administrator. They pair guests to locations to make reservations.

The administrator may view existing reservations for a host location.
The administrator may create a reservation for a guest with a host location.
The administrator may edit existing reservations.
The administrator may cancel a future reservation.
Glossary
User
A user is a person who can be either a guest or a host, or both. They are explicitly defined in the database.
Guest
A customer. Someone who wants to book a place to stay. The concept of a "guest" isn't explicit. You're only a guest if you're a user attached to a reservation.
Host
An accommodation provider. Someone who has a property to rent. The concept of a "host" isn't explicit. You're only a host if you're a user attached to a location.
State
A US state. Explicitly defined in the database.
Location
A rental property. A host is a user attached to a location. Explicitly defined in the database.
Reservation
One or more days where a guest has exclusive access to a location. Explicitly defined in the database.
Administrator
The application user. Guests and hosts don't book their own reservations. The administrator does it for them.



Requirements
There are four required scenarios.

View Reservations for Host Location
Display all reservations for a host location.

The administrator may enter a value that uniquely identifies a host (email) or they can search for a host and pick one out of a list.
If the host is not found, display a message.
If the host has no reservations, display a message.
Display the location.
Display all reservations for that host.
Display useful information for each reservation: the guest, dates, totals, etc.
Sort reservations in a meaningful way.
Make a Reservation
Books accommodations for a guest at a location.

The administrator may enter a value that uniquely identifies a guest (email) or they can search for a guest and pick one out of a list.
The administrator may enter a value that uniquely identifies a host (email) or they can search for a host and pick one out of a list.
Display the location.
Display all future reservations so the administrator can choose available dates.
Enter a start and end date for the reservation.
Calculate the total, display a summary, and ask the administrator to confirm. The reservation total is based on the host's standard rate and weekend rate. For each day in the reservation, determine if it is a weekday (Sunday, Monday, Tuesday, Wednesday, or Thursday) or a weekend (Friday or Saturday). If it's a weekday, the standard rate applies. If it's a weekend, the weekend rate applies.
On confirmation, save the reservation.
Validation

Guest, host, and start and end dates are required.
The guest and host must already exist in the database.
The start date must come before the end date.
The reservation may never overlap existing reservation dates.
The start date must be in the future.
Edit a Reservation
Edits an existing reservation.

Find a reservation.
Start and end date can be edited. No other data can be edited.
Recalculate the total, display a summary, and ask the administrator to confirm.
Validation

Guest, host, and start and end dates are required.
The guest and host must already exist in the database.
The start date must come before the end date.
The reservation may never overlap existing reservation dates.
Cancel a Reservation
Cancel a future reservation.

Find a reservation.
Only future reservations are shown.
On success, display a message.
Validation

You cannot cancel a reservation that's in the past.
Technical Requirements
All financial math must use BigDecimal.
Dates must be LocalDate, never strings.

Testing
Create a set_known_good_state() stored procedure in your testing schema.

Delete all data.
Set auto-incremented keys back to one for test reproducibility.
Add just a few records to each table and make names predictable and generic.
All data components must be thoroughly tested and establish known good state. Never test with "production" data.

All domain components must be thoroughly tested using test doubles. Do not use database repositories for domain testing.

User interface testing is not required.

Deliverables
A schedule of concrete tasks. Tasks should contain time estimates and should never be more than 4 hours.
Class diagram (informal is okay)
Sequence diagrams or flow charts (optional, but encouraged)
A Java Maven project that contains everything needed to run without error
Test suite
Stretch Goals
Allow the user to create, edit, and delete guests.
Allow the user to create, edit, and delete hosts.
Display reservations for a guest.
Display all reservations for a state, city, or zip code.
