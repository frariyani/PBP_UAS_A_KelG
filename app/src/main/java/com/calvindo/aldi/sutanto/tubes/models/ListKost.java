package com.calvindo.aldi.sutanto.tubes.models;

import java.util.ArrayList;

public class ListKost {
    public ArrayList<Kost> ListKost;

    public ListKost(){
        ListKost = new ArrayList<>();
        ListKost.add(new Kost("Kost Premium", "Gombong",
                "Jl. Potongan", "-7.607468", "109.515752", "082112134121",
                2000000.00 , "https://pbs.twimg.com/media/EjuODzyVkAAaMNT?format=jpg&name=large", 0));
        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
                "Jl. Potongan", "-7.607468", "109.515752", "082152134121",
                3000000.00 , "https://pbs.twimg.com/media/EjuOb1YVcAEh__k?format=jpg&name=900x900",0));
        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
                "Jl. Potongan", "-7.607468", "109.515752", "082152134121",
                3000000.00 , "https://pbs.twimg.com/media/EjuOeDTUYAEkWVc?format=jpg&name=medium",0));
        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
                "Jl. Potongan", "-7.607468", "109.515752", "082152134121",
                3000000.00 , "https://pbs.twimg.com/media/EjuOhDEVkAA1Nrv?format=jpg&name=small",0));
        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
                "Jl. Potongan", "-7.607468", "109.515752", "082152134121",
                3000000.00 , "https://pbs.twimg.com/media/EjuOjU5VkAAETkt?format=jpg&name=900x900",0));
    }
}
