Clarifai Java Client - Developer info
===============

Setup
------
Recommended environment is as follows:

* Latest version of IntelliJ. Other IDEs might be fine, but have not been tested.
* Java 8. Unit tests are written in a separate Gradle module so that we don't need to use Java 8 to compile the client,
  but can use lambdas in test-cases for readability's sake.

You must configure your IDE to run annotation processors. The steps are available
[here](https://github.com/tbroyer/gradle-apt-plugin/blob/master/README.md#usage-with-ides).
Without these steps, you will be forced to build from the command-line every time you need updated generated code.


Credentials
------

To push to our Sonatype (Maven Central) and jCenter accounts, you will need to fill in the credentials in
the [sensitive.properties](sensitive.properties) file. Please see this file for instructions on how to obtain
these credentials.


Deploying
------

Deploying to Jitpack:

- Simply tag a release in our GitHub repo and it will be released via JitPack. This is useful because JitPack hosts
our Javadocs at `https://jitpack.io/com/github/clarifai/[artifactId]/[version]/javadoc/`

Deploying to jCenter and Maven Central:

- Run `./gradlew clean build bintrayUpload`. This will run our unit tests and, if they pass, will deploy to jCenter and
then Maven Central.

- The version in these two repos will be the tag that is placed on the current HEAD of the repo if it exists, otherwise
the most recent tag in the repo followed by the commit hash (eg, "2.0.0-beta2-1234567"). For this reason, you usually
will want to tag a GitHub release for your current commit, make sure that tag is present in your local repo, and then
run the command to deploy under that release name.


Convention
---------

There is a code-style file checked into this repo at `.idea/codeStyleSettings.xml`. Please make sure that you are using
the project's code-styling in IntelliJ. Please make sure that you run `Code -> Reformat Code` from the IDE before
opening any PRs.

Please make sure that your inspections profile in your IDE is also set to use the project's inspections (available at
`.idea/inspectionProfiles/`), and that you either squash or suppress any warnings that the IDE emits. Even if a warning
seems unimportant, you *must* suppress it with an annotation at the very least, or we won't be able to tell the
noise from the actual warnings.

Aside from these styling requirements, it is very important that all public-facing method parameters, fields, and return
types are annotated with `@NotNull` or `@Nullable` so that users don't get NPEs.

In the same vein, we place great emphasis on immutability in this library; please use Builders to encapsulate mutability
and then return an immutable object from there.