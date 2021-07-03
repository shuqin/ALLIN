package zzz.study.annotations;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;
import zzz.study.patterns.composite.escondition.Match;
import zzz.study.patterns.composite.escondition.Range;

import java.util.*;

/**
 * Created by shuqin on 18/4/11.
 */
public class EsQuery {

    private static int DEFAULT_SIZE = 100;

    private final Map<String, Object> termFilter;
    private final Map<String, Range> rangeFilter;
    private final Map<String, Match> matchFilter;
    private final Map<String, Object> mustNotTermFilter;
    private final Map<String, Object> shouldTermFilter;
    private int size;
    private String orderBy = null;
    private String order = null;
    // query 查询语法, 是否需要 filtered, filter 这两层
    // 5.x 版本不再需要这两层
    private boolean isNeedFilterLayer = true;
    private Integer from;
    private Integer shouldMatchMinimum;

    private List<String> includes;
    private List<String> excludes;

    public EsQuery() {
        this.termFilter = new HashMap<>();
        this.rangeFilter = new HashMap();
        this.matchFilter = new HashMap();
        this.mustNotTermFilter = new HashMap<>();
        this.shouldTermFilter = new HashedMap();
        this.size = DEFAULT_SIZE;
        this.includes = new ArrayList<>();
        this.excludes = new ArrayList<>();
    }

    public EsQuery addTermFilter(String key, Object value) {
        this.termFilter.put(key, value);
        return this;
    }

    public EsQuery addMustNotTermFilter(String key, Object value) {
        this.mustNotTermFilter.put(key, value);
        return this;
    }

    public EsQuery addAllMustNotTermFilter(Map<String, Object> mustNot) {
        if (mustNot != null && !mustNot.isEmpty()) {
            this.mustNotTermFilter.putAll(mustNot);
        }
        return this;
    }

    public EsQuery addShouldTermFilter(String key, Object value) {
        this.shouldTermFilter.put(key, value);
        return this;
    }

    public EsQuery addAllShouldTermFilter(Map<String, Object> should) {
        if (should != null && !should.isEmpty()) {
            this.shouldTermFilter.putAll(should);
        }
        return this;
    }

    public EsQuery addRangeFilter(String key, long gte, long lte) {
        this.rangeFilter.put(key, new Range(gte, lte));
        return this;
    }

    public EsQuery addMatchFilter(String key, Match value) {
        this.matchFilter.put(key, value);
        return this;
    }

    public EsQuery addIncludeFields(List<String> includes) {
        this.includes.addAll(includes);
        return this;
    }

    public EsQuery addExcludeFields(List<String> excludes) {
        this.excludes.addAll(excludes);
        return this;
    }


    @Override
    public String toString() {
        return toJsonString();
    }

    public String toJsonString() {
        Map<String, Object> finalQuery = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        Map<String, Object> filteredMap = new HashMap<>();
        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> boolMap = new HashMap<>();

        List<Object> mustList = obtainTermFilterList(this.termFilter);

        List<Object> mustNotList = obtainTermFilterList(this.mustNotTermFilter);

        List<Object> shouldList = obtainTermFilterList(this.shouldTermFilter);

        if (!this.rangeFilter.isEmpty()) {
            for (Map.Entry<String, Range> e : this.rangeFilter.entrySet()) {
                Map<String, Object> rangeMap = new HashMap<>();
                Map<String, Object> rangeEntityMap = new HashMap<>();
                rangeEntityMap.put(e.getKey(), e.getValue().toMap());
                rangeMap.put(Constant.range, rangeEntityMap);
                mustList.add(rangeMap);
            }
        }

        if (!this.matchFilter.isEmpty()) {
            this.matchFilter.forEach(
                    (key, match) -> {
                        Map<String, String> matchEntityMap = new HashMap<>();
                        Map<String, Map> matchMap = new HashMap<>();
                        Map<String, Map> subMatchMap = new HashMap<>();
                        matchEntityMap.put(Constant.query, match.getQuery());
                        matchEntityMap.put(Constant.should_minum, match.getMinimumShouldMatch());
                        matchMap.put(key, matchEntityMap);
                        subMatchMap.put(Constant.match, matchMap);
                        mustList.add(subMatchMap);
                    });
        }

        boolMap.put(Constant.must, mustList);
        if (!mustNotList.isEmpty())
            boolMap.put(Constant.mustNot, mustNotList);
        if (!shouldList.isEmpty()) {
            // 有 minimum_should_match 不带过滤器
            boolMap.put(Constant.should, shouldList);
            boolMap.put(Constant.should_minum, shouldMatchMinimum);
            queryMap.put(Constant.bool, boolMap);
        } else {
            if (isNeedFilterLayer) {
                filterMap.put(Constant.bool, boolMap);
                filteredMap.put(Constant.filter, filterMap);
                queryMap.put(Constant.filtered, filteredMap);
            } else {
                queryMap.put(Constant.bool, boolMap);
            }
        }
        finalQuery.put(Constant.query, queryMap);

        Map<String, Object> orderMap = new HashMap<>();
        Map<String, Object> orderItem = new HashMap<>();

        if (order != null && orderBy != null) {
            orderItem.put(Constant.order, this.order);
            orderMap.put(this.orderBy, orderItem);
            finalQuery.put(Constant.sort, orderMap);
        }

        Map<String, Object> source = new HashMap<>();
        if (!includes.isEmpty()) {
            source.put(Constant.includes, this.includes);
        }
        if (!excludes.isEmpty()) {
            source.put(Constant.excludes, this.excludes);
        }
        if (!source.isEmpty()) {
            finalQuery.put(Constant.source, source);
        }

        finalQuery.put(Constant.size, this.size);
        if (from != null) {
            finalQuery.put(Constant.from, from.intValue());
        }
        return JSON.toJSONString(finalQuery);
    }

