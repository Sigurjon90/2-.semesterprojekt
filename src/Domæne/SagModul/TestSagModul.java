package Domæne.SagModul;

public class TestSagModul {

    public static void main(String[] args) {

        SagsBehandler s1 = new SagsBehandler("Alex");
        SagsBehandler s2 = new SagsBehandler("Daniel");

        System.out.println(s1.toString());
        System.out.println(s2.toString());
        System.out.println("-----------------------TEST createCase:-------------------------");
        s1.createCase("misbrug", "stoffer er ikke godt");
        s2.createCase("alko", "alko er ikke godt");

        System.out.println(s1.getCases());
        System.out.println(s2.getCases());
        System.out.println("-----------------------TEST transferCase:-------------------------");

        s1.transferCaseTo(s2, 1);
        System.out.println("");

        System.out.println(s1.getCases());
        System.out.println(s2.getCases());
        System.out.println("-----------------------TEST deleteCase:-------------------------");
        s2.deleteCase(s2.getCases().get(1));

        System.out.println(s1.getCases());
        System.out.println(s2.getCases());

    }

}
