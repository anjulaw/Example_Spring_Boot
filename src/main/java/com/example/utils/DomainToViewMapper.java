package com.example.utils;

import com.example.domain.Issue;
import com.example.view.IssueView;

/**
 * Created by Anjulaw on 12/26/2016.
 */
public class DomainToViewMapper {

    public static IssueView mapDomainIssueToView(Issue domainIssue){

        IssueView vw = new IssueView();
        vw.setId(domainIssue.getId());


        return vw;
    }
}
