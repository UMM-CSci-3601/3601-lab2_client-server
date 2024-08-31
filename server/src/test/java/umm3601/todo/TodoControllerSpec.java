package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.javalin.http.Context;
import umm3601.Main;
import umm3601.user.UserController;
import umm3601.user.UserDatabase;

public class TodoControllerSpec {
  private static TodoDatabase db;

  private TodoController todoController;

  @Mock
  private Context ctx;

  @Captor
  private ArgumentCaptor<Todo[]> todoArrayCaptor;

  /**
   * Setup the "database" with some example todos and
   * create a TodoController to exercise in the tests.
   *
   * @throws IOException if there are problems reading from the "database" file.
   */
  @BeforeEach
  public void setUp() throws IOException {
    // Reset our mock context and argument captor
    // (declared above with Mockito annotations @Mock and @Captor)
    MockitoAnnotations.openMocks(this);
    // Construct our "database"
    db = new TodoDatabase(Main.TODO_DATA_FILE);
    // Construct an instance of our controller which
    // we'll then test.
    todoController = new TodoController(db);
  }

  /**
   * Confirm that we can get all the users.
   *
   * @throws IOException if there are problems reading from the "database" file.
   */
  @Test
  public void canGetAllTodos() throws IOException {
    // Call the method on the mock context, which doesn't
    // include any filters, so we should get all the todos
    // back.
    todoController.getTodos(ctx);

    // Confirm that `json` was called with all the todos.
    // The ArgumentCaptor<User[]> userArrayCaptor was initialized in the @BeforeEach
    // Here, we wait to see what happens *when ctx calls the json method* in the call
    // userController.getTodos(ctx) and the json method is passed a Todo[]
    // (That's when the Todo[] that was passed as input to the json method is captured)
    verify(ctx).json(todoArrayCaptor.capture());
    // Now that the `Todo[]` that was passed as input to the json method is captured,
    // we can make assertions about it. In particular, we'll assert that its length
    // is the same as the size of the "database". We could also confirm that the
    // particular todos are the same/correct, but that can get complicated
    // since the order of the todos in the "database" isn't specified. So we'll
    // just check that the counts are correct.
    assertEquals(db.size(), todoArrayCaptor.getValue().length);
    assertTrue(db.size() > 0);
  }
}
