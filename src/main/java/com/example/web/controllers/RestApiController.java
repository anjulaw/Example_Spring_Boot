package com.example.web.controllers;

import com.example.domain.Issue;
import com.example.domain.Bug;
import com.example.utils.CalculationUtil;
import com.example.utils.DomainToViewMapper;
import com.example.view.BugView;
import com.example.view.IssueView;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * Created by Anjulaw on 12/26/2016.
 */
@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    Environment env;

    @Autowired
    RestTemplate resTemplate;

    @Autowired
    HttpEntity qaMetrixHttpEntity;

    @RequestMapping("/getInvalidCount")
    public BugView invalidDefects(@RequestParam(name = "createdDate") String createdDate) {

        try {

            final String totalBugCountUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= 2016-12-01 AND createdDate <= 2016-12-31 AND reporter in (\"sachini@codegen.net\")&fields=key&maxResults=100";


            final String invalidBugCountUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= 2016-12-01 AND createdDate <= 2016-12-31 AND reporter in (\"sachini@codegen.net\")&fields=key&maxResults=100";


            ResponseEntity<Bug> totalBugCountResponse = resTemplate.exchange(totalBugCountUrl, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
            Bug bugCountObject = totalBugCountResponse.getBody();
            if (bugCountObject != null) {

                ResponseEntity<Bug> invalidBugCountResponse = resTemplate.exchange(invalidBugCountUrl, HttpMethod.GET, qaMetrixHttpEntity, Bug.class);
                Bug bugCountInvalid = invalidBugCountResponse.getBody();

                BugView bugViewinvalidDefects = CalculationUtil.calInvalidBugRatio(bugCountObject, bugCountInvalid);

                return bugViewinvalidDefects;

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }


    @RequestMapping("/getDefectRemoval")
    public BugView defectRemoval (@RequestParam(name="issuetype") String issuetype) {
        try{

            final String defectQATestUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=\"Project Ref\" in (TA) AND issuetype in (\"Local Issue\") AND createdDate >= 2017-01-01 AND createdDate <= 2017-01-31&fields=key&maxResults=100";

            final String defectEndUserUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= 2017-01-01 AND createdDate <= 2017-01-31 AND  project  = \"Tour America\" AND level = EXTERNAL AND type in (\"Production Issue\",\"Non-Prod Issue\",Clarification)&fields=key&maxResults=100";

            ResponseEntity<Bug> defectQAResponse = resTemplate.exchange(defectQATestUrl, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
            Bug defectQAObject = defectQAResponse.getBody();

            if (defectQAObject !=null){

                ResponseEntity<Bug> defectEndUserResponse = resTemplate.exchange(defectEndUserUrl, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
                Bug defectEndUserObject = defectEndUserResponse.getBody();

                BugView bugViewdefectRemoval = CalculationUtil.caldefectRemovalEfficiency(defectQAObject,defectEndUserObject);

                return bugViewdefectRemoval;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
