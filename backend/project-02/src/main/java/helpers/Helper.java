package helpers;

import models.Product;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static List<Product> getDummyProducts() {
        var list = new ArrayList<Product>();
        list.add(new Product(
                1L,
                "https://guesseu.scene7.com/is/image/GuessEU/M63H24W7JF0-L302-ALTGHOST?wid=1500&fmt=jpeg&qlt=80&op_sharpen=0&op_usm=1.0,1.0,5,0&iccEmbed=0",
                "CHECK PRINT SHIRT",
                110D
        ));
        list.add(new Product(
                2L, "https://guesseu.scene7.com/is/image/GuessEU/FLGLO4FAL12-BEIBR?wid=700&amp;fmt=jpeg&amp;qlt=80&amp;op_sharpen=0&amp;op_usm=1.0,1.0,5,0&amp;iccEmbed=0",
                "GLORIA HIGH LOGO SNEAKER",
                91D
        ));
        list.add(new Product(
                1L,
                "https://guesseu.scene7.com/is/image/GuessEU/HWVG6216060-TAN?wid=700&amp;fmt=jpeg&amp;qlt=80&amp;op_sharpen=0&amp;op_usm=1.0,1.0,5,0&amp;iccEmbed=0",
                "CATE RIGID BAG",
                94.5D
        ));
        list.add(new Product(
                1L,
                "http://guesseu.scene7.com/is/image/GuessEU/WC0001FMSWC-G5?wid=520&fmt=jpeg&qlt=80&op_sharpen=0&op_usm=1.0,1.0,5,0&iccEmbed=0",
                "GUESS CONNECT WATCH",
                438.9D
        ));
        list.add(new Product(
                1L,
                "https://guesseu.scene7.com/is/image/GuessEU/AW6308VIS03-SAP?wid=700&amp;fmt=jpeg&amp;qlt=80&amp;op_sharpen=0&amp;op_usm=1.0,1.0,5,0&amp;iccEmbed=0"
                , "70s RETRO GLAM KEFIAH",
                20D
        ));

        return list;

    }
}
