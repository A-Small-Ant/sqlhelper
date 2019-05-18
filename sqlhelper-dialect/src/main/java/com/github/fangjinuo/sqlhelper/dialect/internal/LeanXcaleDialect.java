/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the LGPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at  http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.fangjinuo.sqlhelper.dialect.internal;

import com.github.fangjinuo.sqlhelper.dialect.internal.limit.OffsetFetchFirstOnlyLimitHandler;

/**
 *
 * LeanXcale Query Engine was forked from Apache Derby, so the Lx-DB SQL dialect is very similar to Apache Derby
 *
 * https://s3-eu-west-1.amazonaws.com/doc-html/doc/latest/develop/index.html#_schemas_and_metadata
 */
public class LeanXcaleDialect extends AbstractDialect {
    public LeanXcaleDialect(){
        super();
        setLimitHandler(new OffsetFetchFirstOnlyLimitHandler());
    }

    @Override
    public boolean isSupportsLimit() {
        return true;
    }

    @Override
    public boolean isSupportsLimitOffset() {
        return true;
    }

    @Override
    public boolean isUseMaxForLimit() {
        return true;
    }
}