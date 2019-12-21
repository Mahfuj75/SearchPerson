package com.onenation.oneworld.mahfuj75.searchperson.objectclass;

import android.content.Context;

import com.onenation.oneworld.mahfuj75.searchperson.R;

import java.util.Objects;

/**
 * Created by mahfu on 2/20/2017.
 */

public class SubjectSelector {

    public SubjectSelector() {
    }

    public String[] GetSubject(Context context, String subject)
    {
        String[] subjectList ;
        if(Objects.equals(subject, "পণ্য"))
        {
            subjectList = context.getResources().getStringArray(R.array.subject_ponno);
        }


        else if(Objects.equals(subject, "পরিবেশ"))
        {
            subjectList = context.getResources().getStringArray(R.array.subject_porebesh);

        }
        else if(Objects.equals(subject, "অপরাধ"))
        {
            subjectList = context.getResources().getStringArray(R.array.subject_oporadh);


        }
        else if(Objects.equals(subject, "চাহিদা"))
        {
            subjectList = context.getResources().getStringArray(R.array.subject_chahida);

        }

        else{

            subjectList = context.getResources().getStringArray(R.array.select);
        }
        return subjectList;
    }
}
