package com.jn.sqlhelper.dialect.ast.expression;

import com.jn.langx.expression.operator.compare.NE;

public class NotEqualExpression extends NE implements SQLExpression {
    public NotEqualExpression() {
        setOperateSymbol("<>");
    }
}
