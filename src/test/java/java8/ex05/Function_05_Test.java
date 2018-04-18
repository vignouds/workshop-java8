package java8.ex05;

import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 5 - java.util.function.Consumer
 */
public class Function_05_Test {

    //tag::functions[]
    Consumer<Person> changePasswordToSecret = p->p.setPassword("secret");

    Consumer<Person> verifyAge = p->{
    	assert p.getAge()>4;
    };

    Consumer<Person> verifyPassword = p->{
    	assert p.getPassword().equals("secret");
    };
    //end::functions[]


    @Test
    public void test_consumer() throws Exception {
        List<Person> personList = Data.buildPersonList();

        personList.forEach(changePasswordToSecret);

        personList.forEach(verifyAge.andThen(verifyPassword));
        
        /*for(Person p : personList) {
            verifyAge.accept(p);
            verifyPassword.accept(p);
        }*/
    }
}
