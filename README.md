# Final Project Deliverable — SE333

## Project Structure

```
final_project_se333_chris/
├── projectAnalyzed/          # Spring Petclinic project analyzed and modified
├── CodeBase/                 # Your source code / analysis code (if different)
├── .github/prompts/          # Prompts used for MCP/agent interaction
├── README.md                 # This documentation (technical + reflection report)
├── report/
│   └── reflection.pdf        # Reflection report (generated from markdown)
└── demo/
    └── final_presentation.mp4 # Demo video (optional for asynchronous)
```

---

## 1. Technical Documentation

### 1.1 MCP Tool / API Documentation

#### Coverage Heatmap Tool (se333-mcp-server/coverage_heatmap)

This tool reads a JaCoCo XML coverage report and returns per-class coverage statistics.

**Inputs:**
- `jacoco_path` (string, optional) — path to JaCoCo XML report (default: `target/site/jacoco/jacoco.xml`).

**Outputs:**
- JSON array of objects, each containing:
  - `class`: class path (e.g. `org/springframework/samples/petclinic/owner/PetController`)
  - `line_coverage`: number (0–100)

**Example usage:**

```bash
# Run unit tests to generate JaCoCo report
mvn test

# Call the coverage heatmap tool (via MCP tool wrapper)
# (In this repo, we used the tool via the agent interface.)
```

---

### 1.2 Installation & Configuration Guide

#### Prerequisites

- Java 17+ JDK
- Maven 3.8+ (or use bundled `mvnw`)
- Git

#### Setup Steps

1. Clone the project:

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
```

2. Run unit tests + coverage:

```bash
./mvnw test
```

3. Generate coverage report (JaCoCo):

```bash
./mvnw test
# Report is generated at target/site/jacoco/jacoco.xml
```

#### Running the MCP Coverage Heatmap Tool

From within the workspace in this project, run:

```bash
# (This assumes you have the MCP tooling environment configured)
# Example command (tool-specific wrapper may vary):
# se333-mcp-server coverage_heatmap --jacoco_path target/site/jacoco/jacoco.xml
```

> **Note:** In this assignment environment we invoked the tool using the `se333-mcp-server/coverage_heatmap` tool interface.

---

### 1.3 Troubleshooting & FAQ

**Q: Tests fail with `No BindingResult for attribute: pet`**
- This happens when controller validation is invoked but the model attribute binding does not create a `BindingResult`.
- Fix: Ensure the controller method signature is `...(@Valid Pet pet, BindingResult result, ...)` and that the form binding uses the correct model attribute name (`pet`).

**Q: `spring-javaformat:validate` fails after editing.
**
- Run `./mvnw spring-javaformat:apply` to auto-format, then re-run tests.

**Q: JaCoCo report not generated?**
- Ensure `mvn test` completes successfully. The report is at `target/site/jacoco/jacoco.xml`.

---

## 2. Reflection Report (2 pages max)

### Introduction

This project focused on improving test reliability and coverage for the Spring Petclinic sample application by fixing a failing controller test case and validating coverage using a custom MCP heatmap tool.

### Methodology

1. **Reproduce failure:** Run `mvn test` and observe the failing test `PetControllerTests.testProcessUpdateFormWithDuplicateName`.
2. **Diagnose root cause:** Inspect controller and test; discovered validation logic incorrectly checked duplicate pet names using `owner.getPet(name, false)` which ignored the current pet ID and caused binding step to be skipped.
3. **Implement fix:** Updated `PetController.processUpdateForm` to explicitly iterate owner pets and ignore the current pet ID when checking for duplicates.
4. **Validate:** Run `mvn test` and confirm all tests pass.
5. **Measure coverage:** Use the `se333-mcp-server/coverage_heatmap` tool on `target/site/jacoco/jacoco.xml`.

### Results & Discussion

#### Coverage Improvement Patterns
- After fixing the failing test, the project builds and tests run cleanly.
- **Coverage heatmap output (example):**
  - `PetController` → 94.55% line coverage
  - `Owner`, `VisitController`, and most domain objects show 95–100% coverage.
- The change improved confidence that validation logic is covered by automated tests; it also removed a failing test that prevented CI from succeeding.

#### Insights from AI-Assisted Development
- The AI agent quickly located the failing test and guided modifications within the controller.
- Prompting the agent to run targeted tests (e.g., single JUnit method) sped up the feedback loop.
- Formatting compliance (Spring Java Format) was enforced automatically and required an extra build step.

#### Recommendations for Future Enhancements
- Add explicit tests for additional validation cases (e.g., null birthdate, whitespace-only names).
- Enhance the coverage heatmap tool to report *per-branch* coverage or directly highlight uncovered lines.
- Automate running the coverage heatmap after tests and generate a summary report (e.g., JSON → markdown).

---

## 3. Presentation Notes (for live/recorded demo)

**Project Overview:**
- Goal: fix failing test + validate coverage using an MCP tool.
- Stack: Java 25, Spring Boot 4, JUnit 5, Maven, JaCoCo.

**Demonstration Flow:**
1. Run `mvn test` to reproduce failure.
2. Show failing assertion and controller logic.
3. Edit `PetController` to fix the duplicate-name validation.
4. Re-run `mvn test` to show tests passing.
5. Run coverage heatmap tool and interpret output.

**Metric Improvement Showcase:**
- Before: tests failed due to missing BindingResult.
- After: all tests pass and PetController coverage is high.

**Reflection:**
- AI assistance was effective for locating failing assertions and guiding test execution.
- Manual reasoning was required to understand binding/model attribute behavior in Spring MVC.
- Future work: automating discovery of missing bindings and generating targeted tests.

---

## Notes

- The provided `report/reflection.pdf` is a generated document that summarizes this report.
- The `demo/final_presentation.mp4` slot is reserved for the recorded presentation.
