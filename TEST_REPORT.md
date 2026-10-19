# Test Report

<!--
    Commit this file to the root of your application's GitHub repository, alongside its code.
    Every requirement listed in your README.md needs at least one test case below. Giving each
    requirement a short ID in the README (e.g. R1, R2) makes the "Requirement" column easy to fill
    in and to check.
-->

## Summary

*Briefly describe how you tested your application – manually through its interface, automatically,
or both – clearly enough that someone else could carry out the same tests. What was hardest to
test, and why?*

Answer: I tested in two ways.

- **Automatically:** my own savechecker module has 101 JUnit 5 tests. Run them with
  `./gradlew test` from the repo root.
- **Manually, through the app:** I ran every test case in the table below by hand, including edge
  cases (no badges and all badges, a Pokémon with no EVs, a wrong path, a folder, a file that is not
  a save, a mistyped `quit`). I used two of the module's test saves: the Red save (two Pokémon, one
  of them traded in) and the Yellow save `Yellow_Random_01.srm` (all eight badges).

To repeat the manual tests:

1. Start the app from the repo root with `./gradlew :app:run --console=plain`. Without
   `--console=plain`, Gradle's progress bar gets mixed into the app's prompts.
2. Type the input from the table. Paths are relative to the `app/` folder, because that is where
   Gradle starts the app, so they begin with `../`.
3. For the report cases, open `app/report/report.html` in a browser (or look at its HTML source) and
   compare it with the expected outcome.

Testing found one real bug: typing a folder instead of a file gave the unclear message "Could not
read or write a file". It is fixed, see the "Folder instead of a file" row.

**What was hardest to test:** there are no automated tests for the app itself, so the report
content had to be checked by opening the HTML report and comparing it value by value with the save
file. The relative paths were also tricky: they depend on where Gradle starts the app, and during
development I typed `./` instead of `../` and got "file not found" for a file that existed.

One more thing to know when repeating the tests: in Git Bash the console showed "Pok�mon" instead
of "Pokémon". This is the console's text encoding, not the app; the HTML report shows "Pokémon"
correctly.

## Test Results

Save file paths are typed relative to the `app/` folder, because that is where `./gradlew :app:run`
starts the app. All save files are the module's test saves in
`savechecker/src/test/resources/saves/`.