    public List<Object> obtainTermFilterList(Map<String, Object> termFilter) {
        List<Object> termFilterList = new ArrayList<>();
        for (Map.Entry<String, Object> e : termFilter.entrySet()) {
            Map<String, Object> termMap = new HashMap<>();
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put(e.getKey(), e.getValue());
            if (e.getValue() instanceof List) {
                termMap.put(Constant.terms, itemMap);
            } else {
                termMap.put(Constant.term, itemMap);
            }
            termFilterList.add(termMap);
        }
        return termFilterList;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Map<String, Object> getTermFilter() {
        return Collections.unmodifiableMap(termFilter);
    }

    public Map<String, Range> getRangeFilter() {
        return Collections.unmodifiableMap(rangeFilter);
    }

    public Map<String, Object> getMustNotTermFilter() {
        return Collections.unmodifiableMap(mustNotTermFilter);
    }

    public Map<String, Object> getShouldTermFilter() {
        return Collections.unmodifiableMap(shouldTermFilter);
    }

    public Map<String, Match> getMatchFilter() {
        return matchFilter;
    }

    public Integer getShouldMatchMinimum() {
        return shouldMatchMinimum;
    }

    public void setShouldMatchMinimum(Integer shouldMatchMinimum) {
        this.shouldMatchMinimum = shouldMatchMinimum;
    }

    public Map<String, Object> getRangeMap(String key) {
        return Collections.unmodifiableMap(rangeFilter.get(key).toMap());
    }

    public List<String> getIncludes() {
        return Collections.unmodifiableList(includes);
    }

    public boolean isNeedFilterLayer() {
        return isNeedFilterLayer;
    }

    public void setNeedFilterLayer(boolean needFilterLayer) {
        isNeedFilterLayer = needFilterLayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EsQuery esQuery = (EsQuery) o;

        if (size != esQuery.size) {
            return false;
        }
        if (isNeedFilterLayer != esQuery.isNeedFilterLayer) {
            return false;
        }
        if (!termFilter.equals(esQuery.termFilter)) {
            return false;
        }
        if (!rangeFilter.equals(esQuery.rangeFilter)) {
            return false;
        }
        if (!orderBy.equals(esQuery.orderBy)) {
            return false;
        }
        if (!order.equals(esQuery.order)) {
            return false;
        }
        if (!from.equals(esQuery.from)) {
            return false;
        }
        if (!mustNotTermFilter.equals(esQuery.mustNotTermFilter)) {
            return false;
        }
        if (!shouldTermFilter.equals(esQuery.shouldTermFilter)) {
            return false;
        }
        if (!includes.equals(esQuery.includes)) {
            return false;
        }
        return excludes.equals(esQuery.excludes);
    }

    @Override
    public int hashCode() {
        int result = termFilter.hashCode();
        result = 31 * result + rangeFilter.hashCode();
        result = 31 * result + size;
        result = 31 * result + orderBy.hashCode();
        result = 31 * result + order.hashCode();
        result = 31 * result + (isNeedFilterLayer ? 1 : 0);
        result = 31 * result + from.hashCode();
        result = 31 * result + mustNotTermFilter.hashCode();
        result = 31 * result + shouldTermFilter.hashCode();
        result = 31 * result + includes.hashCode();
        result = 31 * result + excludes.hashCode();
        return result;
    }
}
