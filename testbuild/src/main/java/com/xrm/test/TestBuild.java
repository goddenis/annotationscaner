package com.xrm.test;

import com.xrm.annotations.CodeUpdateAnnotaition;

@CodeUpdateAnnotaition(author = "Denis Bogomolov", date = "28/11/2016", description = "Update")
public class TestBuild {
    public static void main(String[] args) {
        System.out.println("Project updated");
    }
}
