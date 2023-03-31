package dto;

import java.util.Random;

public class CourierCreation {

    int id;
            String login;
            String password;
            String name;


    public CourierCreation(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;


        //this.login = String.valueOf((Math.random() + 1)).toString().substring(7);

        Random rand = new Random();

        String str = rand.ints(48, 123)

                .filter(num -> (num<58 || num>64) && (num<91 || num>96))

                .limit(8)

                .mapToObj(c -> (char)c).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)

                .toString();

        this.login = str;

    }

}
