package com.ace.smart.common.controller;

import com.ace.smart.bolg.entity.PArticle;
import com.ace.smart.bolg.service.PArticleService;
import com.ace.smart.common.util.SolrBeanUtil;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("solr")
public class SolrController {

    @Autowired
    private SolrClient solrClient;
    @Autowired
    private PArticleService pArticleService;

    /**
     * 根据id查询索引
     * @return
     * @throws Exception
     */
    @RequestMapping("getById")
    public String getById() throws Exception {
        SolrDocument document = solrClient.getById("core1", "6666");
        System.out.println(document);
        return document.toString();
    }

    @RequestMapping("search")
    public String search(){
        try {
            SolrQuery params = new SolrQuery();
            //排序
            params.set("q","*:*");
            params.addSort("viewCount", SolrQuery.ORDER.asc);
            //分页
            params.setStart(0);
            params.setRows(20);
            QueryResponse queryResponse = solrClient.query(params);
            SolrDocumentList results = queryResponse.getResults();
             results.getNumFound();
            List<PArticle> pArticles =(List<PArticle>) SolrBeanUtil.toBeanList(results,PArticle.class);
            System.out.println("");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @desc 添加索引
     * @author zzh
     * @date 2018/8/10 16:44
     * @param
     * @return
     */
    @RequestMapping("/addDoc")
    public void addDucument() throws IOException{
        List<PArticle> list = pArticleService.selectAll();
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (PArticle pArticle:list) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("articleId", pArticle.getArticleId());
            doc.addField("articleTitle", pArticle.getArticleTitle());
            doc.addField("articleAuthor", pArticle.getArticleAuthor());
            doc.addField("wordage", pArticle.getWordage());
            doc.addField("likesCount", pArticle.getLikesCount());
            doc.addField("viewCount", pArticle.getViewCount());
            doc.addField("articleType", pArticle.getArticleType());
            doc.addField("articleUrl", pArticle.getArticleUrl());
            doc.addField("articleTime", pArticle.getArticleTime());
            doc.addField("articleSource", pArticle.getArticleSource());
            docs.add(doc);
        }
        try {
            UpdateResponse rsp = solrClient.add(docs);
            solrClient.commit();
        } catch (SolrServerException  e) {
            e.printStackTrace();
        }
    }
}
