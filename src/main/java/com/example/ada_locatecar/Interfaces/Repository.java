package com.example.ada_locatecar.Interfaces;

public interface Respository<T> {


    public default java.lang.Object add(java.lang.Object T){
        return T;
    }
}
