package com.example.hackthe6ix;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface MyInterface {
    /**
     * Invoke lambda function "echo". The function name is the method name
     */
    @LambdaFunction
    String matchingAlgo(String s);

    /**
     * Invoke lambda function "echo". The functionName in the annotation
     * overrides the default which is the method name
     */
    @LambdaFunction(functionName = "matchingAlgo")
    void noEcho(String s);
}