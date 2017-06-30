package com.whysoserious.neeraj.hospitalmanagementsystem;

/**
 * Created by Neeraj on 08-Apr-16.
 */
public class RowItem {
    private String d_name;
    private String p_name;
    private String problem;

    public RowItem(String p_name, String d_name, String problem) {
        this.d_name = d_name;
        this.p_name = p_name;
        this.problem = problem;
    }

    public String getDoctor() {
        return d_name;
    }

    public void setDoctor(String d_name) {
        this.d_name = d_name;
    }

    public String getPatient() {
        return p_name;
    }

    public void setPatient(String p_name) {
        this.p_name = p_name;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
