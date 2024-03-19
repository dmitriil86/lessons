public class Lesson {

    public static void main(String[] args) throws IllegalAccessException {
            Account account = new Account("Client");

            account.addCurency(NameCurrency.RUB,100);
            account.addCurency(NameCurrency.RUB,300);
            account.addCurency(NameCurrency.ERU,200);
            account.undo();
            account.undo();
            account.setName("Bank");
            Loadable sv = account.save();
            account.undo();
            account.undo();
            sv.load();
            account.undo();
            account.undo();
    }
}
