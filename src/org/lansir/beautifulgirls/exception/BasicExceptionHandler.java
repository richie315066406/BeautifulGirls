/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lansir.beautifulgirls.exception;

import org.lansir.beautifulgirls.utils.LogUtil;

import android.content.Context;

/**
 * Basic ExceptionHandler, toast the exception
 * @author zhe.yangz 2012-2-17 上午11:39:58
 */
public class BasicExceptionHandler implements ExceptionHandler {

    protected Context mContext = null;

    public BasicExceptionHandler(Context c) {
        mContext = c;
    }

    public void handle(Exception e) {
        if (e instanceof AkInvokeException) {
            LogUtil.e(((AkInvokeException) e).code + " " + e.getCause().toString());
        }
        else if (e instanceof AkServerStatusException) {
            LogUtil.e(((AkServerStatusException) e).code + " " + e.toString());
        }
        else {
        	LogUtil.e(e.toString());
        }
    }

}
