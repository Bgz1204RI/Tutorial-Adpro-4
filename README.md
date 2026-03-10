### Reflection — Module 04 (TDD, Refactoring, and Service Layer)

During this module, I implemented the Order feature using a Test-Driven Development (TDD) approach. The development process followed the classic **Red–Green–Refactor** cycle, where tests are written first, followed by the minimal implementation needed to make them pass, and finally improvements to the code structure without changing behavior.

The first step involved writing unit tests for the **Order model**. These tests validated behaviors such as ensuring an order cannot be created with an empty product list, verifying default status values, checking valid and invalid status assignments, and confirming that status updates behave correctly. Initially, all tests failed because the model had not yet been implemented. After implementing the constructors and validation logic in the `Order` class, the tests began to pass.

Next, I refactored the code by introducing an **OrderStatus enum**. Originally, order statuses were hardcoded strings scattered throughout the code. This made the code harder to maintain and more error-prone. By replacing these string literals with an enum, all possible order statuses are centralized in a single place, improving readability, maintainability, and reducing the risk of invalid values.

After the model was completed, I implemented and tested the **OrderRepository**. The repository stores orders using an in-memory list and provides methods to save orders, find orders by ID, and retrieve orders by author. The tests ensured that saving an order could both create and update entries, and that lookups by ID and author behaved correctly, including cases where the requested data does not exist.

Finally, I implemented the **OrderService layer**, which acts as the business logic layer between controllers and repositories. This service handles operations such as creating orders, updating order status, and retrieving orders. Mockito was used to mock the repository in order to isolate and test the service logic independently. The tests verified both valid operations and failure scenarios, such as attempting to update the status of a non-existent order.

Overall, this module demonstrated how TDD can guide development by ensuring functionality is verified through tests before implementation. The introduction of the enum also highlighted the importance of refactoring to improve code quality and maintainability without changing external behavior.
