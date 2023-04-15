package com.shack.andrahalli;

public class AdminViewEmpModel {

    String employeename,employeephone,employeerole,employeeid,url;

    AdminViewEmpModel(){

    }

    public AdminViewEmpModel(String employeename, String employeephone, String employeerole, String employeeid, String url) {
        this.employeename = employeename;
        this.employeephone = employeephone;
        this.employeerole = employeerole;
        this.employeeid = employeeid;
        this.url = url;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getEmployeephone() {
        return employeephone;
    }

    public void setEmployeephone(String employeephone) {
        this.employeephone = employeephone;
    }

    public String getEmployeerole() {
        return employeerole;
    }

    public void setEmployeerole(String employeerole) {
        this.employeerole = employeerole;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
