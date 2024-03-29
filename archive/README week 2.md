# eshoppers

adpro moment

Week 2 checklist:
### Tutorial
- [x] Create a new branch named ci-cd based on the latest version of your main/master branch.
- [x] Save and commit your latest version of build.gradle.kts
- [x] Save and commit the ci.yml
- [x] Commit the workflow for running the OSSF Scorecard and push it to cicd branch

### Exercise
- [x] If you haven’t done so, please merge the cicd branch to your main/master branch.
- [x] Create a new branch named module-2-exercise based on the latest commit on the main/master branch.
- [x] If the code coverage of your Module 1 Exercise is not 100% yet, try to improve the code coverage of your project. You can do so by adding a new unit test into the project. Then, re-run the test suite and JaCoCo test reporting.
- [x] If you added a new test case, don’t forget to commit it into the module-2-exercise branch.
- [x] Take note of the code coverage improvement after adding the new test case.
- [x] Add another code scanning/analysis tool into your CI/CD process. (SonarCloud or PMD)
- [x] Create a new workflow that triggers the new code scanning/analysis on every push events to every branch. Don’t forget to save and commit the new workflow file into the module-2-exercise branch.
- [x] Run the workflows related to code scanning/analysis and take note of the detected code quality issues.
- [x] Try to address at least one code quality issue by fixing the codebase. Save and commit the fix into the module-2-exercise branch. You may fix more than one issue. Make sure each fix is saved as independent commits from other fixes.
- [x] Verify that the fixed issue(s) does not come up in the next workflow run.
- [x] Implement an auto-deploy mechanism to a PaaS in your online Git repository using either push- or pull-based approach. Push-based approach can be implemented by using a workflow that “pushes” your codebase to the deployment environment, whereas pull-based approach lets the deployment environment to “pull” the code base into the environment. Usually PaaS that follow a pull-based approach require the codebase to be associated with the PaaS and will monitor changes of your codebase remotely.
- [x] Write the new workflow to deploy the codebase to a PaaS of your choice. Don’t forget to save and commit it to module-2-exercise.
- [x] Merge the module-2-exercise branch to the main/master branch.
- [x] Let the workflows run and wait for deployment to be finished.
- [x] Take note of the URL where your app is deployed.
> https://eshoppers-riorio805.koyeb.app/
- [x] (Optional/Challenge) Try to add new test cases to increase your code coverage to 100%. Make sure the new test cases are meaningful. That is, the new test cases verify the correctness of the codebase.

### Reflection
> List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
- Unnecessary Imports
-> Fixed by splitting wildcard into individual imports
- ControlStatementBraces
-> Fixed by adding braces (duh)

> Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

I think the current implementation has met the definition of CI/CD.
There are 3 GitHub Action workflows to automatically test the validity and analyze the code of the project, which fills the Continuous Integration aspect.
There is also a Dockerfile to automatically deploy the project as a Docker image to be able to be deployed in a PaaS, such as Koyeb used in this project.
