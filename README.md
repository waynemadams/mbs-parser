# mbs-parser
A Scala library for parsing the various mortgage-backed security issue files produced by Fannie Mae.

U.S. government-sponsored enterprises involved with the mortgage market provide data on their websites for outside researchers.  This library is intended to facilitate the parsing of these data files.  Eventually, I plan to provide a full library for Fannie Mae, Ginnie Mae and Freddie Mac; for now the library parses a subset of Fannie Mae files only.

## Fannie Mae

Fannie Mae files come in two categories:  issuance files and monthly files.  The status of the parser library for these files is shown below:
* Issuance files:
  * New Issue Pool Statistics _(available)_
  * New Issues Loan and Collateral Statement _(available)_
  * Loan Level Disclosure _(available)_
* Monthly files:
  * MBS Stats _(available)_
  * Fixed Rate Quartile File _(in progress)_
  * ARM Stats _(coming soon)_
  * GEO Stats _(coming soon)_
  * Adjustable Rate Quartile File _(coming soon)_
  * Supplemental File _(coming soon)_
  * Interest Only Disclosure File _(coming soon)_
  * DMBS Stats _(coming soon)_
  * Actual/360 Fixed Rate Mega and Actual/360 ARM MBS File _(coming soon)_
  * Loan Level Disclosure _(coming soon)_

Note that retrieval of Fannie Mae data requires a (free) login, requiring you to agree to certain terms and conditions.  A minimal amount of data (either one or two records, or the supplied sample files) is included in the library for purposes of running the unit tests only, to stay within the redistribution requiements in the terms and conditions.  To be able to parse real data, you will need an (again, free) account with Fannie Mae, with which you'll be able to download files.

### Running the code

This is an SBT project, so you can clean, compile, run tests, and run applications via the usual mechanisms:

* sbt clean
* sbt compile
* sbt test
* sbt run

While the class `main` methods are partially fleshed out as examples, you'll need to download data and provide your own code to process it.  The `main` methods of the three Issuance-File formats are much more fleshed-out than those for the Monthly Files, but on inspection you'll note they'll need to be modified to parse data from your local environment.  I'll clean these methods up a little in the near future to make them more usable "out of the box."

Some of these files are quite large and you may not have enough available memory to bring an entire file into memory and parse it into a `List` of whatever object type you are retrieving.  I'm gradually adding code to extract subsets of files to allow incremental processing; this procedure can be a little tricky, as some files are hundreds of MB in size and don't contain even a single newline character or delimiter.  As I continue to flesh out the library, I will continue including some helper functions to manage these types of issues, in addition to the "core" infrastructure to parse a String record into an instance of the desired type.

Also in planning is more documentation on using the library, with some examples and a tutorial on one of my blogs.  Coming soon!

