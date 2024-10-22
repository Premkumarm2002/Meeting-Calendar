# Calendar Assistant API

## Overview
The Calendar Assistant API is a Spring Boot application that helps users manage their calendars by allowing them to book meetings, find free time slots, and check for scheduling conflicts. This API is built following RESTful principles and utilizes JPA for data persistence.

## Features
- Book meetings for individual users
- Find available time slots between two users
- Identify meeting conflicts for specified users
- REST-compliant endpoints, easily testable with Postman

## API Endpoints
1. **Book Meeting**
   - **POST** `/api/meetings/book`
   - Request Body:
     ```json
     {
         "owner": "sandy",
         "startTime": "2024-10-22T10:00:00",
         "endTime": "2024-10-22T10:30:00",
         "title": "Team Meeting"
     }
     ```

2. **Get Free Slots**
   - **GET** `/api/meetings/free-slots`
   - Query Parameters:
     - `user1`: Username of the first user
     - `user2`: Username of the second user
     - `durationInMinutes`: Duration of the meeting
   - Example:
     ```
     GET /api/meetings/free-slots?user1=sandy&user2=john&durationInMinutes=30
     ```

3. **Find Conflicts**
   - **POST** `/api/meetings/conflicts`
   - Request Body:
     ```json
     {
         "owner": "sandy",
         "startTime": "2024-10-22T10:20:00",
         "endTime": "2024-10-22T10:40:00"
     }
     ```
How to Run:
1. Clone the repository:

git clone https://github.com/Premkumarm2002/Meeting-Calendar.git
cd Meeting-Calendar

2. Run the application:

mvn spring-boot:run

3. Access the API at http://localhost:8080/api/meetings.
