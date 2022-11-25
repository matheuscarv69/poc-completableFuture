import domain.People;
import service.PeopleValidationService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<People> peopleList = createPeopleList();
        var validationService = new PeopleValidationService();

        usingCompletableFeature(peopleList, validationService);

//        notUsingCompletableFuture(peopleList, validationService);

    }

    private static List<People> createPeopleList() {

        return List.of(
                People.builder().id(1L).name("Axel").surname("Rose").build(),
                People.builder().id(2L).name("Dizzy").surname("Reed").build(),
                People.builder().id(3L).name("Slash").surname("Hudson").build(),
                People.builder().id(4L).name("Richard").surname("Fortus").build(),
                People.builder().id(5L).name("Duff").surname("McKagan").build(),
                People.builder().id(6L).name("Frank").surname("Ferrer").build()
        );

    }

    private static void usingCompletableFeature(List<People> peopleList, PeopleValidationService validationService) {
        var invocationStartTime = System.currentTimeMillis();

        var futureList = peopleList
                .stream()
                .map(people -> {

                    return validationService.getValidationPeopleAsync();

                })
                .collect(Collectors.toList());

        var endInvocationTime = System.currentTimeMillis();
        System.out.println("Invocation Time: " + (endInvocationTime - invocationStartTime) + " ms");


        workingInOtherThing();


        var validations = futureList
                .stream()
                .map(future -> {

                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;

                })
                .collect(Collectors.toList());

        var endExecutionTime = System.currentTimeMillis();
        System.out.println("Validation Execution Time: " + (endExecutionTime - invocationStartTime) + " ms");


        System.out.println("Validation list: " + validations);
    }

    private static void notUsingCompletableFuture(List<People> peopleList, PeopleValidationService validationService) {
        var invocationStartTime = System.currentTimeMillis();

        var validations = peopleList
                .stream()
                .map(people -> {

                    workingInOtherThing();
                    return validationService.getValidationPeople();

                })
                .collect(Collectors.toList());

        var endInvocationTime = System.currentTimeMillis();
        System.out.println("Runtime: " + (endInvocationTime - invocationStartTime) + " ms");

        System.out.println("Validation list: " + validations);
    }


    private static void workingInOtherThing() {
        long sum = 0;

        for (int i = 0; i < 1000000; i++) {
            sum += i;
        }

        System.out.println("Working on something else, like adding numbers: " + sum);
    }

}