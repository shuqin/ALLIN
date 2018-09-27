package cc.lovesq.study

import spock.lang.Specification
import spock.lang.Unroll
import zzz.study.algorithm.search.BinarySearch

/**
 * Created by shuqin on 18/3/25.
 */
class BinarySearchTest extends Specification {

    def "testSearch"() {
        expect:
        BinarySearch.search(arr as int[], key) == result

        where:
        arr       | key | result
        []        | 1   | -1
        [1]       | 1   | 0
        [1]       | 2   | -1
        [3]       | 2   | -1
        [1, 2, 9] | 2   | 1
        [1, 2, 9] | 9   | 2
        [1, 2, 9] | 3   | -1
        //null      | 0   | -1
    }

    @Unroll
    def "testSearch(#key in #arr index=#result)"() {
        expect:
        BinarySearch.search(arr as int[], key) == result

        where:
        arr       | key | result
        []        | 1   | -1
        [1, 2, 9] | 9   | 2
        [1, 2, 9] | 3   | 0
    }

}
