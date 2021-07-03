@Test
        def "test${Method}"() {
        expect:
        ${inst}.invokeMethod("${method}", [${paramListInMethodCall}]) == ${result}

        where:
        ${paramListInDataProvider}     | ${result}
        ${paramValues} | ${resultValue}

        }