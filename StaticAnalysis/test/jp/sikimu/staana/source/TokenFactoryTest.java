package jp.sikimu.staana.source;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class TokenFactoryTest {

	@Test
	void test() throws IOException {
		
		assertThrows(NullPointerException.class, () -> {new TokenFactory(null);});
	}
}
