import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Account {
    private String name;
    private Map<NameCurrency,Integer> currency = new HashMap<>();

    private Deque<Command> commands = new ArrayDeque<>();

    private class RecoveryPoint implements Loadable
    {

        private String name;
        private Map<NameCurrency,Integer> currency;

        private Deque<Command> commands = new ArrayDeque<>();
        public RecoveryPoint() {
            this.name = Account.this.name;
            this.currency = new HashMap<>(Account.this.currency);
            this.commands = new ArrayDeque<>(Account.this.commands);
        }


        @Override
        public void load() {
            Account.this.name = this.name;
            Account.this.currency = this.currency;
            Account.this.commands = this.commands;
        }
    }
    public Account(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalAccessException {
        if(name == null|| name.isEmpty()) throw new IllegalAccessException();
        String oldName = this.name;
        this.commands.push(()->{this.name = oldName;});
        this.name = name;
    }

    public Map<NameCurrency, Integer> getCurency() {
        return new HashMap<>(this.currency);
    }

    public void addCurency(NameCurrency nameCurency, Integer value) throws IllegalAccessException {
        if(value<0) throw new IllegalAccessException();
        if(this.currency.containsKey(nameCurency))
        {
            Integer oldValue = this.currency.get(nameCurency);
            this.commands.push(()->{this.currency.put(nameCurency, oldValue);});

        }
        else
        {
            this.commands.push(()->{this.currency.remove(nameCurency);});
        }
        this.currency.put(nameCurency, value);
    }

    public Account undo() throws NothingToDo
    {
        if (commands.isEmpty()) throw new NothingToDo();
        commands.pop().perform();
        return  this;
    }

    public  Loadable save()
    {
        return new RecoveryPoint();
    }


}
