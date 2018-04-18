package java8.ex04;


import java8.data.Account;
import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Exercice 04 - FuncCollection
 */
public class Lambda_04_Test {

    // tag::interfaces[]
    interface GenericPredicate<T> {
        boolean test(T t);
    }

    interface GenericMapper<T, E> {
      E map(T t);
    }

    interface Processor<T> {
        void process(T t);
    }
    // end::interfaces[]

    // tag::FuncCollection[]
    class FuncCollection<T> {

        private Collection<T> list = new ArrayList<>();

        public void add(T a) {
            list.add(a);
        }

        public void addAll(Collection<T> all) {
            for(T el:all) {
                list.add(el);
            }
        }
    // end::FuncCollection[]

        // tag::methods[]
        private FuncCollection<T> filter(GenericPredicate<T> predicate) {
            FuncCollection<T> result = new FuncCollection<>();           
            for (T item : list) {
				if(predicate.test(item))
            		result.add(item);
			}
            return result;
        }

        private <E> FuncCollection<E> map(GenericMapper<T, E> mapper) {
            FuncCollection<E> result = new FuncCollection<>();
            for (T item : list) {
				result.add(mapper.map(item));
			}
            return result;
        }

        private void forEach(Processor<T> processor) {
           // TODO
        	for (T item : list) {
				processor.process(item);
			}
        }
        // end::methods[]

    }



    // tag::test_filter_map_forEach[]
    @Test
    public void test_filter_map_forEach() throws Exception {

        List<Person> personList = Data.buildPersonList(100);
        FuncCollection<Person> personFuncCollection = new FuncCollection<>();
        personFuncCollection.addAll(personList);

        personFuncCollection
                // TODO filtrer, ne garder uniquement que les personnes ayant un age > 50
                .filter(p->p.getAge()>50)
                // TODO transformer la liste de personnes en liste de comptes. Un compte a par défaut un solde à 1000.
                .map(p->{
                	Account a = new Account();
                	a.setBalance(1000);
                	a.setOwner(p);
                	return a;
                })
                // TODO vérifier que chaque compte a un solde à 1000.
                // TODO vérifier que chaque titulaire de compte a un age > 50
                .forEach(a->{
                	assertTrue((a.getBalance()==1000));
                	assertTrue(a.getOwner().getAge()>50);
                });
    }
    // end::test_filter_map_forEach[]

    // tag::test_filter_map_forEach_with_vars[]
    @Test
    public void test_filter_map_forEach_with_vars() throws Exception {

        List<Person> personList = Data.buildPersonList(100);
        FuncCollection<Person> personFuncCollection = new FuncCollection<>();
        personFuncCollection.addAll(personList);
        
        GenericPredicate<Person> filterByAge = p->p.getAge()>50;
        GenericMapper<Person, Account> mapToAccount = p->{
        	Account a = new Account();
        	a.setBalance(1000);
        	a.setOwner(p);
        	return a;
        };
        Processor<Account> verifyAccount = a->{
        	assertTrue((a.getBalance()==1000));
        	assertTrue(a.getOwner().getAge()>50);
        };

        personFuncCollection
                .filter(filterByAge)
                .map(mapToAccount)
                .forEach(verifyAccount);

    }
    // end::test_filter_map_forEach_with_vars[]


}
