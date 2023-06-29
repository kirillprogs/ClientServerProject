package org.example.client;

import org.junit.jupiter.api.Test;

class HttpAccessorTest {
    @Test
    void test() {
        HttpAccessor accessor = new HttpAccessor();
        accessor.login("jerry", "abba");
    }
}