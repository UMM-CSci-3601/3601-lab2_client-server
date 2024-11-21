package umm3601.todo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.http.Context;

public class TodoDatabase {
  private Todo[] allTodos;

    public TodoDatabase(String todoDataFile) throws IOException {
    // The `.getResourceAsStream` method searches for the given resource in
    // the classpath, and returns `null` if it isn't found. We want to throw
    // an IOException if the data file isn't found, so we need to check for
    // `null` ourselves, and throw an IOException if necessary.
    InputStream resourceAsStream = getClass().getResourceAsStream(todoDataFile);
    if (resourceAsStream == null) {
      throw new IOException("Could not find " + todoDataFile);
    }
    InputStreamReader reader = new InputStreamReader(resourceAsStream);
    // A Jackson JSON mapper knows how to parse JSON into sensible 'User'
    // objects.
    ObjectMapper objectMapper = new ObjectMapper();
    // Read our user data file into an array of Todo objects.
    allTodos = objectMapper.readValue(reader, Todo[].class);
  }

  public Integer size() {
    return allTodos.length;
  }

  /**
   * Get an array of all the todos satisfying the queries in the params.
   *
   * @param queryParams map of key-value pairs for the query
   * @return an array of all the users matching the given criteria
   */
  public Todo[] listTodos(Context ctx) {
    return allTodos;
  }

}
