package com.springboot.restapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Size;

/**
 * @Description:
 * @Package: com.springboot.restapi
 * @Author: denglt
 * @Date: 2018/11/1 10:54 AM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Validated
@RestController
public class ElasticsearchController {

    @Resource
    private TransportClient client;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping(value = "/search", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JSONObject search(@Size(min = 2, max = 10) String address) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("address", address));  // and
        SearchResponse response = client.prepareSearch("test")
                .setTypes("_doc")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)                 // Query
                .setFrom(0).setSize(60).setExplain(true)
                .addSort("age", SortOrder.DESC)
                .get();
        SearchHit[] searchHits = response.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getSourceAsMap());
        }
        String content = response.toString();
        return JSON.parseObject(content);
    }


}
