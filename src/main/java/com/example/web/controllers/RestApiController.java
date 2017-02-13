package com.example.web.controllers;

import com.example.domain.Issue;
import com.example.domain.TotalBugCount;
import com.example.domain.User;
import com.example.repository.UserRepository;
import com.example.utils.DomainToViewMapper;
import com.example.view.InvalidDefectsView;
import com.example.view.IssueView;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anjulaw on 12/26/2016.
 */
@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    Environment env;




    @RequestMapping("/getIssue" )
    public IssueView test(@RequestParam(name = "issueId") String issueId){

       final String uri = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate%20%3E%3D%202016-12-01%20AND%20createdDate%20%3C%3D%202016-12-31%20AND%20reporter%20in%20(%22sachini%40codegen.net%22)&fields=key&maxResults=100";

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

        if(issue!=null){

            IssueView vw = DomainToViewMapper.mapDomainIssueToView(issue);
            return vw;
        }
        return null;
    }

   @RequestMapping("/getInvalidCount" )
    public InvalidDefectsView invalidDefects(@RequestParam(name = "total") String total){

       final String uri = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate%20%3E%3D%202016-12-01%20AND%20createdDate%20%3C%3D%202016-12-31%20AND%20reporter%20in%20(%22sachini%40codegen.net%22)&fields=key&maxResults=100";

       String plainCreds = "anjulaw@codegen.net:Suraj@123";
       byte[] plainCredsBytes = plainCreds.getBytes();
       byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
       String base64Creds = new String(base64CredsBytes);

       RestTemplate restTemplate = new RestTemplate();
       HttpHeaders headers = new HttpHeaders();
       headers.add("Authorization", "Basic " + base64Creds);

       HttpEntity<String> request = new HttpEntity<String>(headers);
       ResponseEntity<TotalBugCount> response = restTemplate.exchange(uri, HttpMethod.GET, request, TotalBugCount.class);
       TotalBugCount totalBugCount = response.getBody();

       if(totalBugCount!=null){

           InvalidDefectsView totalBugCountView = DomainToViewMapper.mapDomainTotalBugCountToView(totalBugCount);
           return totalBugCountView;
       }

       return null;

   }



}
