package ru.bmstu.lab02;

import ru.bmstu.lab02.HashMapCustom.Probing;

class HashMapCustomQuadraticProbingTest extends HashMapCustomContractTest {

    @Override
    Probing probing() {
        return Probing.QUADRATIC;
    }
}