| Test case | Requirement | Input | Expected outcome | Actual outcome |
| --------- | ----------- | ----- | ---------------- | -------------- |
| Start the app | R1 | Ran `./gradlew :app:run`. | A welcome message that explains what the app does and how to use it, then a prompt asking for a save file path. | ✅ As expected: "=== Pokémon Save Report ===", a short description, "How to use it" with three steps, then "Save file path (or quit):". Note: Git Bash showed "Pok�mon" instead of "Pokémon" (console encoding, see Summary). |
| Create a report from a valid save | R2 | Typed `../savechecker/src/test/resources/saves/Red/Pokemon Red (UE) [S][!].sav` and pressed Enter. | The report is saved as `report/report.html` and the app prints where it was saved. | ✅ As expected: "Report written to report\report.html", and `app/report/report.html` was created. |
| Ask again after a report | R3 | Same run as above, after the report was written. | The app asks for another save file path. | ✅ As expected: "Save file path (or quit):" was shown again. |
| Trainer name | R4 | Opened `app/report/report.html` from the Red save run above. | The heading is the trainer's name, "A". | ✅ As expected: `<h1>A</h1>`. |
| No badges | R5 | Same Red report. | All eight badges are listed, all with "no". | ✅ As expected: BOULDER to EARTH, all "no". |
| All eight badges | R4, R5 | Typed `../savechecker/src/test/resources/saves/Yellow/Yellow_Random_01.srm` and opened the new report. | Edge case, the opposite of the Red save: heading "YELLOW" and all eight badges with "yes". | ✅ As expected: "YELLOW", "Trainer ID: 27315", BOULDER to EARTH all "yes". |
| Nickname, level and experience | R6 | Same Red report, party table. | EEVEE: level 20, experience 8000. SQUIRTLE: level 6, experience 202. | ✅ As expected. |
| Stats | R7 | Same Red report, party table. | EEVEE: max HP 56, Attack 29, Defense 27, Speed 27, Special 35. | ✅ As expected. |
| Stat Exp (EVs) | R8 | Same Red report, party table. | EEVEE: all five Stat Exp values 0. SQUIRTLE: 45, 49, 49, 45, 65. A note under the table explains the 0 to 65535 range. | ✅ As expected, including the note "Stat Exp is how Generation 1 stores EVs. Each one goes from 0 to 65535." |
| IVs | R9 | Same Red report, party table. | EEVEE: Attack IV 7, Defense IV 5, Speed IV 2, Special IV 12. | ✅ As expected. |
| Pokédex counts | R15 | Same Red report. | Seen 7, caught 5. | ✅ As expected: "Seen: 7", "Caught: 5". |
| Trainer ID, HP IV and traded Pokémon | R16 | Same Red report. The Red save's EEVEE was traded in from another game (original trainer ID 46116), SQUIRTLE was caught by the player. | Trainer ID 48035. HP IV: EEVEE 12, SQUIRTLE 8. Traded: EEVEE yes, SQUIRTLE no. | ✅ As expected: "Trainer ID: 48035", HP IV 12 and 8, Traded "Yes" for EEVEE and "No" for SQUIRTLE. |
| Level chart | R10 | Same Red report, "Levels" section. | A bar chart with two bars, labelled EEVEE and SQUIRTLE. EEVEE's bar (level 20) is higher than SQUIRTLE's (level 6). | ✅ As expected: chart "Party levels" with the bars EEVEE and SQUIRTLE, axis 0 to 20. |
| EV chart per Pokémon | R11 | Same Red report, "Stat Exp (EVs) per Pokémon" section. | Edge case: EEVEE has 0 in all five Stat Exp values, so the text "no EVs yet" is shown instead of a chart. SQUIRTLE gets a bar chart with five bars: HP, Attack, Defense, Speed, Special. | ✅ As expected: "EEVEE: no EVs yet", then a chart titled SQUIRTLE with the five bars. |
| File does not exist | R12 | Typed `../no_such_file.sav`. | A message that the file was not found, no report is created, and the app asks for a path again. | ✅ As expected: "File not found: ../no_such_file.sav", then the prompt again. `report.html` was not changed (still the YELLOW report). |
| Wrong relative path | R12 | Typed `./savechecker/src/test/resources/saves/Yellow/Yellow_Randomizer_02.srm` (one dot instead of two, a mistake made during development on 2026-10-15). | Same as above: "file not found" message and the app asks again. | ✅ As expected: "File not found: ./savechecker/src/test/resources/saves/Yellow/Yellow_Randomizer_02.srm". Before error handling was added (2026-10-15), the same input crashed the app with a `NoSuchFileException` stack trace – fixed. |
| File is not a save file | R13 | Typed `../README.md`. | A message that the file is not a valid save file, no report is created, and the app asks again. | ✅ As expected: "Not a valid save file: Expected 32768 bytes but got 3555 bytes", then the prompt again. `report.html` was not changed. |
| Folder instead of a file | R13 | Typed `../savechecker` (a folder, not a file). | A message that this is not a valid save file, no report is created, and the app asks again. | ❌ The app did not crash and asked again, but printed "Could not read or write a file: ..\savechecker", which does not say what is wrong. Fixed: `SaveFileLoader` now checks for a folder first. After the fix: "Not a valid save file: ../savechecker is a folder, not a save file", then the prompt again. |
| Close the app | R14 | Typed `quit`. | The app closes. | ✅ As expected: the app stopped and Gradle printed "BUILD SUCCESSFUL". |
| Mistyped quit | R14, R12 | Typed `qquit` by mistake. | Edge case: only the exact word `quit` closes the app, so `qquit` is treated as a path. | ✅ As expected: "File not found: qquit", then the prompt again. The app did not close. |

## Known Issues in Third-Party Modules

*List any flaws you found in the other student's module that affected your application, how they
show up, and how you handled them (e.g. a workaround, or a link to an issue you filed in the
module's repository). Write "None found" if there weren't any.*

Answer:

**svg-charts v1.0.1: a bar chart where every value is 0 gets a decimal axis.** If all values given
to `BarChart.setValues(...)` are 0, the chart draws the y-axis as 0, 0.2, 0.4, 0.6, 0.8, 1 and all
bars have height 0. For whole-number data like EVs this looks like a bug in the report: an empty
chart with odd decimal steps. Charts where at least one value is above 0 are fine (for example
values up to 3 give the axis 0, 1, 2, 3).

This affected the EV charts (R11): a newly caught Pokémon, like EEVEE in the Red save, has 0 in
all five Stat Exp values. Workaround: `ChartBuilder` checks for this case first and shows the text
"no EVs yet" instead of a chart. 

Reported in the module's repository: [issue #1](https://github.com/Robert060615/svg-charts-module/issues/1).

**Limitation, not a bug: one data series per chart.** `BarChart` takes one list of values, so the
EVs of several Pokémon cannot be compared in one grouped chart. The report therefore has one EV
chart per Pokémon.
