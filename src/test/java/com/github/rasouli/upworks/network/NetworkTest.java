package com.github.rasouli.upworks.network;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NetworkTest {

    public Network newNetwork(int vertexCount) throws Exception {
        return new Network(vertexCount);
    }

    @Test
    public void cyclesWithinGraphShouldBeSupported() throws Exception {
        Network network = newNetwork(1);
        network.connect(1, 1);
        assertThat(network.query(1, 1)).isTrue();
    }

    @Test
    public void edgesShouldBeTreatedTheSameRegardlessOfTheOrderTheyAreQueried() throws Exception {
        Network network = newNetwork(2);
        network.connect(1, 2);
        assertThat(network.query(2, 1)).isTrue();
        assertThat(network.query(1, 2)).isTrue();
    }

    @Test
    public void negativeNumberGivenToTheConstructorMustThrowException() {
        assertThatThrownBy(
                new ThrowableAssert.ThrowingCallable() {
                    @Override
                    public void call() throws Throwable {
                        newNetwork(-5);
                    }
                }
        ).hasMessage("Expected Non-Negative vertex size for the graph");
    }

    @Test
    public void vertexWithZeroLabelIsNotSupportedToConnect() {
        assertThatThrownBy(
                new ThrowableAssert.ThrowingCallable() {
                    @Override
                    public void call() throws Throwable {
                        newNetwork(1).connect(0, 0);
                    }
                }
        ).hasMessage("Vertex value 0 is not acceptable!");
    }

    @Test
    public void vertexWithOutOfSizeLabelIsNotSupportedToConnect() {
        assertThatThrownBy(
                new ThrowableAssert.ThrowingCallable() {
                    @Override
                    public void call() throws Throwable {
                        newNetwork(1).connect(2, 2);
                    }
                }
        ).hasMessage("Vertex value 2 is not acceptable!");
    }

    @Test
    public void vertexWithZeroLabelIsNotSupportedToQuery() {
        assertThatThrownBy(
                new ThrowableAssert.ThrowingCallable() {
                    @Override
                    public void call() throws Throwable {
                        Network network = newNetwork(2);
                        network.connect(2, 2);
                        network.query(0,0);
                    }
                }
        ).hasMessage("Vertex value 0 is not acceptable!");
    }

    @Test
    public void vertexWithOutOfSizeLabelIsNotSupportedToQuery() {
        assertThatThrownBy(
                new ThrowableAssert.ThrowingCallable() {
                    @Override
                    public void call() throws Throwable {
                        Network network = newNetwork(2);
                        network.connect(2, 2);
                        network.query(4,4);
                    }
                }
        ).hasMessage("Vertex value 4 is not acceptable!");
    }


    @Test
    public void randomGraphTest() throws Exception {
        Network network = newNetwork(8);
        network.connect(1,2);
        network.connect(2,6);
        network.connect(1,6);
        network.connect(2,4);
        network.connect(5,8);

        assertThat(network.query(1,6)).isTrue();
        assertThat(network.query(6,4)).isTrue();
        assertThat(network.query(6,2)).isTrue();
        assertThat(network.query(7,4)).isFalse();
        assertThat(network.query(5,6)).isFalse();
    }

}
