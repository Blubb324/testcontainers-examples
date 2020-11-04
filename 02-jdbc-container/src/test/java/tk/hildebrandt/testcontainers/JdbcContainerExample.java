package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public class JdbcContainerExample {

   private static final Logger LOG = LoggerFactory.getLogger(
      JdbcContainerExample.class);
   private static final int POSTGRES_PORT = 5432;
   private static final String POSTGRES_PASSWORD = "test1234";
   private static final String POSTGRES_USER = "test";

   public static void main(String[] args) {
      try {
         runContainer();
      } catch (Exception ignore) {
         //
      }
   }

   private static void runContainer() throws Exception {
//      TODO replace with JdbcContainer
      PostgreSQLContainer databaseContainer = new PostgreSQLContainer("postgres:9.6");
      databaseContainer.withLogConsumer(new Slf4jLogConsumer(LOG));
      databaseContainer.start();
      printDatabaseNameAndVersion(databaseContainer.getJdbcUrl(), databaseContainer.getUsername(), databaseContainer.getPassword());
      databaseContainer.stop();
   }

   private static void printDatabaseNameAndVersion(String jdbcUrl1, String user,
                                                   String password)
      throws SQLException {
      String jdbcUrl = jdbcUrl1;
      try (Connection connection = DriverManager
         .getConnection(jdbcUrl, user, password)) {
         Object databaseProductName =
            connection.getMetaData().getDatabaseProductName();
         Object productVersion =
            connection.getMetaData().getDatabaseProductVersion();
         LOG.info("Greetings from {} in version {}", databaseProductName,
            productVersion);
      }
   }
}
