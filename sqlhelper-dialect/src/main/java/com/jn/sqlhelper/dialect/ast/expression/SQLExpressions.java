package com.jn.sqlhelper.dialect.ast.expression;

import com.jn.langx.expression.operator.BinaryOperator;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.Strings;
import com.jn.langx.util.function.Supplier0;

public class SQLExpressions {
    public static abstract class AbstractExpressionBuilder<E extends SQLExpression> {
        public abstract E build();
    }

    public static abstract class BinaryOperatorExpressionBuilder<E extends SQLExpression & BinaryOperator, T extends BinaryOperatorExpressionBuilder<E, T>> extends AbstractExpressionBuilder<E> {
        private String symbol;
        private SQLExpression left;
        private SQLExpression right;
        private Supplier0<E> supplier;

        public T supplier(Supplier0<E> supplier) {
            this.supplier = supplier;
            return (T) this;
        }

        public T symbol(String symbol) {
            this.symbol = symbol;
            return (T) this;
        }

        public T left(String expression) {
            left = new StringExpression(expression);
            return (T) this;
        }

        public T left(long expression) {
            left = new IntegerOrLongExpression(expression);
            return (T) this;
        }

        public T left(double expression) {
            left = new DoubleExpression(expression);
            return (T) this;
        }

        public T left(SQLExpression expression) {
            left = expression;
            return (T) this;
        }

        public T right(String expression) {
            right = new StringExpression(expression);
            return (T) this;
        }

        public T right(long expression) {
            right = new IntegerOrLongExpression(expression);
            return (T) this;
        }

        public T right(double expression) {
            right = new DoubleExpression(expression);
            return (T) this;
        }

        public T right(SQLExpression expression) {
            right = expression;
            return (T) this;
        }

        @Override
        public E build() {
            Preconditions.checkNotNull(left);
            Preconditions.checkNotNull(right);
            Preconditions.checkNotNull(supplier);

            E binaryOperator = supplier.get();
            if (Strings.isNotEmpty(symbol)) {
                binaryOperator.setOperateSymbol(symbol);
            }
            binaryOperator.setLeft(left);
            binaryOperator.setRight(right);
            return binaryOperator;
        }
    }

    public static class AndBuilder extends BinaryOperatorExpressionBuilder<AndExpression, AndBuilder> {
        public AndBuilder() {
            supplier(new Supplier0<AndExpression>() {
                @Override
                public AndExpression get() {
                    return new AndExpression();
                }
            });
        }
    }

    public static class OrBuilder extends BinaryOperatorExpressionBuilder<OrExpression, OrBuilder> {
        public OrBuilder() {
            supplier(new Supplier0<OrExpression>() {
                @Override
                public OrExpression get() {
                    return new OrExpression();
                }
            });
        }
    }

    public static class EqualBuilder extends BinaryOperatorExpressionBuilder<EqualExpression, EqualBuilder> {
        public EqualBuilder() {
            supplier(new Supplier0<EqualExpression>() {
                @Override
                public EqualExpression get() {
                    return new EqualExpression();
                }
            });
        }
    }

    public static class NotEqualBuilder extends BinaryOperatorExpressionBuilder<NotEqualExpression, NotEqualBuilder> {
        public NotEqualBuilder() {
            supplier(new Supplier0<NotEqualExpression>() {
                @Override
                public NotEqualExpression get() {
                    return new NotEqualExpression();
                }
            });
        }
    }

}
