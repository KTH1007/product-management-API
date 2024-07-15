package kr.co.hanbit.product.management.domain;

public class EntityNotFoundException extends RuntimeException{
    // RuntimeException을 상속받아 Unchecked Exception
    public EntityNotFoundException(String message) {
        super(message);
    }
}
