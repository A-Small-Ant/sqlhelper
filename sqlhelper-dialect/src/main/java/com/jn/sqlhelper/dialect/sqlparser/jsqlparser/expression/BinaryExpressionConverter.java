package com.jn.sqlhelper.dialect.sqlparser.jsqlparser.expression;

import com.jn.langx.expression.operator.BinaryOperator;
import com.jn.langx.util.function.Supplier;
import com.jn.sqlhelper.dialect.expression.SQLExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;

public abstract class BinaryExpressionConverter<SE extends SQLExpression & BinaryOperator, JE extends BinaryExpression> implements ExpressionConverter<SE, JE> {

    private Supplier<SE,JE > jsqlparserExpressionSupplier;

    public void setJsqlparserExpressionSupplier(Supplier<SE, JE> supplier) {
        this.jsqlparserExpressionSupplier = supplier;
    }

    @Override
    public JE toJSqlParserExpression(SE expression) {
        SQLExpression left = (SQLExpression) expression.getLeft();
        SQLExpression right = (SQLExpression) expression.getRight();

        ExpressionConverterRegistry registry = ExpressionConverterRegistry.getInstance();
        ExpressionConverter leftExpressionConverter = registry.getExpressionConverterByStandardExpressionClass(left.getClass());
        Expression leftExp = leftExpressionConverter.toJSqlParserExpression(left);
        ExpressionConverter rightExpressionConverter = registry.getExpressionConverterByStandardExpressionClass(right.getClass());
        Expression rightExp = rightExpressionConverter.toJSqlParserExpression(right);

        JE jsqlparserExpression = jsqlparserExpressionSupplier.get(expression);
        jsqlparserExpression.setLeftExpression(leftExp);
        jsqlparserExpression.setRightExpression(rightExp);
        return jsqlparserExpression;
    }

    @Override
    public SE fromJSqlParserExpression(JE expression) {
        return null;
    }

}
