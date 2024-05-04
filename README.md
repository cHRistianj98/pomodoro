# Pomodoro Application - Backend

## Pomodoro Technique

The Pomodoro Technique is a time management method that helps in better organizing work and maintaining high productivity. It involves dividing time into intervals, typically 25 minutes long, called "pomodoros", during which you focus solely on one task, followed by a 5-minute break. After completing 4 pomodoros, a longer break, usually 15-30 minutes, follows.

## Technologies

The Pomodoro Application has been implemented using the following technologies:

- Java 17
- Spring Boot
- PostgreSQL

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