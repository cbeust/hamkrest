package com.natpryce.hamkrest;

import com.natpryce.hamkrest.assertion.assertThat
import org.junit.Test
import kotlin.test.assertEquals


class Equality {
    @Test
    fun equal() {
        assertMatch((equalTo(10))(10))
        assertMatch(equalTo("hello")("hello"))
    }

    @Test
    fun not_equal() {
        assertMismatchWithDescription("was 20", equalTo(10)(20))
        assertMismatchWithDescription("was 1", equalTo(0)(1))
    }

    @Test
    fun description() {
        assertEquals("is equal to 20", equalTo(20).description())
        assertEquals("is equal to \"foo\"", equalTo("foo").description())
        assertEquals("is equal to \"hello \\\"nat\\\"\"", equalTo("hello \"nat\"").description())
    }
}

class Nullability {
    @Test
    fun absence() {
        val m : Matcher<Int?> = absent();

        assertMatch(m(null))
        assertMismatchWithDescription("was 100", m(100))
    }

    @Test
    fun presence() {
        val m : Matcher<String?> = present(equalTo("xxx"));

        assertMatch(m("xxx"))
        assertMismatchWithDescription("was null", m(null))
        assertMismatchWithDescription("was \"yyy\"", m("yyy"))
    }
}

class Downcasting {
    val m : Matcher<Any> = cast(equalTo("bob"))

    @Test
    fun wrong_type() {
        assertMismatchWithDescription("had type kotlin.Double", m(10.0))
    }

    @Test
    fun correct_type_and_downcast_mismatch() {
        assertMismatchWithDescription("was \"alice\"", m("alice"))
    }

    @Test
    fun correct_type_and_downcast_match() {
        assertMatch(m("bob"))
    }
}

class Comparables {
    @Test
    fun order_comparisons() {
        assertThat(10, greaterThan(5))
        assertThat(10, greaterThanOrEqualTo(5))
        assertThat(10, greaterThanOrEqualTo(10))
        assertThat(10, !greaterThanOrEqualTo(50))

        assertThat(10, lessThan(20))
        assertThat(10, lessThanOrEqualTo(20))
        assertThat(10, lessThanOrEqualTo(10))
        assertThat(10, !lessThanOrEqualTo(5))
    }

    @Test
    fun within_range() {
        assertThat(1, isWithin(1..20))
        assertThat(10, isWithin(1..20))
        assertThat(20, isWithin(1..20))
        assertThat(0, !isWithin(1..20))
        assertThat(21, !isWithin(1..20))

        assertThat(isWithin(1..20).description(), equalTo("is within 1..20"))
        assertThat((!isWithin(1..20)).description(), equalTo("is not within 1..20"))
    }
}
