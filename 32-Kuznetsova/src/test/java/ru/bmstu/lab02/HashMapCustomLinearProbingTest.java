package ru.bmstu.lab02;

import ru.bmstu.lab02.HashMapCustom.Probing;

class HashMapCustomLinearProbingTest extends HashMapCustomContractTest {

    @Override
    Probing probing() {
        return Probing.LINEAR;
    }
}
