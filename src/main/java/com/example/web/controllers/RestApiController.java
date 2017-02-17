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
    HttpEntity qMetrixHttpEntity;


    @RequestMapping("/getIssue")
    public IssueView test(@RequestParam(name = "issueId") String issueId) {

        final String uri = "http://codegen.atlassian.net/rest/api/2/search?jql=createdDate%20%3E%3D%202016-12-01%20AND%20createdDate%20%3C%3D%202016-12-31%20AND%20reporter%20in%20(%22sachini%40codegen.net%22)&fields=key&maxResults=100";

         /*Map<String, String> params = new HashMap<String, String>();
        params.put("issueId", issueId);

        RestTemplate restTemplate = new RestTemplate();
        Issue result = restTemplate.getForObject(uri, Issue.class, params);

        System.out.println(result);*/

        String plainCreds = "anjulaw@codegen.net:Suraj@123";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Issue> response = restTemplate.exchange(uri, HttpMethod.GET, request, Issue.class);
        Issue issue = response.getBody();

        if (issue != null) {

            IssueView vw = DomainToViewMapper.mapDomainIssueToView(issue);
            return vw;
        }
        return null;
    }


    @RequestMapping("/getInvalidCount")
    public BugView invalidDefects(@RequestParam(name = "total") String total) {

        try {

           /*final String totalBugCountUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate%20%3E%3D%202016-12-01%20AND%20createdDate%20%3C%3D%202016-12-31%20AND%20reporter%20in%20(%22sachini%40codegen.net%22)&fields=key&maxResults=100";*/

            final String totalBugCountUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= 2016-12-01 AND createdDate <= 2016-12-31 AND reporter in (\"sachini@codegen.net\")&fields=key&maxResults=100";


            final String invalidBugCountUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= 2016-12-01 AND createdDate <= 2016-12-31 AND reporter in (\"sachini@codegen.net\")&fields=key&maxResults=100";


            ResponseEntity<Bug> totalBugCountResponse = resTemplate.exchange(totalBugCountUrl, HttpMethod.GET,qMetrixHttpEntity, Bug.class);
            Bug bugCountObject = totalBugCountResponse.getBody();
            if (bugCountObject != null) {

                ResponseEntity<Bug> invalidBugCountResponse = resTemplate.exchange(invalidBugCountUrl, HttpMethod.GET, qMetrixHttpEntity, Bug.class);
                Bug bugCountInvalid = invalidBugCountResponse.getBody();

                BugView bugView = CalculationUtil.calInvalidBugRatio(bugCountObject, bugCountInvalid);

                return bugView;

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }


}
