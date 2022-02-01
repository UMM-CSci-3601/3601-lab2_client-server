package umm3601;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.javalin.http.HttpCode;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * A set of simple functional tests that checks that
 * the simple "demo" paths that we provided as examples
 * in the server actually do something reasonable.
 *
 * The two redirect tests ("/users" and "/todos") don't
 * actually test much besides that an HTML document is
 * returned. Anything else seemed too fragile to me.
 */
public class ServerFunctionalSpec {

  @BeforeAll
  public static void startServer() {
    Server.main(null);
  }

  @AfterAll
  public static void stopServer() {
    Server.stopServer();
  }

  @Test
  public void helloWorks() {
    HttpResponse<String> response = Unirest.get("http://localhost:4567/hello").asString();
    assertThat(response.getStatus()).isEqualTo(HttpCode.OK.getStatus());
    assertThat(response.getBody()).isEqualTo("Hello World");
  }

  @Test
  public void usersRedirects() {
    HttpResponse<String> response = Unirest.get("http://localhost:4567/users").asString();
    assertThat(response.getStatus()).isEqualTo(HttpCode.OK.getStatus());
    assertThat(response.getBody()).contains("<!DOCTYPE html>");
  }

  @Test void todosRedirects() {
    HttpResponse<String> response = Unirest.get("http://localhost:4567/todos").asString();
    assertThat(response.getStatus()).isEqualTo(HttpCode.OK.getStatus());
    assertThat(response.getBody()).contains("<!DOCTYPE html>");
  }

}
