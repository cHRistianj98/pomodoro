[![Build Status](https://github.com/christianj98/pomodoro/actions/workflows/ci.yml/badge.svg)](https://github.com/christianj98/pomodoro/actions/workflows/ci.yml)

# Pomodoro Application - Backend

## Pomodoro Technique

The Pomodoro Technique is a time management method that helps in better organizing work and maintaining high productivity. It involves dividing time into intervals, typically 25 minutes long, called "pomodoros", during which you focus solely on one task, followed by a 5-minute break. After completing 4 pomodoros, a longer break, usually 15-30 minutes, follows.

## Technologies

The Pomodoro Application has been implemented using the following technologies:

- Java 17
- Spring Boot 3
- PostgreSQL

## Functional Requirements
1. User Registration: The user can register using an email and password. There is also an option to register via a Gmail account. Email verification is not required in this app.
2. User Login: The user can log in using their email (username) and password. Login through a Gmail account (OAuth2) is also possible.
3. Login is Optional: Logging in is not required to use the app. An unauthenticated user can see a timer counting down a set number of minutes (e.g., 30 minutes), start and stop the timer, and add tasks. However, the tasks are not saved anywhere in the database. This functionality is available only to logged-in users, and tasks will remain visible until the user's session expires in the browser.
4. Authenticated User: A logged-in user can add tasks, which are saved in the database and later retrieved from the backend. They can also perform all actions available to unauthenticated users.
5. Technology: The project will be developed using Angular (Frontend) and Spring Boot 3 (Backend). For CI/CD, GitHub Actions will be used. The hosting provider is still to be decided.
6. Authentication via JWT: User login and authentication will be handled using JWT (JSON Web Tokens).
7. Task Completion: Users can mark tasks as completed or incomplete.
8. Pomodoro Session: When a task is marked as active and the timer counts down 30 minutes to zero, one Pomodoro session for that task is considered complete. A maximum number of Pomodoro sessions can be set for each task. Pomodoros can also be completed without an active task, in which case the sessions are not tracked.
9. Breaks: The app should allow a 5-minute break between Pomodoro sessions.
## Non-Functional Requirements
1. Centering of Timer and Tasks: The timer and tasks should be centered on the screen.
2. Red Color Scheme: The app should predominantly feature a red color scheme, and a visual element, such as a tomato, should be incorporated somewhere in the design.

## Running the Application

To run the application, Docker and Docker Compose are required. Follow these steps:

1. Clone this repository to your machine.
2. Navigate to the project directory.
3. Run the command `docker-compose up -d` in your terminal.
4. Once the containers are up and running, the application will be accessible at `http://localhost:8080`.

## Database Configuration

The application requires access to a PostgreSQL database. To configure the database, set the environment variable `pomodoro.db.password` to the password for the database. You can do this by setting the appropriate value in the `docker-compose.yml` file or directly in your operating system.

If setting the environment variable in your operating system, ensure that you have defined the `pomodoro.db.password` variable with the appropriate value.

## Swagger endpoints

You can reach Swagger endpoints using the following link:
[Link to Swagger](localhost:8080/swagger-ui.html)

## Contact

For any questions or issues with the application, please contact our technical team.