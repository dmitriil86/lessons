import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void getName() {
        String expected = "Test";
        Account account = new Account(expected);
        String actual = account.getName();
        assertEquals(expected,actual);

    }

    @Test
    void setName() throws IllegalAccessException {
        String expected = "Test";
        Account account = new Account("Tester");
        account.setName(expected);
        String actual = account.getName();
        assertEquals(expected,actual);
    }

    @Test
    void getCurency() throws IllegalAccessException {
        Map<NameCurrency, Integer> nameCurrencyIntegerMap = new HashMap<>();
        nameCurrencyIntegerMap.put(NameCurrency.ERU,200);

        Account account = new Account("Test");
        account.addCurency(NameCurrency.ERU, 200);
        Map<NameCurrency, Integer> actual = account.getCurency();
        assertEquals(nameCurrencyIntegerMap,actual);
    }


    @Test
    void undo() throws IllegalAccessException {
        Account account = new Account("Test");
        account.addCurency(NameCurrency.RUB,100);
        account.addCurency(NameCurrency.ERU,200);
        account.setName("Tester");
        assertEquals("Tester",account.getName());
        account.undo();
        assertEquals("Test",account.getName());
        account.undo();
        assertFalse(account.getCurency().containsKey(NameCurrency.ERU));
    }

    @Test
    void save() throws IllegalAccessException {
        Account account = new Account("Test");
        Loadable sv = account.save();
        account.setName("Tester");
        assertEquals("Tester", account.getName());
        sv.load();
        assertEquals("Test", account.getName());

    }
}