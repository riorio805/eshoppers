# eshoppers

adpro moment\
Website: [here](https://eshoppers-riorio805.koyeb.app/)

## Reflection 1
> Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests.

I think this workflow is useful, although I think the tests are not thorough enough.
For example in OrderTest, you could test every required status condition and not just "SUCCESS".

As for the positives, this workflow increases my confidence writing clean code and
refactoring already existing code without worrying about changing the behaviour of the program.
Even if it somehow changes, we can revert to a previous commit and add more tests as needed in order to provide consistency (within reason).

I think this workflow also can increase my productivity, which is especially important.
Because of timed assignments, I cannot afford to spend any unnecessary time, and I need to finish them as fast as possible.
This workflow can give accurate and timely feedback so that I don't spend any unnecessary time wandering around/refactoring for no reason.  

>You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests.

- **Fast**\
The tests have successfully followed this principle.
The tests are separated into specific sections, each testing a different portion of the code.
As such, the test can be run separately which decreases the test time,
which allows the tests to be run a lot more frequently without impacting the workflow too much.

- **Isolated / Independent**\
The tests are separated into specific sections, each testing a different portion of the code.
Although in the class OrderServiceImplTest, the tests depend on the previous tests (see testCreateOrderIfAlreadyExists).
To fix this, I need to change the tests so that the testing environment is cleaned after every test,
so that each test can be done in a clean slate.

- **Repeatable**\
OrderServiceImplTest uses mocking, which makes the output of the functions consistent.

- **Self-validating**\
The tests only use assertions without printing anything to the output.

- **Timely/Thorough**\
The tests mostly cover all the possible paths, with only some missing.


## Reflection 2



## Tutorial

### [RED] Make tests for Order model
- [x] Checkout the branch order
- [x] Create OrderTest test suite class in src/test/java/model.
- [x] Commit the changes to Git with this message [RED] Add tests for Order model.
- [x] Run the test. You can see almost all the tests are failed (except Happy path test on setStatus).
- [x] Commit the changes to Git with this message [RED] Add Order model skeleton.
- [x] Push the changes to branch order.

### [GREEN] Implement Order model
- [x] Go to Order model class in src/main/java/model.
- [x] Implement stuff
- [x] Run the test again, all the tests should be passed.
- [x] Commit the changes to Git with this message [GREEN] Implement Order model.
- [x] Push the changes to branch order.

### [REFACTOR] Implement OrderStatus enum
- [x] Create OrderStatus enum in src/main/java/enums.
- [x] Commit the changes to Git with this message [REFACTOR] Add OrderStatus enum.
- [x] Rerun the tests. Make sure the tests are still all passed.
- [x] Commit the changes to Git with this message [REFACTOR] Apply OrderStatus enum check to Order model.
- [x] Rerun the tests again after modifying them. Make sure the tests are still all passed.
- [x] Commit the changes to Git with this message [REFACTOR] Apply OrderStatus enum to Order model tests.
- [x] Push the changes to branch order.

### [RED] Make tests for Order repository
- [x] Create the OrderRepositoryTest test suite class in src/test/java/repository.
- [x] Commit changes to Git with this message: [RED] Add tests for OrderRepository.
- [x] Run the test. You can see all the tests are failed (except findById with invalid ID test).
- [x] Commit the changes to Git with this message [RED] Add OrderRepository skeleton.
- [x] Push the changes to branch order.

### [GREEN] Implement Order repository
- [x] Go to OrderRepository class in src/main/java/repository.
- [x] Implement stuff
- [x] Run the test again, all the tests should be passed.
- [x] Commit the changes to Git with this message [GREEN] Implement OrderRepository class.
- [x] Push the changes to branch order.

### [RED] Make unit tests for Order service
- [x] Create the OrderServiceImplTest test suite class in src/test/java/service.
- [x] Commit changes to Git with this message: [RED] Add tests for OrderServiceImpl.
- [x] Run the test. You can see all the tests are failed (except findById with invalid ID test).
- [x] Commit the changes to Git with this message [RED] Add OrderServiceImpl skeleton.
- [x] Push the changes to branch order.

### [GREEN] Implement Order service to pass tests
- [x] Go to OrderService class in src/main/java/service.
- [x] Implement stuff
- [x] Run the test again, all the tests should be passed.
- [x] Commit the changes to Git with this message [GREEN] Implement OrderRepository class.
- [x] Push the changes to branch order.


## Exercise: Implement a New Feature
- [ ] Continue your work on the order branch.
- [ ] Follow TDD workflow rules and implement:
    - [ ] Payment model class
    - [ ] Payment repository class
    - [ ] Payment service class
    - [ ] Payment by voucher sub-feature code
    - [ ] Cash on Delivery sub-feature code, or Payment by Bank Transfer sub-feature code (depends on your student number)
- [ ] Commit history must contain minimum 3 commits for each class and commits must follow TDD cycle (RED [making tests], GREEN [implementation], REFACTOR). Add prefix on the commit message to signify each phase (for example: “[RED] Add tests for addPayment function”).
- [ ] Copy the controller and templates of Order and Payment, then commit it.\
- [ ] Create pull request/merge request from order branch to main/master branch, but do not merge it. Keep the pull request/merge request open for Bonus 2.


## Bonus 1: TDD to Create Controllers and UI for Order and Payment
- [ ] Implement Thymeleaf templates for Order feature. Commit it with [CHORES] tag in the commit message.
- [ ] Implement functional tests for Order features. Commit it with [RED] tag in the commit message.
- [ ] Implement OrderController in src/main/java/controller. Test it until the functional tests are passed. Then, commit it with the [GREEN] tag in the commit message.
- [ ] Implement functional tests for Payment features. Commit it with [RED] tag in the commit message.
- [ ] Implement PaymentController in src/main/java/controller. Test it until the functional tests are passed. Then, commit it with the [GREEN] tag in the commit message.
- [ ] Implement Thymeleaf templates for Payment feature. Commit it with [CHORES] tag in the commit message.
- [ ] Implement Thymeleaf templates for /order/pay pages. Commit it with [CHORES] tag in the commit message.
- [ ] Push them to the order branch. Do not merge it by yourself.
- [ ] Make sure that the line coverage and branch coverage is 100%.


## Bonus 2: Refactor Other’s Code
- [ ] Add the teammate that will review your code to your repository as a member.
- [ ] Add the teammate that will review your code to your pull request/merge request as a Reviewer.
- [ ] Review your friend’s code and add comments if you find any code smells.
- [ ] Create a new branch named refactor/[NPM] based on the latest version of your teammate’s payment branch. For example, refactor/1606895606.
- [ ] Refactor your teammate’s code, commit those changes in the refactor/[NPM] branch.
- [ ] Test the code after refactoring, make sure other features don’t break.
- [ ] Make a new pull request/merge request from your refactoring branch to order branch.