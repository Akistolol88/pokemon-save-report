# Pokémon Save Report

A command-line application that reads a Pokémon Generation 1 save file and creates an HTML report
of the trainer and the Pokémon in the party, with charts.

## Vision

Pokémon Save Report is for players who are curious about their own Pokémon in a Generation 1 game
(Red, Blue or Yellow). They have a save file from their game, but there is not much they can do
with it without starting the game, and even in the game some values are hidden. With this
application they give the save file to the app and get a detailed report of their trainer and the
Pokémon in their party: stats, level, experience, EVs and IVs. This saves time compared to looking
everything up in the game, and it shows the EVs and IVs, which the game never tells the player.

## Requirements

### Starting the app

- **R1.** When the app starts, it shows a welcome message that explains how to use it, and asks
  for the path to a save file.

### Creating the report

- **R2.** When the user enters the path to a valid save file, the app creates the file
  `report.html` in a folder named `report` and prints where the file was saved.
- **R3.** After a report has been created, the app asks for the path to another save file.

### Report content

- **R4.** The report shows the trainer's name.
- **R5.** The report shows which of the eight badges the trainer has.
- **R6.** The report shows, for each Pokémon in the party, its nickname, level and experience.
- **R7.** The report shows, for each Pokémon in the party, its stats: max HP, Attack, Defense,
  Speed and Special.
- **R8.** The report shows, for each Pokémon in the party, its EVs: HP, Attack, Defense, Speed and
  Special. In Generation 1 these are called stat experience and go from 0 to 65535, so the report
  labels them "Stat Exp" and explains the range.
- **R9.** The report shows, for each Pokémon in the party, its IVs: Attack, Defense, Speed and
  Special.
- **R15.** The report shows how many Pokémon species the trainer has seen and caught in the
  Pokédex.

### Charts

- **R10.** The report contains a bar chart of the level of each Pokémon in the party, with one bar
  per Pokémon labelled with its nickname.
- **R11.** The report contains, for each Pokémon in the party, a bar chart of its five EVs. If all
  five EVs are 0, the report shows the text "no EVs yet" instead of an empty chart.

### Errors

- **R12.** If the file does not exist, the app prints a message that the file was not found,
  creates no report, and asks for a path again.
- **R13.** If the file is not a valid Generation 1 save file, the app prints a message that the
  file is not a valid save file, creates no report, and asks for a path again.

### Closing the app

- **R14.** The user can type `quit` instead of a path to close the app.

The test cases for these requirements are in [TEST_REPORT.md](TEST_REPORT.md).

## How to run

The app requires **Java 25** or later.

Instructions for downloading and running the app will be added here with the first release.

## Built with

- [Pokemon-Red-Blue-Savechecker-Module](https://github.com/Akistolol88/Pokemon-Red-Blue-Savechecker-Module),
  my own module, which reads and validates the save file.
- [svg-charts-module](https://github.com/Robert060615/svg-charts-module) by Robert060615, which
  draws the charts as SVG.

## License

See [LICENSE](LICENSE).
