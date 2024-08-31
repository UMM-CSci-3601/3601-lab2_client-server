package umm3601.todo;

import io.javalin.http.Context;

public class TodoController {
  private TodoDatabase todoDatabase;

  public TodoController(TodoDatabase db) {
      this.todoDatabase = db;
  }

  public void getTodos(Context ctx) {
    Todo[] todos = todoDatabase.listTodos(ctx);
    ctx.json(todos);
  }

}
