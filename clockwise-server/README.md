# ClockWise Server

Database configuration:

    Postgres database:
        - name: clockwisedb
        - user: postgres
        - password: !qaz@wsx
        - address: jdbc:postgresql://127.0.0.1:5432/clockwisedb

        Everything can be changed at application.properties file

    Super Admin:

        Default user created at the first start of the server, credentials can be found at application.properties file.

    Build and Launch without IDE:

        1. Open cmd.
        2. Move to clockwise-server directory
        3. Build app with "gradle.bat build" command (./gradlew build for Linux)
        4. Launch server with: "java -jar build\libs\clockwise-server-0.1.0.jar" command (server by default starts over port 8080)
