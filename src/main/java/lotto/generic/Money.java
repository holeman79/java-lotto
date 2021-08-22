package lotto.generic;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

public class Money {
    public static final Money ZERO = Money.wons(0);

    private final BigDecimal amount;

    public static Money wons(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public static <T> Money sum(Collection<T> bags, Function<T, Money> monetary) {
        return bags.stream().map(monetary).reduce(Money.ZERO, Money::plus);
    }

    Money(BigDecimal amount) {
        validateGreaterThanNegative(amount);
        this.amount = amount;
    }

    private void validateGreaterThanNegative(final BigDecimal amount) {
        if (amount.longValue() < 0) {
            throw new IllegalArgumentException("Money는 0보다 작을 수 없습니다.");
        }
    }

    public Money plus(Money amount) {
        return new Money(this.amount.add(amount.amount));
    }

    public Money minus(Money amount) {
        return new Money(this.amount.subtract(amount.amount));
    }

    public Money times(double percent) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(percent)));
    }

    public double divide(Money amount) {
        return this.amount.divide(amount.amount, 2, BigDecimal.ROUND_FLOOR)
                .doubleValue();
    }

    public int divideAndDiscardRemainder(Money amount) {
        return this.amount.divide(amount.amount, BigDecimal.ROUND_FLOOR)
                .intValue();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Money)) {
            return false;
        }

        Money other = (Money) object;
        return Objects.equals(amount.doubleValue(), other.amount.doubleValue());
    }

    public int hashCode() {
        return Objects.hashCode(amount);
    }

    public String toString() {
        return amount.toString() + "원";
    }
}