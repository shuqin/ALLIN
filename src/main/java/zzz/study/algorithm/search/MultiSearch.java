package zzz.study.algorithm.search;

import java.util.HashMap;
import java.util.Map;

public class MultiSearch {

    public static void main(String[] args) {
        int[] arr = new int[] { 1,3,5,3,3,4,6,9,2,8,0,3,5,1,5,8,6 };
        Search[] searches = new Search[] { new Search(2,7,8), new Search(1,9,3), new Search(2,12,8) };
        System.out.println(search(arr, searches));
    }

    public static Map<Integer, SearchResult> search(int[] arr, Search[] searches) {
        Map<Integer, SearchResult> searchResultMap = groupSame(searches);
        Map<Integer, Integer> result = new HashMap<>();
        for (int i=0; i < arr.length; i++) {
            SearchResult searchResult = searchResultMap.get(arr[i]);
            if (searchResult != null && i >= searchResult.startIndex && i < searchResult.endIndex) {
                searchResult.count += 1;
            }
        }
        return searchResultMap;
    }

    public static Map<Integer, SearchResult> groupSame(Search[] searches) {
        Map<Integer, SearchResult> searchResultMap = new HashMap<>();
        for (Search search: searches) {
            SearchResult searchResult = searchResultMap.get(search.key);
            if (searchResult == null) {
                searchResultMap.put(search.key, new SearchResult(search));
            }
            else {
                searchResult.startIndex = Math.min(search.startIndex, searchResult.startIndex);
                searchResult.endIndex = Math.max(search.endIndex, searchResult.endIndex);
            }
        }
        return searchResultMap;
    }

}

class Search {
    int startIndex;
    int endIndex;
    int key;
    public Search() {}
    public Search(int startIndex, int endIndex, int key) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.key = key;
    }
}

class SearchResult extends Search {
    int count;
    public SearchResult(Search search) {
        this.key = search.key;
        this.startIndex = search.startIndex;
        this.endIndex = search.endIndex;
    }

    public String toString() {
        return String.format("(%d,%d,%d,c=%d)", startIndex, endIndex, key, count);
    }
}




