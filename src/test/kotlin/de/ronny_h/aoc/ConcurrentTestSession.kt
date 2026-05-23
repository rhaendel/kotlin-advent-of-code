package de.ronny_h.aoc

import de.infix.testBalloon.framework.core.TestCompartment
import de.infix.testBalloon.framework.core.TestSession

// this lets TestBalloon run all tests in parallel
class ConcurrentTestSession : TestSession(defaultCompartment = { TestCompartment.Concurrent })
