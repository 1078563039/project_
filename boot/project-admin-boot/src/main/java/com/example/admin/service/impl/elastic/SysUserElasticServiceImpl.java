package com.example.admin.service.impl.elastic;

import com.example.admin.pojo.entity.elstic.SysUserEntityElastic;
import com.example.admin.service.elastic.SysUserElasticService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserElasticServiceImpl implements SysUserElasticService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Page<SysUserEntityElastic> searchUsers(String query, int page, int size) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .should(new MatchQueryBuilder("userName", query))
                        .should(new MatchQueryBuilder("nickName", query)))
                .withHighlightFields(
                        new HighlightBuilder.Field("userName"),
                        new HighlightBuilder.Field("nickName"))
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<SysUserEntityElastic> searchHits = elasticsearchRestTemplate.search(searchQuery, SysUserEntityElastic.class);

        List<SysUserEntityElastic> results = new ArrayList<>();
        for (SearchHit<SysUserEntityElastic> hit : searchHits) {
            SysUserEntityElastic user = hit.getContent();

            List<String> titleHighlights = hit.getHighlightFields().get("userName");
            if (titleHighlights != null && !titleHighlights.isEmpty()) {
                user.setUserName(titleHighlights.get(0));
            }

            List<String> contentHighlights = hit.getHighlightFields().get("nickName");
            if (contentHighlights != null && !contentHighlights.isEmpty()) {
                user.setNickName(contentHighlights.get(0));
            }

            results.add(user);
        }

        return new PageImpl<>(results, PageRequest.of(page, size), searchHits.getTotalHits());
    }
}
