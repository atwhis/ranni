package com.ymchen.ranni.component.elastic.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ESUtil {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 是否存在索引
     *
     * @param index
     * @return
     */
    public boolean existIndex(String index) {
        try {
            return restHighLevelClient.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
        } catch (Exception ex) {
            log.error("查询索引异常", ex);
        }

        return Boolean.FALSE;
    }

    /**
     * 创建索引
     *
     * @param index
     * @param columnMap
     * @return
     */
    public boolean createIndex(String index, Map<String, Object> columnMap) {
        if (existIndex(index)) {
            return Boolean.FALSE;
        }

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        if (null != columnMap && columnMap.size() > 0) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("properties", columnMap);
            createIndexRequest.mapping(properties);
        }

        try {
            restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            return Boolean.TRUE;
        } catch (Exception ex) {
            log.error("创建索引异常", ex);
        }

        return Boolean.FALSE;
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public boolean deleteIndex(String index) {
        try {
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
            return acknowledgedResponse.isAcknowledged();
        } catch (Exception ex) {
            log.error("删除索引异常", ex);
        }
        return Boolean.FALSE;
    }


    /**
     * 新增索引数据
     *
     * @param index
     * @param jsonData
     * @return
     */
    public boolean insertData(String index, String jsonData) {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.source(jsonData, XContentType.JSON);

        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            return Boolean.TRUE;
        } catch (Exception ex) {
            log.error("新增索引数据异常", ex);
        }

        return Boolean.FALSE;
    }

    /**
     * 查询索引数据
     *
     * @param index
     * @param field
     * @param values
     * @return
     */
    public Object queryIndexData(String index, String field, String values) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery(field, values);
        sourceBuilder.query(queryBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            if (null != response.getHits() && null != response.getHits().getHits()) {
                return response.getHits();
            }
        } catch (IOException ex) {
            log.error("查询索引数据异常", ex);

        }
        return null;
    }

    /**
     * mustache search
     *
     * @param scriptPath
     * @param object
     * @return
     */
    public Object mustacheSearch(String scriptPath, Object object) {

        String scriptContent = readContentFromResource(scriptPath).trim();
        if (StringUtils.isNotBlank(scriptContent) && null != object) {
            Map<String, Object> params = (Map<String, Object>) JSONObject.parseObject(JSONObject.toJSONString(object), Map.class);
            SearchTemplateRequest searchTemplateRequest = new SearchTemplateRequest();
            searchTemplateRequest.setScript(scriptContent);
            searchTemplateRequest.setScriptType(ScriptType.INLINE);
            searchTemplateRequest.setScriptParams(params);
            searchTemplateRequest.setRequest(new SearchRequest());

            try {
                SearchResponse response = restHighLevelClient.searchTemplate(searchTemplateRequest, RequestOptions.DEFAULT).getResponse();
                if (null != response.getHits() && null != response.getHits().getHits()) {
                    return response.getHits();
                }
            } catch (IOException ex) {
                log.error("模版搜索异常", ex);
            }
        }
        return null;
    }

    /**
     * 删除索引数据
     *
     * @param index
     * @param dataId
     * @return
     */
    public boolean deleteData(String index, String dataId) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(index, dataId);
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (Exception ex) {
            log.error("删除索引数据异常", ex);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * @param resourcePath
     * @return
     */
    private String readContentFromResource(String resourcePath) {
        StringBuilder sb = new StringBuilder();
        try {
            Resource resource = new ClassPathResource(resourcePath);
            InputStream is = resource.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String data = null;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
            br.close();
            isr.close();
            is.close();
        } catch (Exception ex) {
            log.error("read content from {} error", resourcePath, ex);
        }
        return sb.toString();
    }
}
