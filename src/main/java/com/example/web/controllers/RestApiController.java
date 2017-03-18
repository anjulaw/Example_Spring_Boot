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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @RequestMapping(value ="/getInvalidCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<BugView> invalidDefects(/*@RequestParam(name = "createdDate") String createdDate*/) {

        try {

            // Calculate the date differnce between Start and End Date
            String start = "2016-01-01";
            String end = "2016-12-31";
            String user = "anjulaw@codegen.net";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);

            Calendar startCalendar = new GregorianCalendar();
            startCalendar.setTime(startDate);
            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(endDate);

            int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
            int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

            System.out.println("Month diff" + diffMonth);

            List<String> dateList = new ArrayList<>();
            String date = sdf.format(startDate);
            dateList.add(date);

            Date initialDate = startDate;
            for (int i = 0; i < diffMonth; i++) {

                Calendar c = Calendar.getInstance();
                c.setTime(initialDate);
                c.add(Calendar.MONTH, 1);  // number of days to add

                initialDate = c.getTime();
                String datex = sdf.format(initialDate);
                dateList.add(datex);
            }

            List<BugView> bugVierwList = new ArrayList<>();

            for (int i = 0; i < dateList.size()-1; i++){

                final String totalBugCountUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= "+dateList.get(i)+" AND createdDate <= "+dateList.get(i+1)+" AND reporter in (\"anjulaw@codegen.net\")&fields=key&maxResults=100";
                System.out.println( "Total Bug Count"+totalBugCountUrl);


                final String invalidBugCountUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= "+dateList.get(i)+" AND createdDate <= "+dateList.get(i+1)+" AND reporter in (\"anjulaw@codegen.net\")&fields=key&maxResults=100";
                System.out.println("Total Invalid Bug count"+invalidBugCountUrl);

                ResponseEntity<Bug> totalBugCountResponse = resTemplate.exchange(totalBugCountUrl, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
                Bug bugCountObject = totalBugCountResponse.getBody();
                if (bugCountObject != null) {

                    ResponseEntity<Bug> invalidBugCountResponse = resTemplate.exchange(invalidBugCountUrl, HttpMethod.GET, qaMetrixHttpEntity, Bug.class);
                    Bug bugCountInvalid = invalidBugCountResponse.getBody();

                    BugView bugViewinvalidDefects = CalculationUtil.calInvalidBugRatio(bugCountObject, bugCountInvalid);

                    bugVierwList.add(bugViewinvalidDefects);
                    return bugVierwList;

                }

            }

            for (BugView bugView : bugVierwList) {
                System.out.println(bugView.getInvalidBugCountRatio());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }


    @RequestMapping("/getDefectRemoval")
    public List<BugView> defectRemoval (@RequestParam(name="issuetype") String issuetype) {
        try{

            String start = "2016-01-01";
            String end = "2016-12-31";
            String user = "anjulaw@codegen.net";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);

            Calendar startCalendar = new GregorianCalendar();
            startCalendar.setTime(startDate);
            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(endDate);

            int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
            int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

            System.out.println("Month diff" + diffMonth);

            List<String> dateList = new ArrayList<>();
            String date = sdf.format(startDate);
            dateList.add(date);

            Date initialDate = startDate;
            for (int i = 0; i < diffMonth; i++) {

                Calendar c = Calendar.getInstance();
                c.setTime(initialDate);
                c.add(Calendar.MONTH, 1);  // number of days to add

                initialDate = c.getTime();
                String datex = sdf.format(initialDate);
                dateList.add(datex);
            }

            List<BugView> bugVierwList = new ArrayList<>();

            for (int i = 0; i < dateList.size()-1; i++){

                final String defectQATestUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=\"Project Ref\" in (TA) AND issuetype in (\"Local Issue\") AND createdDate >= "+dateList.get(i)+" AND createdDate <= "+dateList.get(i+1)+"&fields=key&maxResults=100";

                final String defectEndUserUrl = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= "+dateList.get(i)+" AND createdDate <= "+dateList.get(i+1)+" AND  project  = \"Tour America\" AND level = EXTERNAL AND type in (\"Production Issue\",\"Non-Prod Issue\",Clarification)&fields=key&maxResults=100";

                ResponseEntity<Bug> defectQAResponse = resTemplate.exchange(defectQATestUrl, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
                Bug defectQAObject = defectQAResponse.getBody();

                if (defectQAObject !=null){

                    ResponseEntity<Bug> defectEndUserResponse = resTemplate.exchange(defectEndUserUrl, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
                    Bug defectEndUserObject = defectEndUserResponse.getBody();

                    BugView bugViewdefectRemoval = CalculationUtil.caldefectRemovalEfficiency(defectQAObject,defectEndUserObject);

                    bugVierwList.add(bugViewdefectRemoval);
                    return bugVierwList;

                }

            }

            for (BugView bugView : bugVierwList) {
                System.out.println(bugView.getInvalidBugCountRatio());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("/getDefectLeakage")
    public List<BugView> defectLeakage(@RequestParam(name="issuetype") String issuetype){
        try{

            String start = "2016-01-01";
            String end = "2016-12-31";
            String user = "anjulaw@codegen.net";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);

            Calendar startCalendar = new GregorianCalendar();
            startCalendar.setTime(startDate);
            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(endDate);

            int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
            int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

            System.out.println("Month diff" + diffMonth);

            List<String> dateList = new ArrayList<>();
            String date = sdf.format(startDate);
            dateList.add(date);

            Date initialDate = startDate;
            for (int i = 0; i < diffMonth; i++) {

                Calendar c = Calendar.getInstance();
                c.setTime(initialDate);
                c.add(Calendar.MONTH, 1);  // number of days to add

                initialDate = c.getTime();
                String datex = sdf.format(initialDate);
                dateList.add(datex);
            }

            List<BugView> bugVierwList = new ArrayList<>();

            for (int i = 0; i < dateList.size()-1; i++){

                final String defectsFoundUAT = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= "+dateList.get(i)+" AND createdDate <= "+dateList.get(i+1)+" AND  project  = \"Tour America\" AND level = EXTERNAL AND type in (\"Non-Prod Issue\")";

                final String defectsFoundQA = "https://codegen.atlassian.net/rest/api/2/search?jql=\"Project Ref\" in (TA) AND issuetype in (\"Local Issue\") AND createdDate >= "+dateList.get(i)+" AND createdDate <= "+dateList.get(i+1)+"";

                ResponseEntity<Bug> defectFoundUATResponse = resTemplate.exchange(defectsFoundUAT, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
                Bug defectFoundUATObject = defectFoundUATResponse.getBody();

                if(defectFoundUATObject !=null){

                    ResponseEntity<Bug> defectsFoundQAResponse = resTemplate.exchange(defectsFoundQA, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
                    Bug defectsFoundQAObject = defectsFoundQAResponse.getBody();

                    BugView bugViewDefectLeakage = CalculationUtil.calDefectLeakage(defectFoundUATObject,defectsFoundQAObject);
                    bugVierwList.add(bugViewDefectLeakage);
                    return bugVierwList;
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @RequestMapping("/getDefectSeverity")
    public List<BugView> defectSeverity(@RequestParam(name = "createdDate") String createdDate){
        try{

            String start = "2016-01-01";
            String end = "2016-12-31";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);

            Calendar startCalendar = new GregorianCalendar();
            startCalendar.setTime(startDate);
            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(endDate);

            int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
            int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

            List<String> dateList = new ArrayList<>();
            String date = sdf.format(startDate);
            dateList.add(date);

            Date initialDate = startDate;
            for (int i = 0; i < diffMonth; i++) {

                Calendar c = Calendar.getInstance();
                c.setTime(initialDate);
                c.add(Calendar.MONTH, 1);  // number of days to add

                initialDate = c.getTime();
                String datex = sdf.format(initialDate);
                dateList.add(datex);
            }

            List<BugView> bugVierwList = new ArrayList<>();

            for (int i = 0; i < dateList.size()-1; i++){

                String defectPriority = "Blocker";

                final String validDefectsSeverity = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= "+dateList.get(i)+" AND createdDate <= "+dateList.get(i+1)+" AND resolution != Invalid AND priority = "+defectPriority+"";

                final String totalValidDefects = "https://codegen.atlassian.net/rest/api/2/search?jql=createdDate >= "+dateList.get(i)+" AND createdDate <= "+dateList.get(i+1)+" AND resolution != Invalid";

                ResponseEntity<Bug> validDefectsSeverityResponse = resTemplate.exchange(validDefectsSeverity, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
                Bug validDefectsSeverityObject = validDefectsSeverityResponse.getBody();

                if(validDefectsSeverityObject !=null){

                    ResponseEntity<Bug> totalValidDefectsResponse = resTemplate.exchange(totalValidDefects, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
                    Bug totalValidDefectsyObject = totalValidDefectsResponse.getBody();

                    BugView bugViewDefectSeverity = CalculationUtil.calDefectSeverity(validDefectsSeverityObject,totalValidDefectsyObject,defectPriority);
                    bugVierwList.add(bugViewDefectSeverity);
                    return bugVierwList;
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("/getEffortVariance")
    public List<BugView> effortVariance(@RequestParam(name = "createdDate") String createdDate){

        try{

            String startDate = "2017-01-01";
            String endDate = "2017-01-31";

            List<BugView> bugVierwList = new ArrayList<>();

/*            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);*/

            final String effortURL = "https://codegen.atlassian.net/rest/api/2/search?jql=\"Project Ref\" = TA AND type = Story AND createdDate >= "+startDate+" AND createdDate <="+endDate+"&fields=key,aggregatetimespent,aggregatetimeoriginalestimate";

            ResponseEntity<Bug> effortVarianceResponse = resTemplate.exchange(effortURL, HttpMethod.GET,qaMetrixHttpEntity, Bug.class);
            Bug effortVarianceObject = effortVarianceResponse.getBody();

            if(effortVarianceObject !=null){

                double totalIssueCount = effortVarianceObject.getTotal();

                for(int x=0;x<totalIssueCount;x++){

                    BugView bugVieweffortVariance = CalculationUtil.caleffortVariance(effortVarianceObject);
                    bugVierwList.add(bugVieweffortVariance);
                    return bugVierwList;
                }

                for (int y=0; y<totalIssueCount; y++){
                    List<String> issueList = new ArrayList<>();
                    String issueID = effortVarianceObject.getKey();
                    issueList.add(issueID);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

}
