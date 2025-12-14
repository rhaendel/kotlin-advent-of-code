# kotlin-advent-of-code

Welcome to the Advent of Code[^aoc] Kotlin project created by [rhaendel][github].

In this repository, rhaendel is about to provide solutions for the puzzles using [Kotlin][kotlin] language.

![Laboratories animation](animations/2025-07_Laboratories.gif)

*More animated GIFs can be found at [here](animations/animations.md).*

## It's all about Learning

I started this repository in december 2024 using the [Advent of Code Kotlin Template][template] delivered by JetBrains.
Since I solved all the puzzles of year 2024, I continued with the first ones, the year 2015, and restructured the
project iteratively.

I'm solving the challenges for fun, but not only. It's a good possibility to learn - about the programming language of
my choice, about algorithms in general, about when it's time to throw code away and start over again with a totally
different approach.

Over the time, a small library of helping functions, classes and algorithms keeps growing in the
[extensions](src/main/kotlin/de/ronny_h/aoc/extensions) package. I try keeping a good test coverage especially there.

While I'm trying to learn and improve my [idiomatic Kotlin](https://kotlinlang.org/docs/idioms.html), I don't stick to
that. I try to us a programming style matching to the problem. Sometimes that's more functional, sometimes it's object
oriented or even procedural if that's more expressive in a situation.

## File Template

There is a file template for IntelliJ IDEA that creates...

* A new source file containing a main function and an initial implementation of
  [`AdventOfCode`](src/main/kotlin/de/ronny_h/aoc/AdventOfCode.kt)
* An empty text file in [resources](src/main/resources) for the day's puzzle input
* A test class in the test source set containing assertions for part one and two

**Using the template in IntelliJ IDEA**

Preparation: In _Settings... > Editor > File and Code Templates_, set the _Scheme_ to `Project`.

Usage: In the the [source directory](src)'s context menu, choose:

_New > Advent of Code_

## Kotlin Resources

If you're stuck with Kotlin-specific questions or anything related to this template, check out the following resources:

- [Kotlin docs][docs]
- [Kotlin Slack][slack]
- Template [issue tracker][issues]

[^aoc]:
[Advent of Code][aoc] – An annual event of Christmas-oriented programming challenges started December 2015.
Every year since then, beginning on the first day of December, a programming puzzle is published every day for
twenty-five days.
You can solve the puzzle and provide an answer using the language of your choice.

[aoc]: https://adventofcode.com

[docs]: https://kotlinlang.org/docs/home.html

[github]: https://github.com/rhaendel

[issues]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/issues

[kotlin]: https://kotlinlang.org

[slack]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up

[template]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template
