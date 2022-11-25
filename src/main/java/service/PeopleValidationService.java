package service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PeopleValidationService {

    public double getValidationPeople() {

        return validPeople();

    }

    public Future<Double> getValidationPeopleAsync() {

        return CompletableFuture.supplyAsync(this::validPeople);

    }

    public double validPeople() {

        delay();

        return ThreadLocalRandom.current().nextDouble() * 100;

    }

    private static void delay() {

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
