/*
 * The MIT License
 *
 * Copyright 2017 Intuit Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.intuit.karate;

import java.io.File;

/**
 *
 * @author pthomas3
 */
public class ScriptEnv {    
    
    public final String env;
    public final String tagSelector;
    public final File featureDir;
    public final String featureName;
    public final CallCache callCache;
    public final Logger logger;
    
    public ScriptEnv(String env, String tagSelector, File featureDir, String featureName, 
            CallCache callCache, Logger logger) {
        this.env = env;
        this.tagSelector = tagSelector;
        this.featureDir = featureDir;
        this.featureName = featureName;
        this.callCache = callCache;
        this.logger = logger;
    }
    
    public ScriptEnv(String env, String tagSelector, File featureDir, String featureName) {
        this(env, tagSelector, featureDir, featureName, new CallCache(), new Logger());
    }    
    
    public static ScriptEnv forEnvAndCurrentWorkingDir(String env) {
        return forEnvAndWorkingDir(env, new File("."));
    }
    
    public static ScriptEnv forEnvAndClass(String env, Class clazz) {
        return forEnvAndWorkingDir(env, FileUtils.getDirContaining(clazz));
    }     
    
    private static ScriptEnv forEnvAndWorkingDir(String env, File workingDir) {
        return new ScriptEnv(env, null, workingDir, null);
    }    
    
    public static ScriptEnv forEnvAndFeatureFile(String env, File featureFile) {
        return forEnvTagsAndFeatureFile(env, null, featureFile);
    }  
    
    public static ScriptEnv forEnvFeatureFileAndLogger(String env, File featureFile, Logger logger) {
        return new ScriptEnv(env, null, featureFile.getParentFile(), featureFile.getName(), new CallCache(), logger);
    }     
    
    public static ScriptEnv forEnvTagsAndFeatureFile(String env, String tagSelector, File featureFile) {
        return new ScriptEnv(env, tagSelector, featureFile.getParentFile(), featureFile.getName());
    }  
    
    public ScriptEnv refresh(String in) { // immutable
        String karateEnv = StringUtils.trimToNull(in);
        if (karateEnv == null) {
            karateEnv = StringUtils.trimToNull(env);
            if (karateEnv == null) {
                karateEnv = StringUtils.trimToNull(System.getProperty(ScriptBindings.KARATE_ENV));
            }
        }
        return new ScriptEnv(karateEnv, tagSelector, featureDir, featureName, callCache, logger);
    }
    
    @Override
    public String toString() {
        return featureName + ", env: " + env + ", dir: " + featureDir;
    }        
    
}
