package com.calvindo.aldi.sutanto.tubes.models;

import java.util.ArrayList;

public class ListKost {
    public ArrayList<Kost> KOST;

    public ListKost(){
        KOST = new ArrayList();
        KOST.add(PANDE);
        KOST.add(NATTE);
//        KOST.add(ANDRE);
//        KOST.add(GEDE);
//        KOST.add(JOHN);
    }

    public static final Kost PANDE = new Kost("Kost Premium", "Gombong",
            "Jl. Potongan", "-7.607468", "109.515752", "082112134121",
            2000000.00 , "https://upload.wikimedia.org/wikipedia/commons/thumb/6/60/YellowLabradorLooking.jpg/250px-YellowLabradorLooking.jpg");

    public static final Kost NATTE = new Kost("Kost Pertamax", "Yogyakarta",
            "Jl. Potongan", "-7.607468", "109.515752", "082152134121",
            3000000.00 , "https://upload.wikimedia.org/wikipedia/commons/thumb/6/60/YellowLabradorLooking.jpg/250px-YellowLabradorLooking.jpg");
}
