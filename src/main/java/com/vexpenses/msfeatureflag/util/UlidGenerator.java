package com.vexpenses.msfeatureflag.util;

import com.github.f4b6a3.ulid.UlidCreator;

public class UlidGenerator {
    public static String generate(){
        return UlidCreator.getMonotonicUlid().toString();
    }
}