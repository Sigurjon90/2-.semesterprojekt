package Domain.CaseModule;

import Domain.User.SocialWorker;

public class TestSagModul {

    public static void main(String[] args) {

        SocialWorker s1 = new SocialWorker("Alex", "Tholle", "altho18", "1234");
        SocialWorker s2 = new SocialWorker("Daniel", "Szenczi", "dasze18", "5678");

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
        
        System.out.println("-----------------------TEST closeCase:-------------------------");
        System.out.println(s2.getCases().get(2).getDescription());
        s2.getCases().get(2).setDescription("Test Edit");
        System.out.println(s2.getCases().get(2).getDescription());
        s2.getCases().get(2).closeCase(true, "test lukning");
        s2.getCases().get(2).setDescription("Jalla");
        System.out.println(s2.getCases().get(2).getDescription());

    }

}
