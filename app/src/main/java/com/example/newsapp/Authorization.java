package com.example.newsapp;

public interface Authorization {
    int _invalidLogin = 1;
    int _invalidPassword = 2;
    int _successUser = 3;
    int _successAdmin = 4;
    int ERROR_READ = 0;
}
