package hr.smilebacksmile.codewars.sumoddnumbers;

import hr.smilebacksmile.codewars.catalog.domain.Catalog;

import java.sql.SQLOutput;
import java.util.Optional;

public class Applicaton {



    public static void main(String[] args){

        String input =
                "<prod><name>drill</name><prx>99</prx><qty>5</qty></prod>\n\n" +
                        "<prod><name>hammer</name><prx>10</prx><qty>50</qty></prod>\n\n" +
                        "<prod><name>screwdriver</name><prx>5</prx><qty>51</qty></prod>\n\n" +
                        "<prod><name>table saw</name><prx>1099.99</prx><qty>5</qty></prod>\n\n";


        System.out.println(Optional.ofNullable(Catalog.catalog(input, "d")).orElse("x"));



    }

}
