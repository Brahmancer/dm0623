/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package toolVendor;

import org.junit.jupiter.api.Test;

import toolVendor.SampleApp;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void appHasAGreeting() {
        SampleApp classUnderTest = new SampleApp();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
